package com.code.group3finalproject.RSSClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.code.group3finalproject.RSSClasses.Feeds.CNNBusiness;
import com.code.group3finalproject.RSSClasses.Feeds.CNNFinance;
import com.code.group3finalproject.RSSClasses.Feeds.CNNWealth;
import com.code.group3finalproject.RSSClasses.Feeds.FinancialTimes;
import com.code.group3finalproject.RSSClasses.Feeds.Fortune;

import java.io.Serializable;
import java.util.ArrayList;


public class RSSManagedClasses implements Serializable {
    ArrayList<RSSNewsFeed> possibleFeeds;

    /**
     *Constructor
     *
     * Initializes all feeds
     */
    public RSSManagedClasses(){
        this.possibleFeeds = new ArrayList<>();

        //Initialize the available feeds
        this.possibleFeeds.add(new CNNBusiness());
        this.possibleFeeds.add(new CNNFinance());
        this.possibleFeeds.add(new CNNWealth());
        this.possibleFeeds.add(new FinancialTimes());
        this.possibleFeeds.add(new Fortune());

    }

    /**
     *This function returns all included RRS NewsFeeds
     *
     * @return
     * Returns an ArrayList of RRSNewsFeed objects
     */
    public ArrayList<RSSNewsFeed> getSelectedFeeds(){
        ArrayList<RSSNewsFeed> filteredFeeds = new ArrayList<>();
        for (RSSNewsFeed feed: possibleFeeds){
            if(feed.isIncluded()){
                filteredFeeds.add(feed);
            }
        }
        return filteredFeeds;
    }

    /**
     * This function returns all possible feeds
     *
     * @return
     * Returns an ArrayList of RRSNewsFeed objects
     */
    public ArrayList<RSSNewsFeed> getAllFeeds(){
        return this.possibleFeeds;
    }

    /**
     *This function toggles whether or not the feed at
     * the inputted index will be loaded in the news feed.
     * @param index
     * An integer representing the index of the RSS feed
     */
    public void toggleInclusion(int index){
        if (index >= this.possibleFeeds.size() || index < 0){
            return;
        }
        this.possibleFeeds.get(index).toggleInclusion();
    }

    /**
     * This function prints the details about each feed
     */
    public void printFeedNames(){
        for (RSSNewsFeed i : possibleFeeds) {
            Log.d("RSSManaged", i.getRSSFeedName());
            Log.d("RSSManaged", i.getRSSFeedURL());
            Log.d("RSSManaged", i.getIncludeFeed()?"T":"F");
        }
    }

}
