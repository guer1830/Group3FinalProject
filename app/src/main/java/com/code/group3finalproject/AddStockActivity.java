package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

public class AddStockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
    }

    public void addStockButton_OnClick(View view) {
        Log.d("AddStockActivity","Add Stock Button clicked");

        EditText stockSymbol = findViewById(R.id.editStockSymbol);

        StockDatabase db = StockDatabase.getInstance(this.getApplicationContext());
        Stock stock1 = new Stock(stockSymbol.getText().toString());
        db.getStockDAO().insert(stock1);

        finish();
    }
}