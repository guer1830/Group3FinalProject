package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * This class consists of methods to query the AlphaAdvantage API for stock quotes and stock
 * histories, which are then be used to generate chart. The API is done with the help of a async
 * class. This is to prevent any issue blocking the main ui thread.
 * Furthermore, it allows the user to select and view different timeframe for the stock price
 * histories.
 */
public class StockDetailActivity extends AppCompatActivity {

    enum STOCK_COMMAND {
        INTRADAY,
        HISTORY,
        QUOTES
    }

    private List<Pair<Date, Double>> historyPrices =new ArrayList<Pair<Date, Double>>();
    private List<Pair<Date, Double>> intradayPrices =new ArrayList<Pair<Date, Double>>();
    final static private String API_HISTORY_JSON_KEY = "Time Series (Daily)";
    final static private String API_INTRADAY_JSON_KEY = "Time Series (5min)";
    final static private String API_CLOSE_PRICE = "4. close";
    private String stockSymbol = "";
    static ArrayList<String> APIKeys = new ArrayList<>(List.of("WXJLN4LO84RM3D7N","9WZPCTEGQG3C7W6Y","RZJ00B9YJEXSXRM1","Y1PSH1HLWOIXGQKU","7QL7T942WEIV77PM","T9YY3H65VFYXKQ3H"));
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        Intent intent = getIntent();

        if (intent.hasExtra("StockSymbol")) {
            stockSymbol = intent.getStringExtra("StockSymbol");
            Log.d(getClass().getSimpleName(),stockSymbol);
        }

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.time_range);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton rb=(RadioButton)findViewById(checkedId);
            String range = (String) rb.getText();

            Calendar date = getCurrentDate();

            // checkedId is the RadioButton selected
            if (range.equals("1D")) {
                // TODO: figure out how to get real time quotes without having to spend money
                date.add(Calendar.DAY_OF_YEAR, -1);
            }else if (range.equals("1W")) {
                date.add(Calendar.DAY_OF_YEAR, -7);
            } else if (range.equals("1M")) {
                date.add(Calendar.MONTH, -1);
            } else if (range.equals("3M")) {
                date.add(Calendar.MONTH, -3);
            } else if (range.equals("1Y")) {
                date.add(Calendar.YEAR, -1);
            } else if (range.equals("All")) {
                date.add(Calendar.YEAR, -100);
            }

            Date startDate = date.getTime();

            if (range.equals("1D") ) {
                if (intradayPrices.isEmpty()) {
                    intradayPrices = getStockIntraday();
                }
                generateGraph(graph, startDate, intradayPrices);
                return;
            }else if (historyPrices.isEmpty()) {
                historyPrices = getStockHistories();
            }
            generateGraph(graph, startDate, historyPrices);
        });

        intradayPrices = getStockIntraday();
        Calendar date = getCurrentDate();
        date.add(Calendar.DAY_OF_YEAR, -1);
        Date startDate = date.getTime();
        generateGraph(graph, startDate, intradayPrices);
        populateQuotes();

        TextView stockSymbolTextView = (TextView) findViewById(R.id.stock_symbol);
        stockSymbolTextView.setText(stockSymbol);
        stockSymbolTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
    }

    /**
     * This method calls the AlphaAdvantage API to get a list of price histories for the given
     * stock symbol
     * @return list of dates and prices
     */
    protected List<Pair<Date, Double>> getStockHistories() {
        try {
            List<Pair<Date, Double>> histories = (List<Pair<Date, Double>>) new StockAPITask(stockSymbol, STOCK_COMMAND.HISTORY).execute().get();
            return histories;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * This method calls the AlphaAdvantage API to get a list of intraday prices for the given
     * stock symbol
     * @return list of dates and prices
     */
    protected List<Pair<Date, Double>> getStockIntraday() {
        try {
            List<Pair<Date, Double>> intraday = (List<Pair<Date, Double>>) new StockAPITask(stockSymbol, STOCK_COMMAND.INTRADAY).execute().get();
            return intraday;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * generate a Calendar class for current day
     * @return current day Calendar
     */
    protected Calendar getCurrentDate() {
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }

    /**
     * This method calls the AlphaAdvantage stock quotes API and populate them in the ui
     * It will also show a Toast if the API rate limit is exceeded
     */
    protected void populateQuotes() {
        try {
            JSONObject quotes = (JSONObject) new StockQuotesAPI(stockSymbol).execute().get();
            if (quotes != null) {
                TextView open_text = (TextView) findViewById(R.id.stock_open);
                TextView close_text = (TextView) findViewById(R.id.stock_close);
                TextView low_text = (TextView) findViewById(R.id.stock_low);
                TextView high_text = (TextView) findViewById(R.id.stock_high);

                open_text.setText("open: " + quotes.get("02. open"));
                close_text.setText("previous close: " + quotes.get("08. previous close"));
                low_text.setText("low: " + quotes.get("04. low"));
                high_text.setText("high: " + quotes.get("03. high"));
            }else {
                Toast.makeText(StockDetailActivity.this,"Exceeding rate limit of AlphaAdvantage API (5 per minute)",Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method uses the stock prices to generate a chart in the UI with the choice of different
     * timeframe.
     * @param graph
     * @param startDate
     * @param stockPrices
     */
    private void generateGraph(GraphView graph, Date startDate, List<Pair<Date, Double>> stockPrices) {
        ArrayList<DataPoint> dps = new ArrayList<DataPoint>();
        if (stockPrices.isEmpty()) {
            return;
        }

        double maxPrice = 0;
        double minPrice = Double.MAX_VALUE;
        Date maxDate = stockPrices.get(stockPrices.size()-1).first;
        Date minDate = stockPrices.get(stockPrices.size()-1).first;

        for (Pair<Date, Double> datePrice : stockPrices) {
            if (datePrice.first.compareTo(startDate) >= 0) {
                if (dps.isEmpty()) {
                    minDate = datePrice.first;
                }
                dps.add(new DataPoint(datePrice.first, datePrice.second));
                maxPrice = Math.max(datePrice.second, maxPrice);
                minPrice = Math.min(datePrice.second, minPrice);
            }
        }

        //TODO the graph need to be seriously tuned.
        LineGraphSeries <DataPoint> series = new LineGraphSeries<DataPoint>(dps.toArray(new DataPoint[0]));
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(StockDetailActivity.this));
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);

        graph.getViewport().setMinX(minDate.getTime());
        graph.getViewport().setMaxX(maxDate.getTime()+1);
        graph.getViewport().setMaxY(maxPrice*1.1);
        graph.getViewport().setMinY(Math.max(0, minPrice/1.1));
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setNumHorizontalLabels(10);
    }

    /**
     * This method is used to return to the previous view
     * @param view
     */
    public void returnToMainView(View view){
        Log.d("StockDetailView","returning to main");
        finish();
    }

    /**
     * This class provides methods to send request to AlphaAdvantage API to get stock quote and then
     * parse them into JSON message for the Activity to consume.
     */
    protected static class StockQuotesAPI extends AsyncTask<Void, Void, JSONObject> {
        String symbol;

        /**
         * This is the constructor of StockQuotesAPI
         * @param symbol stock symbol
         */
        StockQuotesAPI(String symbol) {
            // list all the parameters like in normal class define
            this.symbol = symbol;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);
        }

        /**
         * This method is calling the main doWork() method, where reqeust are being sent and parsed
         * @param params
         * @return
         */
        @Override
        protected JSONObject doInBackground(Void... params) {
            return doWork();
        }

        /**
         * This is the main function of this async class. It queries the AlphaAdvantage API for stock
         * quotes and genreate a JsonObject from it.
         * @return
         */
        protected JSONObject doWork() {
            String api = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + this.symbol + "&apikey=" + APIKeys.get(count++ % APIKeys.size());

            try {
                URL stockApi = new URL(api);
                URLConnection urlConn = stockApi.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                return (JSONObject) new JSONObject(stringBuffer.toString()).get("Global Quote");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * This class provides methods to send request to AlphaAdvantage API to get stock histories and then
     * parse them into JSON message for the Activity to consume.
     */
    protected static class StockAPITask extends AsyncTask<Void, Void, Object> {
        String symbol;
        STOCK_COMMAND command;

        /**
         * This is the constructor of StockQuotesAPI
         * @param symbol  stock symbol
         * @param command  STOCK_COMMAND
         */
        StockAPITask(String symbol, STOCK_COMMAND command) {
            // list all the parameters like in normal class define
            this.symbol = symbol;
            this.command = command;
        }

        @Override
        protected void onPostExecute(Object s) {
            super.onPostExecute(s);
        }

        /**
         * This method is calling the main doWork() method, where reqeust are being sent and parsed
         * @param params
         * @return
         */
        @Override
        protected Object doInBackground(Void... params) {
            return doWork();
        }

        /**
         * This is the main function of this async class. It queries the AlphaAdvantage API for stock
         * histories and genreate a JsonObject from it.
         * @return
         */
        protected Object doWork() {
            List<Pair<Date, Double>> datePrices =new ArrayList<Pair<Date, Double>>();
            String api = "";
            String pattern = "";
            String jsonKey = "";
            //https://github.com/sfuhrm/yahoofinance-api
            switch (this.command) {
                case INTRADAY:
                    api = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + this.symbol + "&interval=5min&apikey=" + APIKeys.get(count++ % APIKeys.size());
                    pattern = "yyyy-MM-dd HH:mm:ss";
                    jsonKey = API_INTRADAY_JSON_KEY;
                    break;
                case HISTORY:
                    api = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + this.symbol + "&outputsize=full&apikey=" + APIKeys.get(count++ % APIKeys.size());
                    pattern = "yyyy-MM-dd";
                    jsonKey = API_HISTORY_JSON_KEY;
                    break;
                default:
            }

            try {
                URL stockApi = new URL(api);
                URLConnection urlConn = stockApi.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                JSONObject history = new JSONObject(stringBuffer.toString());
                JSONObject prices = (JSONObject)history.get(jsonKey);
                for(Iterator iterator = prices.keys(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    Date date=new SimpleDateFormat(pattern).parse(key);
                    datePrices.add(0, new Pair(date, Double.parseDouble( ((String) ((JSONObject)prices.get(key)).get(API_CLOSE_PRICE)))));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return datePrices;
        }
    }
}