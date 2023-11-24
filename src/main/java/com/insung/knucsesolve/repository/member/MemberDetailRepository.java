package com.insung.knucsesolve.repository.member;

import com.insung.knucsesolve.domain.member.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberDetailRepository extends JpaRepository<MemberDetail, Integer> {
    Boolean existsByNickname(String nickname);

    Optional<MemberDetail> findByMember_Id(Integer memberId);
}
