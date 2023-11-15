package com.project.web.repository.post;

import com.project.web.domain.post.PostCommentCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCommentCountRepository extends JpaRepository<PostCommentCount, Integer> {
    /*
     게시글의 댓글 수 업데이트 함수.
     * 댓글 등록, 삭제 시 사용됨.
    */
    @Modifying
    @Query("update PostCommentCount pcc " +
            "set pcc.commentCount = pcc.commentCount + :value, " +
            "pcc.totalCommentCount = pcc.totalCommentCount + :totalValue " +
            "where pcc.post.id = :postId")
    int updateByPostId(Integer postId, Integer value, Integer totalValue);


    @Query("select pcc from PostCommentCount pcc where pcc.post.id = :postId")
    Optional<PostCommentCount> findByPostId(Integer postId);

    @Modifying
    @Query("delete from PostCommentCount pcc where pcc.post.id = :postId")
    int deleteByPostId(Integer postId);
}
