import com.blackcodex.demo.spring.twitter.TweetModel;
import com.blackcodex.demo.spring.twitter.TweetDataService;
import com.blackcodex.demo.spring.twitter.repository.TweetsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@WebAppConfiguration
public class TestTweetDataService {
    @Autowired
    TweetDataService mService;

    @Test
    public void testTweetEnum() {
        mService.deleteAll();

        assertEquals(0, mService.enumAll().size());

        final TweetModel addedItem = mService.add("Bob", "@Alice, I want to know the #secret");
        assertNotNull(addedItem);

        assertEquals(1, mService.enumAll().size());
    }

    @Test
    public void testTweetValidation() {
        mService.deleteAll();

        assertNull(mService.validate(0));

        final TweetModel addedTweet = mService.add("TwitterDev", "I like #twitterApi");
        assertNotNull(addedTweet);

        final List<TweetModel> list = mService.enumAll();
        assertNotNull(list);
        assertEquals(1, list.size());

        // try to validate e not existing tweet
        assertNull(mService.validate(0));

        // Validate an existing twwet
        TweetModel item = mService.validate(addedTweet.getId());
        assertNotNull(item);
    }

    @Test
    public void testTweetValidated() {
        mService.deleteAll();

        mService.add("Bob",   "@Alice, I want to know the #secret");
        mService.add("Alice", "@Bob, #secret what is your #publicKey?");
        mService.add("Bob",   "@Alice, #secret my #publickey is http://pastebin.com/bob-public-key");
        mService.add("Alice", "@Bob, Here is the #secret http://pastebin.com/encoded-message-for-bob");

        List<TweetModel> tweetList = mService.enumAll();
        assertNotNull(tweetList);
        assertEquals(4, tweetList.size());

        // No tweets validated for Bob
        assertEquals(0,mService.queryValidatedByUser("Bob").size());

        // Some tweets validated for Bob, some others not
        assertNotNull(mService.validate(tweetList.get(0).getId()));
        assertEquals(1, mService.queryValidatedByUser("Bob").size());

        // All tweets validated for Bob
        assertNotNull(mService.validate(tweetList.get(2).getId()));
        assertEquals(2, mService.queryValidatedByUser("Bob").size());

        // Validate tweet for another user, count of validated messages shall not change
        assertNotNull(mService.validate(tweetList.get(1).getId()));
        assertEquals(2, mService.queryValidatedByUser("Bob").size());
    }

    @Test
    public void testDataServiceInvalid() {
        final TweetsRepository oldRepo = mService.setTweetsRepository(null);

        // No function shall throw when repo is not configured
        mService.deleteAll();
        assertNull(mService.enumAll());
        assertNull(mService.validate(0));
        assertNull(mService.add("user", "text"));
        assertNull(mService.queryValidatedByUser("user"));

        mService.setTweetsRepository(oldRepo);
    }
}
