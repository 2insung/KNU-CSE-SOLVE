package com.project.web.repository.comment;

import com.project.web.domain.comment.DeletedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedCommentRepository extends JpaRepository<DeletedComment, Integer> {
}
