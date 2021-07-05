package com.blackcodex.demo.spring.twitter.repository;

import com.blackcodex.demo.spring.twitter.TweetModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetsRepository extends CrudRepository<TweetModel, Long> {
    @Query(value="select * from Tweets where validated=TRUE and username=?1", nativeQuery = true)
    List<TweetModel> find(@Param("username") String username);
}
