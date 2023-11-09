package com.project.web.repository.member;

import com.project.web.domain.member.MemberCommentCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCommentCountRepository extends JpaRepository<MemberCommentCount, Integer> {
    /*
     사용자의 댓글 수 업데이트 함수.
     * 댓글 등록, 삭제 시 사용됨.
    */
    @Modifying
    @Query("update MemberCommentCount mcc set mcc.commentCount = mcc.commentCount + :value where mcc.member.id = :memberId")
    int updateByMemberId(Integer memberId, Integer value);
}
