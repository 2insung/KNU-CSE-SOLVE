package com.project.web.repository.comment;

import com.project.web.domain.comment.CommentRecommendCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRecommendCountRepository extends JpaRepository<CommentRecommendCount, Integer> {

}
