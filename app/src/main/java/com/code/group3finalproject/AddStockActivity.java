package com.code.group3finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    static List<String>stockSymbolList;
    static List<String>stockNameList = new ArrayList<String>();
    int count = 0;

    public static final String EXTRA_REPLY = "com.code.group3finalproject.AddStockActivity.REPLY";

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
                //}
                if(count <= 5) {
                    new GetContacts(changedText).execute();
                    count++;
                } else {
                    Toast.makeText(AddStockActivity.this,"!!!Exceeded 5 API Calls for stock search!!!",Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddStockActivity.this,
                        android.R.layout.simple_list_item_1, stockSymbolList);
                SearchText.setThreshold(0);
                SearchText.setAdapter(adapter);


            }
        });

    }

    protected static class GetContacts extends AsyncTask<Void, Void, Void> {
        String changedText;
        GetContacts(String changedText) {
             this.changedText = changedText;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            JSONArray contacts = doBGWork();
            try {
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

            } catch(Exception e) {
                //
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.i("onPostExecute():","On Post Execute:::::::::::::"+stockSymbolList);

        }

        protected JSONArray doBGWork() {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //Log.i("AddStockActivity","url fetching");
            String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + this.changedText +"&apikey=A17S0FRD7LSUFI01";

            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("bestMatches");
                    return contacts;
                    // looping through All Contacts

                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                }

            } else {
                //Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
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

        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(stockSymbol.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String word = stockSymbol.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(RESULT_OK, replyIntent);
        }

        finish();
    }
}