package com.project.web.repository.member;

import com.project.web.domain.member.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberDetailRepository extends JpaRepository<MemberDetail, Integer> {


    Optional<MemberDetail> findByNickname(String nickname);

    Boolean existsByNickname(String nickname);

    Optional<MemberDetail> findByMember_Id(Integer memberId);
}
