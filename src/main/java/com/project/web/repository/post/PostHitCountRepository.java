package com.project.web.repository.post;

import com.project.web.domain.post.PostHitCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostHitCountRepository extends JpaRepository<PostHitCount, Integer> {
    @Query("select phc from PostHitCount phc where phc.post.id = :postId")
    Optional<PostHitCount> findByPostId(Integer postId);


}
