package com.code.group3finalproject.APIEndPoints;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.xmlpull.v1.XmlPullParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class QuoteAPIEndPoint extends AsyncTask<String, Integer, String> {
    private String mAPIKey;
    private String mQuoteURL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE";
    private String mSymbol = "";
    TextView mPriceText;
    private String stockPrice = "0.00";

    public QuoteAPIEndPoint(String symbol, String APIKey, TextView textView) {
        mSymbol = symbol;
        mAPIKey = APIKey;
        mPriceText = textView;
    }

    public String getAPIKey() {
        return this.mAPIKey;
    }

    public void setAPIKey(String APIKey) {
        this.mAPIKey = APIKey;
    }

    public String getSymbol() {
        return this.mSymbol;
    }

    public void setSymbol(String symbol) {
        this.mSymbol = symbol;
    }

    /**
     * @param strings
     * @deprecated
     */
    @Override
    protected String doInBackground(String... strings) {
        String EndPointURL = mQuoteURL + "&symbol=" + mSymbol + "&apikey=" + mAPIKey;

        try {
            URL url = new URL(EndPointURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            InputStream in = conn.getInputStream();
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("Global Quote")) {
                    JsonReader quote = reader;
                    quote.beginObject();
                    while (quote.hasNext()) {
                        String quoteName = quote.nextName();
                        Log.i("Global Quote", quoteName);
                        if (quoteName.equals("05. price")) {
                            stockPrice = reader.nextString();
                        } else {
                            quote.skipValue();
                        }
                    }
                    quote.endObject();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (Exception ex) {
            ex.printStackTrace();
            stockPrice = "0.00";
        }
        return "";
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mPriceText.setText("$ " + stockPrice);
    }
}
