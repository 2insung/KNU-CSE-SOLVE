package com.project.web.repository;

import com.project.web.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p, b, mp, pc, pi " +
            "FROM Post p " +
            "INNER JOIN Board b on p.board = b "+
            "INNER JOIN MemberProfile mp on p.member = mp.member " +
            "INNER JOIN PostContent pc ON pc.post = p " +
            "INNER JOIN PostInfo pi ON pi.post = p " +
            "WHERE b.type = :type " +
            "ORDER BY " +
            "CASE WHEN pi.isNotification = TRUE THEN 1 ELSE 2 END, pc.createdAt DESC")
    Page<Object[]> findByPageableAndTypeDefault(Pageable pageable, String type);

    @Query("SELECT p, b, mp, pc, pi " +
            "FROM Post p " +
            "INNER JOIN Board b on p.board = b " +
            "INNER JOIN MemberProfile mp on p.member = mp.member " +
            "INNER JOIN PostContent pc ON pc.post = p " +
            "INNER JOIN PostInfo pi ON pi.post = p " +
            "WHERE b.type = :type AND p.id = :postId")
    Optional<Object[]> findByTypeAndId(String type, Long postId);
}
