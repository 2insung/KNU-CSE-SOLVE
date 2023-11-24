package com.insung.knucsesolve.repository.member;
import com.insung.knucsesolve.domain.member.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberAuthRepository extends JpaRepository<MemberAuth, Integer> {
    Boolean existsByUsername(String username);

    @Query("select ma from MemberAuth ma where ma.member.id = :memberId")
    Optional<MemberAuth> findByMemberId(Integer memberId);
}
