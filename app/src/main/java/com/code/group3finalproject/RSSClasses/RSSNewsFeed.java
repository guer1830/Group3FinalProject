package com.code.group3finalproject.RSSClasses;

import java.util.ArrayList;

public class RSSNewsFeed {

    private ArrayList<RSSNewsObject> newsArray;

    public RSSNewsFeed(){
        newsArray = new ArrayList<>();
    }

    public ArrayList<RSSNewsObject> getNewsFeed(){
        return newsArray;
    }

    public void addSelectedRSSFeed(String feedName){

    }

    public void removeSelectedRSSFeed(String feedName){

    }
}
