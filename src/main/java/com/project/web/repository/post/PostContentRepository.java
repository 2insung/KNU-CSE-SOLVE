package com.project.web.repository.post;

import com.project.web.domain.post.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostContentRepository extends JpaRepository<PostContent, Integer> {
    @Query("select pc from PostContent pc join fetch pc.post where pc.post.id = :postId")
    Optional<PostContent> findWithPostByPostId(Integer postId);

    @Query("select pc from PostContent pc join fetch pc.post where pc.post.board.id = :boardId and pc.post.isNotice = true")
    List<PostContent> findWithPostByBoardIdAndIsNotice(Integer boardId);

    @Modifying
    @Query("delete from PostContent pc where pc.post.id = :postId")
    int deleteByPostId(Integer postId);
}
