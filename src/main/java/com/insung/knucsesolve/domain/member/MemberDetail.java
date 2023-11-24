package com.insung.knucsesolve.domain.member;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_detail")
public class MemberDetail {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "description")
    private String description;

    @Column(name = "grade")
    private String grade;

    @Column(name = "admission_year")
    private String admissionYear;

    @Column(name = "department")
    private String department;

    @Builder
    public MemberDetail(Integer id, Member member, String nickname, String profileImage, String description,
                        String grade, String admissionYear, String department) {
        this.id = id;
        this.member = member;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateGrade(String grade) {
        this.grade = grade;
    }

    public void updateAdmissionYear(String admissionYear) {
        this.admissionYear = admissionYear;
    }

    public void updateDepartment(String department) {
        this.department = department;
    }
}
