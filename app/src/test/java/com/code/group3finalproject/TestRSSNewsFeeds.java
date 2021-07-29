package com.code.group3finalproject;

import android.content.Context;

import com.code.group3finalproject.RSSClasses.Feeds.CNNBusiness;
import com.code.group3finalproject.RSSClasses.Feeds.CNNFinance;
import com.code.group3finalproject.RSSClasses.Feeds.CNNWealth;
import com.code.group3finalproject.RSSClasses.Feeds.FinancialTimes;
import com.code.group3finalproject.RSSClasses.Feeds.Fortune;
import com.code.group3finalproject.RSSClasses.Feeds.Investing;
import com.code.group3finalproject.RSSClasses.Feeds.YahooFinance;
import com.code.group3finalproject.RSSClasses.RSSManagedClasses;
import com.code.group3finalproject.RSSClasses.RSSNewsFeed;
import com.code.group3finalproject.RSSClasses.RSSNewsObject;

import org.junit.Test;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestRSSNewsFeeds {

    @Test
    public void generalRSSNewsObjectClass(){
        RSSNewsObject myObj = new RSSNewsObject();

        //Test Base Case
        assertEquals(myObj.getArticleURL(),"");
        assertEquals(myObj.getImageURL(),"");
        assertEquals(myObj.getNewsDescription(),"");
        assertEquals(myObj.getNewsTitle(),"");
        assertEquals(myObj.getPublicationDate(),"");

        //Test Setters
        String url = "test.html";
        String imageURL = "image.png";
        String description = "Test news description";
        String title = "Hello";
        String pubdate = "today";
        myObj.setArticleURL(url);
        myObj.setImageURL(imageURL);
        myObj.setNewsDescription(description);
        myObj.setNewsTitle(title);
        myObj.setPublicationDate(pubdate);

        //Confirm that setting functions are working
        assertEquals(myObj.getArticleURL(),url);
        assertEquals(myObj.getImageURL(),imageURL);
        assertEquals(myObj.getNewsDescription(),description);
        assertEquals(myObj.getNewsTitle(),title);
        assertEquals(myObj.getPublicationDate(),pubdate);


    }


    @Test
    public void generalRSSManagedClasses(){
        RSSManagedClasses myObj = new RSSManagedClasses();
        ArrayList<RSSNewsFeed> feeds = myObj.getAllFeeds();
        ArrayList<RSSNewsFeed> filtered = myObj.getSelectedFeeds();
        assertEquals(feeds,filtered);

        //Test filtered feed selection
        myObj.toggleInclusion(1);
        feeds = myObj.getAllFeeds();
        filtered = myObj.getSelectedFeeds();
        assertNotEquals(feeds,filtered);

        //Test toggle Inclusion
        myObj.toggleInclusion(-1);
        myObj.toggleInclusion(11);
        myObj.toggleInclusion(1);
        feeds = myObj.getAllFeeds();
        filtered = myObj.getSelectedFeeds();
        assertEquals(feeds,filtered);

    }

    @Test
    public void generalRSSNewsFeedClass() {

        RSSNewsFeed myObj = new RSSNewsFeed();

        //Test expected XML Tags
        assertEquals(myObj.getDescriptionTag(),"");
        assertEquals(myObj.getHyperLinkTag(),"");
        assertEquals(myObj.getPublicationDateTag(),"");
        assertEquals(myObj.getImageLinkTag(),"");
        assertEquals(myObj.getTitleTag(),"");
        assertEquals(myObj.getItemTag(),"");

        //Test URL
        assertEquals(myObj.getRSSFeedName(),"");
        assertEquals(myObj.getRSSFeedURL(),"");

        //Inlcusion Toggle
        assertEquals(myObj.isIncluded(),true);
        myObj.toggleInclusion();
        assertEquals(myObj.isIncluded(),false);

    }

    @Test
    public void textCNNBusiness() {
        CNNBusiness myObj = new CNNBusiness();

        //Test expected XML Tags
        assertEquals(myObj.getDescriptionTag(),"description");
        assertEquals(myObj.getHyperLinkTag(),"link");
        assertEquals(myObj.getPublicationDateTag(),"pubDate");
        assertEquals(myObj.getImageLinkTag(),"");
        assertEquals(myObj.getTitleTag(),"title");
        assertEquals(myObj.getItemTag(),"item");

        //Test URL
        assertEquals(myObj.getRSSFeedName(),"CNN Business");
        assertEquals(myObj.getRSSFeedURL(),"https://www.cnbc.com/id/10001147/device/rss/rss.html");

        //Inlcusion Toggle
        assertEquals(myObj.isIncluded(),true);
        myObj.toggleInclusion();
        assertEquals(myObj.isIncluded(),false);
    }

    @Test
    public void textCNNFinance() {
        CNNFinance myObj = new CNNFinance();

        //Test expected XML Tags
        assertEquals(myObj.getDescriptionTag(),"description");
        assertEquals(myObj.getHyperLinkTag(),"link");
        assertEquals(myObj.getPublicationDateTag(),"pubDate");
        assertEquals(myObj.getImageLinkTag(),"");
        assertEquals(myObj.getTitleTag(),"title");
        assertEquals(myObj.getItemTag(),"item");

        //Test URL
        assertEquals(myObj.getRSSFeedName(),"CNN Finance");
        assertEquals(myObj.getRSSFeedURL(),"https://www.cnbc.com/id/10000664/device/rss/rss.html");

        //Inlcusion Toggle
        assertEquals(myObj.isIncluded(),true);
        myObj.toggleInclusion();
        assertEquals(myObj.isIncluded(),false);
    }

    @Test
    public void textCNNWealth() {
        CNNWealth myObj = new CNNWealth();

        //Test expected XML Tags
        assertEquals(myObj.getDescriptionTag(),"description");
        assertEquals(myObj.getHyperLinkTag(),"link");
        assertEquals(myObj.getPublicationDateTag(),"pubDate");
        assertEquals(myObj.getImageLinkTag(),"");
        assertEquals(myObj.getTitleTag(),"title");
        assertEquals(myObj.getItemTag(),"item");

        //Test URL
        assertEquals(myObj.getRSSFeedName(),"CNN Wealth");
        assertEquals(myObj.getRSSFeedURL(),"https://www.cnbc.com/id/10001054/device/rss/rss.html");

        //Inlcusion Toggle
        assertEquals(myObj.isIncluded(),true);
        myObj.toggleInclusion();
        assertEquals(myObj.isIncluded(),false);
    }

    @Test
    public void testFinancialTimes() {
        FinancialTimes myObj = new FinancialTimes();

        //Test expected XML Tags
        assertEquals(myObj.getDescriptionTag(),"description");
        assertEquals(myObj.getHyperLinkTag(),"link");
        assertEquals(myObj.getPublicationDateTag(),"pubDate");
        assertEquals(myObj.getImageLinkTag(),"");
        assertEquals(myObj.getTitleTag(),"title");
        assertEquals(myObj.getItemTag(),"item");

        //Test URL
        assertEquals(myObj.getRSSFeedName(),"Financial Times");
        assertEquals(myObj.getRSSFeedURL(),"https://www.ft.com/?format=rss");

        //Inlcusion Toggle
        assertEquals(myObj.isIncluded(),true);
        myObj.toggleInclusion();
        assertEquals(myObj.isIncluded(),false);
    }

    @Test
    public void testFortune() {
        Fortune myObj = new Fortune();

        //Test expected XML Tags
        assertEquals(myObj.getDescriptionTag(),"description");
        assertEquals(myObj.getHyperLinkTag(),"link");
        assertEquals(myObj.getPublicationDateTag(),"pubDate");
        assertEquals(myObj.getImageLinkTag(),"");
        assertEquals(myObj.getTitleTag(),"title");
        assertEquals(myObj.getItemTag(),"item");

        //Test URL
        assertEquals(myObj.getRSSFeedName(),"Fortune");
        assertEquals(myObj.getRSSFeedURL(),"https://fortune.com/feed");

        //Inlcusion Toggle
        assertEquals(myObj.isIncluded(),true);
        myObj.toggleInclusion();
        assertEquals(myObj.isIncluded(),false);
    }

    @Test
    public void testInvesting() {
        Investing myObj = new Investing();

        //Test expected XML Tags
        assertEquals(myObj.getDescriptionTag(),"description");
        assertEquals(myObj.getHyperLinkTag(),"link");
        assertEquals(myObj.getPublicationDateTag(),"pubDate");
        assertEquals(myObj.getImageLinkTag(),"");
        assertEquals(myObj.getTitleTag(),"title");
        assertEquals(myObj.getItemTag(),"item");

        //Test URL
        assertEquals(myObj.getRSSFeedName(),"Investing");
        assertEquals(myObj.getRSSFeedURL(),"https://www.investing.com/rss/news.rss");

        //Inlcusion Toggle
        assertEquals(myObj.isIncluded(),true);
        myObj.toggleInclusion();
        assertEquals(myObj.isIncluded(),false);
    }

    @Test
    public void testYahooFinance() {
        YahooFinance myObj = new YahooFinance();

        //Test expected XML Tags
        assertEquals(myObj.getDescriptionTag(),"description");
        assertEquals(myObj.getHyperLinkTag(),"link");
        assertEquals(myObj.getPublicationDateTag(),"pubDate");
        assertEquals(myObj.getImageLinkTag(),"");
        assertEquals(myObj.getTitleTag(),"title");
        assertEquals(myObj.getItemTag(),"item");

        //Test URL
        assertEquals(myObj.getRSSFeedName(),"Yahoo Finance");
        assertEquals(myObj.getRSSFeedURL(),"https://finance.yahoo.com/news/rssindex");

        //Inlcusion Toggle
        assertEquals(myObj.isIncluded(),true);
        myObj.toggleInclusion();
        assertEquals(myObj.isIncluded(),false);
    }
}