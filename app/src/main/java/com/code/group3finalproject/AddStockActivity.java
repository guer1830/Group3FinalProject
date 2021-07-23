package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.app.AlertDialog;

import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AddStockActivity extends AppCompatActivity {

    private AutoCompleteTextView SearchText;
    String company_Symbol;
    String stockURL;
    String Name;
    String Symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        SearchText = (AutoCompleteTextView) findViewById(R.id.Searchtext);
        SearchText.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            company_Symbol= SearchText.getText().toString();
            stockURL = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=tesco&apikey=demo";
            if(company_Symbol.length() > 0) {
                new MyAsyncTask().execute(stockURL);
            }
            else {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddStockActivity.this);
                builder.setTitle("error");
                builder.setPositiveButton("Okay",null);
                builder.setMessage("No information available");
                AlertDialog theAlertDialog = builder.create();
                theAlertDialog.show();

            }
        }
        });
    }


    public class MyAsyncTask extends AsyncTask<String, String, String>
    {
        protected String doInBackground(String... args)
        {
            String response = null;
            try{
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpEntity httpEntity = null;
                HttpResponse httpResponse = null;

                HttpGet httpGet = new HttpGet(args[0]);

                httpResponse = httpClient.execute(httpGet);
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);



            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                parseJSON(result);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Do anything with response..
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