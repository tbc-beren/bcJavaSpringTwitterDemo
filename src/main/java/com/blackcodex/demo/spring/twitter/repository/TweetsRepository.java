package com.blackcodex.demo.spring.twitter.repository;

import com.blackcodex.demo.spring.twitter.TweetModel;
import org.springframework.data.repository.CrudRepository;

public interface TweetsRepository extends CrudRepository<TweetModel, Long> {
}
