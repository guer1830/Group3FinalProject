package com.code.group3finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.code.group3finalproject.RSSClasses.RSSManagedClasses;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RSSFeedManager extends AppCompatActivity implements  RSSManagerRecyclerViewAdapter.OnItemClickListener{

    private RSSManagerRecyclerViewAdapter RSSManagerRecycleFeed;
    private RSSManagedClasses feedManager = null;

    /**
     * On create for class
     * Loads and displys the managed feeds
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed_manager);

        //Load the saved preferences for the feed manager
        try {
            Log.d("IO", "Loading File");
            FileInputStream fis = this.openFileInput("RSS.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            feedManager = (RSSManagedClasses) is.readObject();
            is.close();
            fis.close();
            Log.d("IO", "Loaded File");

            if(feedManager == null){
                feedManager = new RSSManagedClasses();
            }
        } catch (FileNotFoundException e) {
            Log.d("IO", e.toString());
            e.printStackTrace();
            feedManager = new RSSManagedClasses();
        } catch (IOException e) {
            e.printStackTrace();
            feedManager = new RSSManagedClasses();
            Log.d("IO", e.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.RSSManagerRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RSSManagerRecycleFeed = new RSSManagerRecyclerViewAdapter(this, feedManager.getAllFeeds());
        RSSManagerRecycleFeed.setClickListener((RSSManagerRecyclerViewAdapter.OnItemClickListener) this);
        recyclerView.setAdapter(RSSManagerRecycleFeed);


        CharSequence text = "Modify Feed Settings";
        int duration = Snackbar.LENGTH_LONG;
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, text, duration);
        mySnackbar.show();
    }

    /**
     * function that returns to the RSS News feed
     * @param view
     */
    public void returnToRSSFeed(View view){
        Log.d("MessageTag","returning to main");
        Intent intent = new Intent(this,MainActivity.class);


        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        FileOutputStream fos;
        try {
            fos = this.openFileOutput("RSS.dat", Activity.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(feedManager);
            os.close();
            fos.close();
            Log.d("RSSFeedManager", "Saved File");
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}