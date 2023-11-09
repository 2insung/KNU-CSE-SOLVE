package com.project.web.repository.member;

import com.project.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    @Query("SELECT m.id, ma.username, mpw.password, md.nickname, md.profileImage, ma.role, ma.level " +
            "FROM Member m " +
            "INNER JOIN MemberAuth ma ON ma.member = m " +
            "INNER JOIN MemberPassword mpw ON mpw.member = m " +
            "INNER JOIN MemberDetail md ON md.member = m " +
            "WHERE ma.username = :username and m.isDeleted = false")
    Optional<Object> findByUsernameForLogin(String username);


    @Query("SELECT m.isDeleted, ma.username, ma.level, md.nickname, md.profileImage, md.description, md.grade, md.admissionYear, md.department, md.createdAt, mpc.postCount, mcc.commentCount " +
            "FROM Member m " +
            "INNER JOIN MemberAuth ma ON ma.member = m " +
            "INNER JOIN MemberDetail md ON md.member = m " +
            "INNER JOIN MemberPostCount mpc ON mpc.member = m " +
            "INNER JOIN MemberCommentCount mcc ON mcc.member = m " +
            "WHERE md.nickname = :nickname")
    Optional<Object> findByNicknameForMy(String nickname);

}
