package com.project.web.repository.comment;

import com.project.web.domain.comment.CommentChildCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentChildCountRepository extends JpaRepository<CommentChildCount, Integer> {


}
