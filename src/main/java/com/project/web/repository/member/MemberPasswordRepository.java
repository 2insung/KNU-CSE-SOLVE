package com.project.web.repository.member;

import com.project.web.domain.member.MemberPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPasswordRepository extends JpaRepository<MemberPassword, Integer> {
}
