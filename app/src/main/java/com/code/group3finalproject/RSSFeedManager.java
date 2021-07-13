package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RSSFeedManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed_manager);
    }

    public void returnToRSSFeed(View view){
        Log.d("MessageTag","returning to main");
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}