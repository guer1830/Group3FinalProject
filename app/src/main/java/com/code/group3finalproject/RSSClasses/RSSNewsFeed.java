package com.code.group3finalproject.RSSClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class RSSNewsFeed implements Parcelable {

    private String RSSFeedURL;
    private String includeFeed;


    protected RSSNewsFeed(Parcel in) {
        RSSFeedURL = in.readString();
        includeFeed = in.readString();
    }

    public RSSNewsFeed() {
        this.RSSFeedURL = "";
        this.includeFeed = "True";
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(RSSFeedURL);
        dest.writeString(includeFeed);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RSSNewsFeed> CREATOR = new Creator<RSSNewsFeed>() {
        @Override
        public RSSNewsFeed createFromParcel(Parcel in) {
            return new RSSNewsFeed(in);
        }

        @Override
        public RSSNewsFeed[] newArray(int size) {
            return new RSSNewsFeed[size];
        }
    };

    public String getRSSFeedURL(){
        return this.RSSFeedURL;
    }

    public boolean isIncluded(){
        return this.includeFeed == "True";
    }
}
