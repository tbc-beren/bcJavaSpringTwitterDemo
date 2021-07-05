# bcJavaSpringTwitterDemo
Spring Demonstration project on Microservices, H2 database and twitter API access.

Reads Twitter samples (Random tweets) to analyze the global feeling of Twitter. The tweets gets stored in the service for later processing.

## Configuration
The **application.properties** file supports two properties to control service configuration.

- bcJavaSpringTwitterDemo.minFollowers=5000
  - **Default: 1500**
  - Specifies a follower count filter. Only tweets from users with a follower count above this threshold get stored by the service.

- bcJavaSpringTwitterDemo.lang=jp,en,ko,th
  - **Default: es,fr,it**
  - Specifies a language filter. Only tweets with language identified matching language in the list get stored by the service.

## Twitter4J Configuration
The **application.properties** file also supports properties to control Twitter4J key and token configuration. See [How to get access to the Twitter API](https://developer.twitter.com/en/docs/twitter-api/getting-started/getting-access-to-the-twitter-api).

* twitter4j.debug=true
* twitter4j.oauth.consumerKey=<Credentials>
* twitter4j.oauth.consumerSecret=<Credentials>
* twitter4j.oauth.accessToken=<Credentials>
* twitter4j.oauth.accessTokenSecret=<Credentials>

## REST Routes
The microservice provides several web interfaces:
* /tweet (get) - Retrieve the list of tweets
* /tweet/{tweetId}/validate (put) - Validates a tweet given its tweetId
* /tweet/findValidatedByUser?username=<name> (get) - Retrieves validated tweets posted by a given user
* /tweet/findHashtag?count=<num> (get) - Retrieves the most used hastags
* /tweet/livestream?enabled=<true|false> (put) - Sets twitter livestream on or off
