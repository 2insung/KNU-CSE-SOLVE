package com.project.web.repository.member;

import com.project.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    /*
     로그인 시 사용자 정보 출력 함수.
     * 사용자 정보를 출력하여 로그인에 성공한다면, 사용자 정보를 PrincipalDetail에 저장함.
     * 비활성화된 사용자는 출력하지 않음.
    */
    @Query("SELECT m.id, ma.username, ma.password, ma.role " +
            "FROM Member m " +
            "INNER JOIN MemberAuth ma ON ma.member = m " +
            "WHERE ma.username = :username and m.isDeleted = false")
    Optional<Object> findPrincipalDetailsByUsername(String username);

    /*
     사용자 정보 출력 함수.
     * '/my/{memberId}' 에서 출력할 사용자 정보.
    */
    @Query("SELECT m.id, m.isDeleted, m.createdAt, ma.username, ma.role, md.nickname, md.profileImage, md.description, md.grade, md.admissionYear, md.department " +
            "FROM Member m " +
            "INNER JOIN MemberAuth ma ON ma.member = m " +
            "INNER JOIN MemberDetail md ON md.member = m " +
            "WHERE m.id = :memberId")
    Optional<Object> findMyDtoById(Integer memberId);

    /*
     사용자 정보 출력 함수.
     * 현재 로그인한 사용자의 정보를 출력함.
    */
    @Query("select m.id, ma.username, md.nickname, md.profileImage " +
            "from Member m " +
            "inner join MemberAuth ma on ma.member = m " +
            "inner join MemberDetail md on md.member = m " +
            "where m.id = :memberId")
    Optional<Object> findUserDtoByMemberId(Integer memberId);

    /*
     사용자 정보 출력 함수.
     * 회원가입 시 이미 가입된 사용자의 정보를 출력함.
    */
    @Query("select m.id, m.isDeleted, ma.username, ma.password, md.nickname " +
            "from Member m " +
            "inner join MemberAuth ma on ma.member = m " +
            "inner join MemberDetail md on md.member = m " +
            "where ma.username = :username")
    Optional<Object> findExistingMemberByUsername(String username);

    /*
     사용자 계정 상태 변경 함수.
     * isDeleted가 true하면 비활성화된 계정임.
    */
    @Modifying
    @Query("update Member m set m.isDeleted = :value where m.id = :memberId")
    int updateIsDeletedById(Boolean value, Integer memberId);
}
