package com.project.web.repository.comment;

import com.project.web.domain.comment.CommentChildCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentChildCountRepository extends JpaRepository<CommentChildCount, Integer> {
    /*
     댓글의 자식 댓글 수 업데이트 함수.
     * 매개변수로 입력된 value 를 더함.
     * 대댓글에는 이 함수가 사용되지 않을 것임. 루트 댓글에만 자식 댓글 수의 변경이 있기 때문.
    */
    @Modifying
    @Query("update CommentChildCount ccc set ccc.childCount = ccc.childCount + :value where ccc.comment.id = :commentId")
    int updateByCommentId(Integer commentId, Integer value);

    /*
     댓글의 자식 댓글 수 + 댓글 출력 함수.
     * deleteComment 에 사용됨.
     * CommentChildCount와 함께 OneToOne으로 매핑된 Comment를 출력함.
    */
    @Query("select ccc from CommentChildCount ccc join fetch ccc.comment where ccc.comment.id = :commentId")
    Optional<CommentChildCount> findByCommentIdWithComment(Integer commentId);


}
