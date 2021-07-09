package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class StockDetailActivity extends AppCompatActivity {

    enum STOCK_COMMAND {
        INFO,
        HISTORY
    }

    private List<Pair<Date, Double>> dailyClosePrices =new ArrayList<Pair<Date, Double>>();
    final private String API_PRICE_JSON_KEY = "Time Series (Daily)";
    final private String API_CLOSE_PRICE = "4. close";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.time_range);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            String stockSymbol = "";
            Intent intent = getIntent();

            if (dailyClosePrices.isEmpty()) {
                if (intent.hasExtra("StockSymbol")) {
                    stockSymbol = intent.getStringExtra("StockSymbol");
                    Log.d(getClass().getSimpleName(),stockSymbol);
                }
                String finalStockSymbol = stockSymbol;
                dailyClosePrices = getStockHistories(finalStockSymbol);
            }

            // checkedId is the RadioButton selected
            RadioButton rb=(RadioButton)findViewById(checkedId);
            String range = (String) rb.getText();
            Integer offsets = 1;
            if (range.equals("1D")) {
                //TODO daily is not working yet
                offsets = 1;
            } else if (range.equals("1W")) {
                offsets = 7;
            } else if (range.equals("1M")) {
                offsets = 30;
            } else if (range.equals("3M")) {
                offsets = 90;
            } else if (range.equals("1Y")) {
                offsets = 365;
            } else if (range.equals("All")) {
                offsets = dailyClosePrices.size();
            }
            offsets = Math.min(offsets, dailyClosePrices.size());

            ArrayList<DataPoint> dps = new ArrayList<DataPoint>();

            double maxPrice = 0;
            double minPrice = Double.MAX_VALUE;
            Date maxDate = dailyClosePrices.get(dailyClosePrices.size()-1).first;
            Date minDate = dailyClosePrices.get(dailyClosePrices.size()-offsets).first;;

            for (int i = dailyClosePrices.size() - offsets; i < dailyClosePrices.size(); i++) {
                Pair<Date, Double> datePrice = dailyClosePrices.get(i);
                dps.add(new DataPoint(datePrice.first, datePrice.second));
                maxPrice = Math.max(datePrice.second, maxPrice);
                minPrice = Math.min(datePrice.second, minPrice);
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
        });

        radioGroup.check(R.id.time_range_week);
    }

    protected List<Pair<Date, Double>> getStockHistories(String symbol) {
        try {
            List<Pair<Date, Double>> histories = (List<Pair<Date, Double>>) new StockAPITask(symbol, STOCK_COMMAND.HISTORY).execute().get();
            return histories;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    protected Stock getStockInfo(String symbol) {
        Stock stock = null;
        try {
            stock = (Stock) new StockAPITask(symbol, STOCK_COMMAND.INFO).execute().get();
        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return stock;
    }

    private class StockAPITask extends AsyncTask<Void, Void, Object> {
        String symbol;
        STOCK_COMMAND command;

        StockAPITask(String symbol, STOCK_COMMAND command) {
            // list all the parameters like in normal class define
            this.symbol = symbol;
            this.command = command;
        }

        @Override
        protected void onPostExecute(Object s) {
            super.onPostExecute(s);
        }

        @Override
        protected Object doInBackground(Void... params) {
            List<Pair<Date, Double>> datePrices =new ArrayList<Pair<Date, Double>>();

            //https://github.com/sfuhrm/yahoofinance-api
            switch (this.command) {
                case INFO:
                    return null;
                case HISTORY:
                    final String AlphaAdvantageAPI = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + this.symbol + "&outputsize=full&apikey=BB7UNOS363WF0ETZ";

                    try {
                        URL stockApi = new URL(AlphaAdvantageAPI);
                        URLConnection urlConn = stockApi.openConnection();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = bufferedReader.readLine()) != null)
                        {
                            stringBuffer.append(line);
                        }

                        JSONObject history = new JSONObject(stringBuffer.toString());
                        JSONObject prices = (JSONObject)history.get(API_PRICE_JSON_KEY);
                        for(Iterator iterator = prices.keys(); iterator.hasNext();) {
                            String key = (String) iterator.next();
                            Date date=new SimpleDateFormat("yyyy-MM-dd").parse(key);
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
                default:
            }
            return datePrices;
        }
    }
}