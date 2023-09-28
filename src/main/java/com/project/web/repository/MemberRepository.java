package com.project.web.repository;

import com.project.web.domain.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m, ma, mp " +
            "FROM Member m " +
            "INNER JOIN MemberAuth ma ON ma.member = m " +
            "INNER JOIN MemberProfile mp ON mp.member = m " +
            "WHERE m.username = :username")
    Optional<Object[]> findByUsernameWithAuthAndProfile(String username);


    @Query("SELECT m, mp, md " +
            "FROM Member m " +
            "INNER JOIN MemberProfile mp ON mp.member = m " +
            "INNER JOIN MemberDetail md ON md.member = m " +
            "WHERE mp.nickname = :nickname")
    Optional<Object[]> findByNicknameWithProfileAndDetail(String nickname);

    Boolean existsByUsername(String username);
}
