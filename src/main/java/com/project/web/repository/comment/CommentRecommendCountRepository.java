package com.project.web.repository.comment;

import com.project.web.domain.comment.CommentRecommendCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRecommendCountRepository extends JpaRepository<CommentRecommendCount, Integer> {
    /*
         댓글의 자식 댓글 수 업데이트 함수.
         * 매개변수로 입력된 value 를 더함.
         * 대댓글에는 이 함수가 사용되지 않을 것임. 루트 댓글에만 자식 댓글 수의 변경이 있기 때문.
        */
    @Modifying
    @Query("update CommentRecommendCount crc set crc.recommendCount = crc.recommendCount + :value where crc.comment.id = :commentId")
    int updateByCommentId(Integer commentId, Integer value);

    @Modifying
    @Query("delete from CommentRecommendCount crc where crc.comment.id in :commentIds")
    int deleteByCommentIds(List<Integer> commentIds);
}
