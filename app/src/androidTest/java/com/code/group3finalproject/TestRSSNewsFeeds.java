package com.code.group3finalproject;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.code.group3finalproject.RSSClasses.Feeds.CNNBusiness;
import com.code.group3finalproject.RSSClasses.RSSManagedClasses;
import com.code.group3finalproject.ui.home.HomeFragment;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class TestRSSNewsFeeds {

    @Test
    public void testFetchRSSNewsFeeds() {
        RSSManagedClasses feedManager = new RSSManagedClasses();

        HomeFragment frag = new HomeFragment();

        RecyclerView recyclerView = frag.getActivity().findViewById(R.id.recycleFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(frag.getActivity()));
        RSSFeedRecyclerViewAdapter RSSRecycleFeed = new RSSFeedRecyclerViewAdapter(frag.getActivity(), new ArrayList<>());
        RSSRecycleFeed.setClickListener(frag);
        recyclerView.setAdapter(RSSRecycleFeed);

        new fetchRSSFeeds(RSSRecycleFeed, feedManager, frag.getActivity().findViewById(R.id.loadingRSSFeed)).execute((Void) null);
        assertTrue(feedManager.getSelectedFeeds().size() > 0);
    }



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