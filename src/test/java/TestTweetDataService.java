import com.blackcodex.demo.spring.twitter.TweetModel;
import com.blackcodex.demo.spring.twitter.TweetDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@WebAppConfiguration
public class TestTweetDataService {
    @Autowired
    TweetDataService mService;

    @Test
    public void testTweetValidation() {
        assertNull(mService.validate(0));

        mService.add(1, "TwitterDev", "I like #twitterApi");
        assertNull(mService.validate(0));

        TweetModel item = mService.validate(1);
        assertNotNull(item);
    }

    @Test
    public void testTweetValidated() {
        mService.add(1, "Bob",   "@Alice, I want to know the #secret");
        mService.add(2, "Alice", "@Bob, #secret what is your #publicKey?");
        mService.add(3, "Bob",   "@Alice, #secret my #publickey is http://pastebin.com/bob-public-key");
        mService.add(4, "Alice", "@Bob, Here is the #secret http://pastebin.com/encoded-message-for-bob");

        // No tweets validated for Bob
        assertEquals(0, mService.queryValidatedByUser("Bob").size());

        // Some tweets validated for Bob, some others not
        assertNotNull(mService.validate(1));
        assertEquals(1, mService.queryValidatedByUser("Bob").size());

        // All tweets validated for Bob
        assertNotNull(mService.validate(3));
        assertEquals(2, mService.queryValidatedByUser("Bob").size());

        // Validate tweet for another user, count of validated messages shall not change
        assertNotNull(mService.validate(2));
        assertEquals(2, mService.queryValidatedByUser("Bob").size());
    }
}
