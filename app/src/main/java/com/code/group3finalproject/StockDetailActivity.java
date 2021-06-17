package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);
        Button button = findViewById(R.id.test_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.time_range);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton timeRange = (RadioButton) findViewById(selectedId);

                Calendar from = getCalendarFrom(timeRange.getText().toString());
                Calendar to = Calendar.getInstance();

                List<HistoricalQuote> histories = getStockHistories("PYR", from, to);

                ArrayList<DataPoint> dps = new ArrayList<DataPoint>();

                double maxPrice = 0;
                double minPrice = Double.MAX_VALUE;
                long maxDate = 0;
                long minDate = Long.MAX_VALUE;

                for (HistoricalQuote hq : histories) {
                    dps.add(new DataPoint(hq.getDate().getTime(), hq.getClose().doubleValue()));
                    maxPrice = Math.max(hq.getClose().doubleValue(), maxPrice);
                    minPrice = Math.min(hq.getClose().doubleValue(), minPrice);
                    maxDate = Math.max(hq.getDate().getTime().getTime(), maxDate);
                    minDate = Math.min(hq.getDate().getTime().getTime(), minDate);
                }

                //TODO the graph need to be seriously tuned.
                LineGraphSeries <DataPoint> series = new LineGraphSeries<DataPoint>(dps.toArray(new DataPoint[0]));
                graph.addSeries(series);
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(StockDetailActivity.this));
                graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);

                graph.getViewport().setMinX(minDate);
                graph.getViewport().setMaxX(maxDate);
                graph.getViewport().setMaxY(maxPrice*1.1);
                graph.getViewport().setMinY(Math.max(0, minPrice/1.1));

            }
        });
    }

    protected List<HistoricalQuote> getStockHistories(String symbol, Calendar from, Calendar to) {
        try {
            List<HistoricalQuote> histories = (List<HistoricalQuote>) new StockAPITask(symbol, STOCK_COMMAND.HISTORY, from, to).execute().get();
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
            stock = (Stock) new StockAPITask(symbol, STOCK_COMMAND.INFO, null, null).execute().get();
        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return stock;
    }

    protected Calendar getCalendarFrom(String range) {
        Calendar from = Calendar.getInstance();

        if (range.equals("1D")) {
            //TODO daily is not working yet
            from.add(Calendar.DAY_OF_YEAR, -1);
        } else if (range.equals("1W")) {
            from.add(Calendar.DAY_OF_YEAR, -7);
        } else if (range.equals("1M")) {
            from.add(Calendar.MONTH, -1);
        } else if (range.equals("3M")) {
            from.add(Calendar.MONTH, -3);
        } else if (range.equals("1Y")) {
            from.add(Calendar.YEAR, -1);
        } else if (range.equals("All")) {
            from.add(Calendar.YEAR, -100);
        }

        return from;
    }

    private class StockAPITask extends AsyncTask<Void, Void, Object> {
        String symbol;
        Calendar from;
        Calendar to;
        STOCK_COMMAND command;

        StockAPITask(String symbol, STOCK_COMMAND command, Calendar from, Calendar to) {
            // list all the parameters like in normal class define
            this.symbol = symbol;
            this.from = from;
            this.to = to;
            this.command = command;
        }

        @Override
        protected void onPostExecute(Object s) {
            super.onPostExecute(s);
        }

        @Override
        protected Object doInBackground(Void... params) {
            try {
                Stock stock = null;

                //https://github.com/sfuhrm/yahoofinance-api
                switch (this.command) {
                    case INFO:
                        stock = YahooFinance.get(this.symbol);
                        return stock;
                    case HISTORY:
                        stock = YahooFinance.get(symbol, from, to, Interval.DAILY);
                        if (stock != null) {
                            return stock.getHistory();
                        }
                    default:
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}