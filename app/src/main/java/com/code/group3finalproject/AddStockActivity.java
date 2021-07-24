package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

import java.util.concurrent.*;

public class AddStockActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.code.group3finalproject.AddStockActivity.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
    }

    public void addStockButton_OnClick(View view) {
        Log.d("AddStockActivity","Add Stock Button clicked");

        EditText mStockSymbol = findViewById(R.id.editStockSymbol);

        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mStockSymbol.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String word = mStockSymbol.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(RESULT_OK, replyIntent);
        }

        finish();
    }
}