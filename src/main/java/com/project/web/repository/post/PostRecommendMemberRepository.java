package com.project.web.repository.post;

import com.project.web.domain.post.PostRecommendMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRecommendMemberRepository extends JpaRepository<PostRecommendMember, Integer> {
    @Modifying
    @Query("delete from PostRecommendMember prm where prm.post.id = :postId")
    int deleteByPostId(Integer postId);

    @Query("select count(prm) > 0 from PostRecommendMember prm where prm.post.id = :postId and prm.member.id = :memberId")
    Boolean existsByPostAndMemberId(Integer postId, Integer memberId);
}
