package com.insung.knucsesolve.repository.post;

import com.insung.knucsesolve.domain.post.PostRecommendMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRecommendMemberRepository extends JpaRepository<PostRecommendMember, Integer> {
    /*
     게시글에 추천한 멤버 삭제 함수.
     * 매개변수로 입력된 post id에 일치하는 레코드를 삭제.
    */
    @Modifying
    @Query("delete from PostRecommendMember prm where prm.post.id = :postId")
    int deleteByPostId(Integer postId);

    /*
     게시글에에 추천한 사용자가 이전에 추천했는지 true/false로 반환하는 함수.
     * 매개변수로 입력된 post id, member id에 일치하는 레코드가 존재한다면 count(prm)이 1 이상이기 때문에, true 반환.
    */
    @Query("select count(prm) > 0 from PostRecommendMember prm where prm.post.id = :postId and prm.member.id = :memberId")
    Boolean existsByPostAndMemberId(Integer postId, Integer memberId);
}
