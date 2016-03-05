package com.example.karan.querydescription;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private static final String TWITTER_CONSUMER_KEY = "nfwunbymv27sogLQHkjAbx5k3";
    private static final String TWITTER_CONSUMER_SECRET = "jlw3XMNRqcprxYFxaZW4bKaE8blmelPFBPY2TFuImvDRGCaciC";
    ImageView txt;
    public List<String> descriptions = new ArrayList<String>();
    public String name;
    public ArrayList<Drawable> draws = new ArrayList<>();
    public Intent mServiceIntent;
    public String usernameperson;
    final private Configuration twitConf = new ConfigurationBuilder()
            .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
            .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
            .setOAuthAccessToken("2928799379-ELDcLocHYVj9NwqK5VpG0Yr2QrX4vZK9A0mt3Zn")
            .setOAuthAccessTokenSecret("rwO6FQPL42YGeLgVg1NOZSlCFHs0SO07qv4eNLOX8o9XP")
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Twitter twitter = new TwitterFactory(twitConf).getInstance();

        Query query = new Query().geoCode(new GeoLocation(37.7680, -78.2055), 1829700625, "mi");

       query(twitter, query, listView);
        query1(twitter,query,listView);



    }

    private void query1(Twitter twitter, Query query, ListView listView) {
        query.count(100);
        query.setCount(100);

        try {
            int w = 0;
            QueryResult result = twitter.search(query);
            List<Status> tweets = result.getTweets();


            while (w < tweets.size()) {

                Status status = tweets.get(w);
                String description = status.getUser().getDescription();
                InputStream is = (InputStream) new URL(status.getUser().getProfileImageURL()).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                if (description.toUpperCase().contains("TJ")) {
                    descriptions.add(description);
                    draws.add(d);
                }
                w++;
            }
            CustomArrayAdapter adapter = new CustomArrayAdapter(this, descriptions, draws);
            listView.setAdapter(adapter);


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void query(Twitter twitter, Query query, ListView listView) {
        query.count(100);
        query.setCount(100);

        try {
            int w = 0;
            QueryResult result = twitter.search(query);
            List<Status> tweets = result.getTweets();


            while (w < tweets.size()) {

                Status status = tweets.get(w);
                String description = status.getUser().getDescription();
                InputStream is = (InputStream) new URL(status.getUser().getProfileImageURL()).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                if (description.toUpperCase().contains("TJ")) {
                    descriptions.add(description);

                    draws.add(d);
                }
                Log.w("TWEET", tweets.get(w).toString());
                Log.w("TWEETDESCRIP", description);


                w++;


            }
            CustomArrayAdapter adapter = new CustomArrayAdapter(this, descriptions, draws);
            listView.setAdapter(adapter);


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
