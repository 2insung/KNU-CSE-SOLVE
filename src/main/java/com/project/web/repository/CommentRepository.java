package com.project.web.repository;

import com.project.web.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c, p, mp1, mp2 " +
            "FROM Comment c " +
            "INNER JOIN Post p ON c.post = p " +
            "INNER JOIN MemberProfile mp1 on mp1.member = c.member "+
            "LEFT JOIN MemberProfile mp2 on mp2.member = c.parentMember " +
            "WHERE p.id = :postId AND (c.isDelete = false OR c.childrenCount != 0) " +
            "ORDER BY c.rootCommentOrder DESC, c.childrenCommentOrder DESC")
    Page<Object[]> findByPageableAndPostId(Pageable pageable, Long postId);
}
