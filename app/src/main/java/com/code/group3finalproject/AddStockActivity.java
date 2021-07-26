package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AddStockActivity extends AppCompatActivity {
    private static String TAG = "AddStockActivity";
    private AutoCompleteTextView SearchText;
    String company_Symbol;
    String stockURL;
    String Name;
    String Symbol;
    String changedText;
    List<String>stockSymbolList;
    List<String>stockNameList = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockSymbolList = new ArrayList<String>();
        setContentView(R.layout.activity_add_stock);
        SearchText = (AutoCompleteTextView) findViewById(R.id.Searchtext);

        SearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = SearchText.getText().toString();
                Log.i("TypedText", "= "+text);
                changedText = text;

               // if (!changedText.equals("")) {
                    stockNameList.clear();
                    stockSymbolList.clear();
                    new GetContacts().execute();

                //}
            }
        });

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(AddStockActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            Log.i("AddStockActivity","url fetching");
            String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + changedText +"&apikey=A17S0FRD7LSUFI01";

            String jsonStr = sh.makeServiceCall(url);

            Log.i(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    //Log.i("MRN", "json obj made : " + jsonObj.toString());
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("bestMatches");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("1. symbol");
                        Log.i(TAG, "1. symbol\t\t: " + id);
                        String name = c.getString("2. name");
                        Log.i(TAG, "2. name\t: " + name);
                        stockSymbolList.add(id);
                        stockNameList.add(name);
                    }
                    Log.i(TAG, "stockSymbolSIZE......." + stockSymbolList.size());


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Log.i("onPostExecute():","On Post Execute:::::::::::::"+stockSymbolList);
           ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddStockActivity.this,
                    android.R.layout.simple_dropdown_item_1line, stockSymbolList);
            SearchText.setThreshold(0);
            SearchText.setAdapter(adapter);
        }
    }


    public void parseJSON(String response) throws JSONException {
        JSONObject jsonObj = new JSONObject(response);
        JSONObject result = jsonObj.getJSONObject("result");
        Name = result.getString("Name");
        Symbol = result.getString("Symbol");

    }




    public void addStockButton_OnClick(View view) {
        Log.d("AddStockActivity","Add Stock Button clicked");

        EditText stockSymbol = findViewById(R.id.Searchtext);

        StockDatabase db = StockDatabase.getInstance(this.getApplicationContext());
        Stock stock1 = new Stock(stockSymbol.getText().toString());
        db.getStockDAO().insert(stock1);

        finish();
    }
}