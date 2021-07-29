package com.code.group3finalproject;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.code.group3finalproject.RSSClasses.Feeds.CNNBusiness;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class TestRSSNewsFeeds {

    @Test
    public void generalRSSNewsFeedClass() {

        CNNBusiness myObj = new CNNBusiness();

        //Test expected XML Tags
        assertEquals(myObj.getDescriptionTag(),"description");
        assertEquals(myObj.getHyperLinkTag(),"link");
        assertEquals(myObj.getPublicationDateTag(),"pubDate");
        assertEquals(myObj.getImageLinkTag(),"");
        assertEquals(myObj.getTitleTag(),"title");
        assertEquals(myObj.getItemTag(),"item");


    }

    @Test
    public void textCNNBusiness() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.code.group3finalproject", appContext.getPackageName());
    }

    @Test
    public void textCNNFinance() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.code.group3finalproject", appContext.getPackageName());
    }

    @Test
    public void textCNNWealth() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.code.group3finalproject", appContext.getPackageName());
    }

    @Test
    public void testFinancialTimes() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.code.group3finalproject", appContext.getPackageName());
    }

    @Test
    public void testFortune() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.code.group3finalproject", appContext.getPackageName());
    }

    @Test
    public void testInvesting() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.code.group3finalproject", appContext.getPackageName());
    }

    @Test
    public void testYahooFinance() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.code.group3finalproject", appContext.getPackageName());
    }
}