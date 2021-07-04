package com.blackcodex.demo.spring.twitter;

import javax.persistence.*;

@Entity
@Table(name = "Tweets")
public class TweetModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    long mId;

    @Column(name = "username", columnDefinition="VARCHAR")
    String mUsername ="";

    @Column(name = "text", columnDefinition="LONGTEXT")
    String mText = "";

    @Column(name = "location", columnDefinition="VARCHAR")
    String mLocation = "";

    @Column(name = "validated")
    boolean mValidated;

    public TweetModel() {
    }

    public TweetModel(long id, String user, String text, String location) {
        mId = id;
        mUsername = user;
        mText = text;
        mLocation = location;
        mValidated = false;
    }

    public long getId() {
        return mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getText() {
        return mText;
    }

    public String getLocation() {
        return mLocation;
    }

    public Boolean isValidated() {
        return mValidated;
    }
}
