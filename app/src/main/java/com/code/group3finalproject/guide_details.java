package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class guide_details extends AppCompatActivity {

    private String text;
    private String gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_details);

        Bundle extras = getIntent().getExtras();
        text = extras.getString("text");
        gif =  extras.getString("gif");

        Log.d("details", text);
        Log.d("details", gif);

        TextView details = findViewById(R.id.guideDetails);
        details.setText(text);


        int resID = getResources().getIdentifier(gif, "drawable", getPackageName());
        GifImageView gifView = findViewById(R.id.guideGIF);
        gifView.setImageResource(resID);
    }
}