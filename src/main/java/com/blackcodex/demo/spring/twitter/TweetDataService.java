package com.blackcodex.demo.spring.twitter;

import com.blackcodex.demo.spring.twitter.repository.TweetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetDataService {

    private static TweetDataService mInstance = null;

    @Autowired
    private TweetsRepository mTweetsRepo;

    @Autowired
    public void onPostConstruct() {
        mInstance = this;
    }

    private static TweetsRepository getTweetrepo() {
        return mInstance.mTweetsRepo;
    }

    public static void deleteAll() {
        final TweetsRepository repo = getTweetrepo();
        if (repo != null) {
            repo.deleteAll();
        }
    }

    public static List<TweetModel> enumAll() {
        final TweetsRepository repo = getTweetrepo();
        try {
            return repo.enumAll();
        } catch (Exception ignored) {
        }
        return null;
    }
    public static TweetModel validate(long tweetId) {
        final TweetsRepository repo = getTweetrepo();
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

    public static TweetModel add(String username, String text) {
        final TweetsRepository repo = getTweetrepo();
        try {
            TweetModel item = new TweetModel(username, text, "");
            repo.save(item);
            return item;
        } catch(Exception ignored) {
        }
        return null;
    }

    public static List<TweetModel> queryValidatedByUser(String username) {
        final TweetsRepository repo = getTweetrepo();
        try {
            return repo.find(username);
        } catch (Exception ignored) {
        }
        return null;
    }

    public TweetsRepository setTweetsRepository(TweetsRepository newRepo) {
        TweetsRepository old = mTweetsRepo;
        mTweetsRepo = newRepo;
        return old;
    }
}
