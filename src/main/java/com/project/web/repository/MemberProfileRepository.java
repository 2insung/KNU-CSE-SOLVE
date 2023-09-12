package com.project.web.repository;

import com.project.web.domain.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    MemberProfile findByMember_Nickname(String nickname);
    MemberProfile findByMember_Id(Long id);
}
