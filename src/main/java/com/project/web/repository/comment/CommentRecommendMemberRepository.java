package com.project.web.repository.comment;

import com.project.web.domain.comment.CommentRecommendMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRecommendMemberRepository extends JpaRepository<CommentRecommendMember, Integer> {
    @Modifying
    @Query("delete from CommentRecommendMember crm where crm.comment.id in :commentIds")
    int deleteByCommentIds(List<Integer> commentIds);

    @Query("select count(crm) > 0 from CommentRecommendMember crm where crm.comment.id = :commentId and crm.member.id = :memberId")
    Boolean existsByCommentAndMemberId(Integer commentId, Integer memberId);
}
