package com.blackcodex.demo.spring.twitter;

import com.blackcodex.demo.spring.twitter.repository.TweetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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

    public static TweetModel add(int id, String username, String text) {
        final TweetsRepository repo = getTweetrepo();
        if (repo == null) {
            return null;
        }
        try {
            TweetModel item = new TweetModel(id, username, text, "");
            repo.save(item);
        } catch(Exception ignored) {
        }
        return null;
    }
}
