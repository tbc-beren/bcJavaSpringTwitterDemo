package com.blackcodex.demo.spring.twitter;

import com.blackcodex.demo.spring.twitter.repository.TweetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class TweetService {

    private static TweetService mInstance = null;

    @Autowired
    private TweetsRepository mTweetsRepo;

    @Autowired
    public void onPostConstruct() {
        mInstance = this;
    }

    private static TweetsRepository getTweetrepo() {
        return mInstance.mTweetsRepo;
    }

    public static TweetModel validate(long tweetId) {
        final TweetsRepository repo = getTweetrepo();
        if (repo == null) {
            return null;
        }
        try {
            Optional<TweetModel> optItem = repo.findById(tweetId);
            if (optItem.isPresent()) {
                TweetModel item = optItem.get();
                item.mValidated = true;
                repo.save(item);
                return item;
            }
        } catch(Exception ignored) {
        }
        return null;
    }
}
