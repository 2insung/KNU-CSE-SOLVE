package com.project.web.repository.comment;

import com.project.web.domain.comment.CommentStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentStatRepository extends JpaRepository<CommentStat, Integer> {
    /*
     댓글의 추천수 업데이트 함수.
     * 매개변수로 입력된 value 를 더함.
    */
    @Modifying
    @Query("update CommentStat cs set cs.recommendCount = cs.recommendCount + :value where cs.comment.id = :commentId")
    int updateByCommentId(Integer commentId, Integer value);

    /*
     댓글의 통계 삭제 함수.
     * 매개변수로 입력된 여러 id에 일치하는 레코드를 삭제.
    */
    @Modifying
    @Query("delete from CommentStat cs where cs.comment.id in :commentIds")
    int deleteByCommentIds(List<Integer> commentIds);
}
