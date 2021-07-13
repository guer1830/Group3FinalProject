package com.code.group3finalproject.RSSClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.code.group3finalproject.RSSClasses.Feeds.CNNBusiness;
import com.code.group3finalproject.RSSClasses.Feeds.CNNFinance;
import com.code.group3finalproject.RSSClasses.Feeds.CNNWealth;

import java.util.ArrayList;

public class RSSManagedClasses implements Parcelable {
    ArrayList<RSSNewsFeed> possibleFeeds;

    public RSSManagedClasses(){
        this.possibleFeeds = new ArrayList<>();

        //Initialize the available feeds
        this.possibleFeeds.add(new CNNBusiness());
        this.possibleFeeds.add(new CNNFinance());
        this.possibleFeeds.add(new CNNWealth());

    }

    protected RSSManagedClasses(Parcel in) {
        this.possibleFeeds = in.readArrayList(null);
    }

    public static final Creator<RSSManagedClasses> CREATOR = new Creator<RSSManagedClasses>() {
        @Override
        public RSSManagedClasses createFromParcel(Parcel in) {
            return new RSSManagedClasses(in);
        }

        @Override
        public RSSManagedClasses[] newArray(int size) {
            return new RSSManagedClasses[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.possibleFeeds);
    }


    public ArrayList<RSSNewsFeed> getSelectedFeeds(){
        ArrayList<RSSNewsFeed> filteredFeeds = new ArrayList<>();
        for (RSSNewsFeed feed: possibleFeeds){
            if(feed.isIncluded()){
                filteredFeeds.add(feed);
            }
        }
        return filteredFeeds;
    }

}
