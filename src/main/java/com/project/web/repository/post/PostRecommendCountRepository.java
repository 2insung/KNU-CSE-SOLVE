package com.project.web.repository.post;

import com.project.web.domain.post.PostRecommendCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRecommendCountRepository extends JpaRepository<PostRecommendCount, Integer> {
    @Query("select prc from PostRecommendCount prc where prc.post.id = :postId")
    Optional<PostRecommendCount> findByPostId(Integer postId);

    @Query("select prc from PostRecommendCount prc join fetch prc.post where prc.post.id = :postId")
    Optional<PostRecommendCount> findWithPostByPostId(Integer postId);

    @Modifying
    @Query("update PostRecommendCount prc set prc.recommendCount = prc.recommendCount + :value where prc.post.id = :postId")
    int updateByPostId(Integer postId, Integer value);

    @Modifying
    @Query("delete from PostRecommendCount prc where prc.post.id = :postId")
    int deleteByPostId(Integer postId);

}
