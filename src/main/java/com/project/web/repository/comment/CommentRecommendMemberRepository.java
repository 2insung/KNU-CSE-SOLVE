package com.project.web.repository.comment;

import com.project.web.domain.comment.CommentRecommendMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRecommendMemberRepository extends JpaRepository<CommentRecommendMember, Integer> {
    /*
     댓글에 추천한 멤버 삭제 함수.
     * 매개변수로 입력된 여러 comment id에 일치하는 레코드를 삭제.
    */
    @Modifying
    @Query("delete from CommentRecommendMember crm where crm.comment.id in :commentIds")
    int deleteByCommentIds(List<Integer> commentIds);

    /*
     댓글에 추천한 사용자가 이전에 추천했는지 true/false로 반환하는 함수.
     * 매개변수로 입력된 여러 comment id, member id에 일치하는 레코드가 존재한다면 count(crm)이 1 이상이기 때문에, true 반환.
    */
    @Query("select count(crm) > 0 from CommentRecommendMember crm where crm.comment.id = :commentId and crm.member.id = :memberId")
    Boolean existsByCommentAndMemberId(Integer commentId, Integer memberId);
}
