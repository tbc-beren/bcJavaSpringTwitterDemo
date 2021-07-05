package com.blackcodex.demo.spring.twitter;

import javax.persistence.*;

@Entity
@Table(name = "Tweets")
public class TweetModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long mId;

    @Column(name = "username", columnDefinition="VARCHAR")
    private String mUsername ="";

    @Column(name = "text", columnDefinition="LONGTEXT")
    private String mText = "";

    @Column(name = "location", columnDefinition="VARCHAR")
    private String mLocation = "";

    @Column(name = "validated")
    boolean mValidated;

    public TweetModel() {
        mValidated = false;
    }

    public TweetModel(long id, String user, String text, String location) {
        this();

        mId = id;
        mUsername = user;
        mText = text;
        mLocation = location;
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
