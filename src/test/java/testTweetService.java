import com.blackcodex.demo.spring.twitter.TweetModel;
import com.blackcodex.demo.spring.twitter.TweetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@WebAppConfiguration
public class testTweetService {
    @Autowired
    TweetService mService;

    @Test
    public void testTweetValidation() {
        assertNull(mService.validate(0));

        mService.add(1, "TwitterDev", "I like #twitterApi");
        assertNull(mService.validate(0));

        TweetModel item = mService.validate(1);
        assertNotNull(item);
    }
}
