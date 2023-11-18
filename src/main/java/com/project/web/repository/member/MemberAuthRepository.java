package com.project.web.repository.member;
import com.project.web.domain.member.Member;
import com.project.web.domain.member.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberAuthRepository extends JpaRepository<MemberAuth, Integer> {
    Boolean existsByUsername(String username);
}
