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


    @Query("SELECT m.isDeleted, ma.username, ma.level, md.nickname, md.profileImage, md.description, md.grade, md.admissionYear, md.department, md.createdAt " +
            "FROM Member m " +
            "INNER JOIN MemberAuth ma ON ma.member = m " +
            "INNER JOIN MemberDetail md ON md.member = m " +
            "WHERE m.id = :memberId")
    Optional<Object> findMyById(Integer memberId);

    @Query("select md.nickname, md.profileImage, ma.role, m.isDeleted " +
            "from Member m " +
            "inner join MemberDetail md on md.member = m " +
            "inner join MemberAuth ma on ma.member = m " +
            "where m.id = :memberId")
    Optional<Object> findUserByMemberId(Integer memberId);

    @Modifying
    @Query("update Member m set m.isDeleted = :value where m.id = :memberId")
    int updateIsDeleted(Boolean value, Integer memberId);

    @Query("select m.id, m.isDeleted, ma.username, md.nickname, mp.password " +
            "from Member m " +
            "inner join MemberAuth ma on ma.member = m " +
            "inner join MemberPassword mp on mp.member = m " +
            "inner join MemberDetail md on md.member = m " +
            "where ma.username = :username")
    Optional<Object> findUserByUsername(String username);
}
