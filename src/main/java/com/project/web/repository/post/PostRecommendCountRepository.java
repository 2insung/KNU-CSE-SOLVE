package com.project.web.repository.post;

import com.project.web.domain.post.PostRecommendCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRecommendCountRepository extends JpaRepository<PostRecommendCount, Integer> {
    @Query("select prc from PostRecommendCount prc where prc.post.id = :postId")
    Optional<PostRecommendCount> findByPostId(Integer postId);


}
