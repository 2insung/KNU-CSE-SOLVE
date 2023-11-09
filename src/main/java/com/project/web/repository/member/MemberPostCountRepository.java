package com.project.web.repository.member;

import com.project.web.domain.member.MemberPostCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPostCountRepository extends JpaRepository<MemberPostCount, Integer> {
    /*
     사용자의 게시글 수 업데이트 함수.
     * 게시글 등록, 삭제 시 사용됨.
    */
    @Modifying
    @Query("update MemberPostCount mpc set mpc.postCount = mpc.postCount + :value where mpc.member.id = :memberId")
    int updateByMemberId(Integer memberId, Integer value);
}
