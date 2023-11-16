package com.project.web.repository.member;

import com.project.web.domain.member.MemberPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberPasswordRepository extends JpaRepository<MemberPassword, Integer> {
    @Query("select mp from MemberPassword mp where mp.member.id = :memberId")
    Optional<MemberPassword> findByMemberId(Integer memberId);
}
