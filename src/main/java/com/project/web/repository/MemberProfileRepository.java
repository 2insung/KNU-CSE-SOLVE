package com.project.web.repository;

import com.project.web.domain.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    Boolean existsByNickname(String nickname);

    @Query("SELECT mp FROM MemberProfile mp JOIN FETCH mp.member WHERE mp.member.username = :username")
    Optional<MemberProfile> findByMemberUsername(String username);
}
