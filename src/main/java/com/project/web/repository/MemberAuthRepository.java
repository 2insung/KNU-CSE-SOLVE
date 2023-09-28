package com.project.web.repository;
import com.project.web.domain.Member;
import com.project.web.domain.MemberAuth;
import com.project.web.domain.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberAuthRepository extends JpaRepository<MemberAuth, Long> {

}
