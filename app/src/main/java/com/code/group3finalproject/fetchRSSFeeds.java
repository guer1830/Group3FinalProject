package com.code.group3finalproject;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.code.group3finalproject.RSSClasses.RSSNewsObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class fetchRSSFeeds extends AsyncTask<Void, Void, Boolean> {
    private ArrayList<RSSNewsObject> recyclerObjects;
    private RSSFeedRecyclerViewAdapter recyclerAdapter;

    public fetchRSSFeeds(RSSFeedRecyclerViewAdapter adapter){
        this.recyclerAdapter = adapter;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try{
            //Iterate over each selected RSS Feed

            String urlString = "https://www.cnbc.com/id/10001054/device/rss/rss.html";
            URL url = new URL(urlString);
            InputStream inputStream = url.openConnection().getInputStream();
            this.recyclerObjects = parseXMLFeed(inputStream);
            return true;
        }
        catch (IOException | XmlPullParserException e){
            Log.e("Error","Error",e);
        }
        return false;
    }


    @Override
    protected void onPostExecute(Boolean success) {
        this.recyclerAdapter.setData(this.recyclerObjects);
    }

    public ArrayList<RSSNewsObject> parseXMLFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String description = null;
        String hyperLink = null;
        String site = null;
        String publicationDate = null;
        String imageLink = null;
        Boolean isObject = false;
        RSSNewsObject RSSNewsObject = new RSSNewsObject();
        ArrayList<RSSNewsObject> answerArr = new ArrayList<>();

        try{
            //Create the XML Parser
            XmlPullParser feedParser = Xml.newPullParser();
            feedParser.setInput(inputStream,null);

            //Advance into the main XML body
            //feedParser.nextTag();
            while(feedParser.next() != XmlPullParser.END_DOCUMENT){
                int eventType = feedParser.getEventType();
                String tagName = feedParser.getName();

                if(tagName == null){
                    continue;
                }
                //Log.d("XML",tagName);
                if(eventType == XmlPullParser.END_TAG){
                    if(tagName.equalsIgnoreCase("item")){
                        isObject = false;
                        title = null;
                        description = null;
                        hyperLink = null;
                        imageLink = null;
                        publicationDate = null;
                    }
                    continue;
                }

                if(eventType == XmlPullParser.START_TAG){
                    if(tagName.equalsIgnoreCase("item")) {
                        isObject = true;
                        continue;
                    }
                }

                String currentTagText = "";

                if(feedParser.next() == XmlPullParser.TEXT){
                    currentTagText= feedParser.getText();
                    feedParser.nextTag();
                }

                if (tagName.equalsIgnoreCase("title")){
                    title = currentTagText;
                }
                else if (tagName.equalsIgnoreCase("link")){
                    hyperLink = currentTagText;
                }
                else if (tagName.equalsIgnoreCase("description")){
                    description = currentTagText;
                }
                else if (tagName.equalsIgnoreCase("pubDate")){
                    publicationDate = currentTagText;
                }

                if(checkIfObjectIsReady(RSSNewsObject,title,description,hyperLink,imageLink,publicationDate) && isObject){
                    RSSNewsObject.setNewsTitle(title);
                    RSSNewsObject.setNewsDescription(description);
                    RSSNewsObject.setArticleURL(hyperLink);
                    RSSNewsObject.setImageURL(imageLink);
                    RSSNewsObject.setPublicationDate(publicationDate);
                    Log.d("XMLContent",title);
                    Log.d("XMLContent",description);
                    Log.d("XMLContent",hyperLink);
                    Log.d("XMLContent",publicationDate);
                    title = null;
                    description = null;
                    hyperLink = null;
                    imageLink = null;
                    publicationDate = null;
                    answerArr.add(RSSNewsObject);
                    RSSNewsObject = new RSSNewsObject();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
        }
        return answerArr;
    }


    private Boolean checkIfObjectIsReady(RSSNewsObject object, String title, String description, String articlLink, String imageLink, String publicationDate){
        Boolean isReady = true;
        if (object.isTitleRequired()){
            isReady = (isReady & title != null);
        }
        if (object.isDescriptionRequired()){
            isReady = (isReady & description != null);
        }
        if (object.isArticleLinkRequired()){
            isReady = (isReady & articlLink != null);
        }
        if (object.isImageRequired()){
            isReady = (isReady & imageLink != null);
        }
        if (object.isDateRequired()){
            isReady = (isReady & publicationDate != null);
        }

        return isReady;
    }
}