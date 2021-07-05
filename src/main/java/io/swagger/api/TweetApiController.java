package io.swagger.api;

import com.blackcodex.demo.spring.twitter.TweetModel;
import com.blackcodex.demo.spring.twitter.TweetService;
import io.swagger.model.Tweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-07-04T19:11:51.709Z")

@Controller
public class TweetApiController implements TweetApi {

    private static final Logger log = LoggerFactory.getLogger(TweetApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TweetApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Tweet>> tweetEnum() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Tweet>>(objectMapper.readValue("{}", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Tweet>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Tweet>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<String>> tweetFindHashtag(@ApiParam(value = "Number of hashtags to retrieve", defaultValue = "10") @Valid @RequestParam(value = "count", required = false, defaultValue="10") Integer count) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<String>>(objectMapper.readValue("{}", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<String>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Tweet>> tweetFindValidatedByUser(@NotNull @ApiParam(value = "Username to retrieve tweets for", required = true) @Valid @RequestParam(value = "username", required = true) String username) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Tweet>>(objectMapper.readValue("{}", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Tweet>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        final List<TweetModel> dataList = TweetService.queryValidatedByUser(username);
        final List<Tweet> resultList = tweetListFromModel(dataList);
        return new ResponseEntity<List<Tweet>>(resultList, HttpStatus.OK);
    }

    public ResponseEntity<Tweet> tweetValidate(@ApiParam(value = "",required=true) @PathVariable("tweetId") Long tweetId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Tweet>(objectMapper.readValue("{\"empty\": false}", Tweet.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Tweet>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        final TweetModel tweetModel = TweetService.validate(tweetId);
        if (tweetModel == null) {
            return new ResponseEntity<Tweet>(HttpStatus.NOT_FOUND);
        }

        final Tweet result = tweetFromModel(tweetModel);
        return new ResponseEntity<Tweet>(result, HttpStatus.OK);
    }

    private Tweet tweetFromModel(TweetModel tweetSrc) {
        Tweet tweet = new Tweet();
        tweet.setId((int) tweetSrc.getId());
        tweet.setUsername(tweetSrc.getUsername());
        tweet.setText(tweetSrc.getText());
        tweet.setLocation(tweetSrc.getLocation());
        tweet.setValidated(tweetSrc.isValidated());
        return tweet;
    }
    private List<Tweet> tweetListFromModel(List<TweetModel> tweetList) {
        List<Tweet> resultList = new ArrayList<>();
        for (TweetModel tweet : tweetList) {
            Tweet tweetModel = tweetFromModel(tweet);
            resultList.add(tweetModel);
        }
        return resultList;
    }

}
