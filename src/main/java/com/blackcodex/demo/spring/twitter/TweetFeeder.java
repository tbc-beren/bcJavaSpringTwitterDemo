package com.blackcodex.demo.spring.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import twitter4j.*;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TweetFeeder {
    static TweetFeeder mInstance = null;

    @Autowired
    Environment mEnv;

    boolean mEnabled = true;

    @Bean(name = "TweetFeeder")
    public void init() {
        mInstance = this;
        initTwitter();
    }

    public static TweetFeeder instance() {
        return mInstance;
    }

    public int getMinFollowerCount() {
        final String prop = mEnv.getProperty("bcJavaSpringTwitterDemo.minFollowers");
        if (prop == null) {
            return 1500;
        }
        return Integer.parseInt(prop);
    }

    public List<String> getLangFilter() {
        String prop = mEnv.getProperty("bcJavaSpringTwitterDemo.lang");
        if (prop == null) {
            prop = "es,fr,it";
        }

        String[] tokens = prop.split(",");
        List<String> result = new ArrayList<>();

        Collections.addAll(result, tokens);
        result.removeIf(String::isEmpty);

        return result;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    StatusListener mListener = new StatusListener(){
        public void onStatus(Status status) {
            GeoLocation location = status.getGeoLocation();

            if (!mEnabled) {
                return;
            }

            // Filter by follower count
            if (status.getUser().getFollowersCount() < getMinFollowerCount()) {
                return;
            }

            // Filter by lang
            final String lang = status.getLang();
            List<String> langFilter = getLangFilter();
            if (langFilter.size()!=0 && !langFilter.contains(lang)) {
                return;
            }

            TweetModel tweet = new TweetModel(
                    status.getUser().getName(),
                    status.getText(),
                    location == null ? "" : location.toString()
            );

            TweetDataService.add(tweet);

            String tweetLog = String.format("New tweet: [%s][%s]", tweet.getUsername(), tweet.getText());
            System.out.println("_______________________________________________________");
            System.out.println(tweetLog);
        }
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
        public void onScrubGeo(long l, long l1) {}
        public void onStallWarning(StallWarning stallWarning) {}
        public void onException(Exception ex) {}
    };

    private void systemPropertyFromEnv(String propName){
        String value = mEnv.getProperty(propName);
        if (value != null) {
            System.setProperty(propName, value);
        }
    }

    private void initTwitter() {
        systemPropertyFromEnv("twitter4j.debug");
        systemPropertyFromEnv("twitter4j.oauth.consumerKey");
        systemPropertyFromEnv("twitter4j.oauth.consumerSecret");
        systemPropertyFromEnv("twitter4j.oauth.accessToken");
        systemPropertyFromEnv("twitter4j.oauth.accessTokenSecret");

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(mListener);
        twitterStream.sample();
    }

    @PreDestroy
    private void doCleanup() {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.removeListener(mListener);
        twitterStream.shutdown();
    }
}
