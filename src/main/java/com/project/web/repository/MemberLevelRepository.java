package com.project.web.repository;

import com.project.web.domain.Level;
import com.project.web.domain.MemberLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberLevelRepository extends JpaRepository<MemberLevel, Long> {
    MemberLevel findByMember_Nickname(String nickname);
    Optional<MemberLevel> findByMember_Id(Long id);
}
