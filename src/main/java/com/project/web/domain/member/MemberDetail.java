package com.project.web.domain.member;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public MemberDetail(Integer id, Member member, String nickname, String profileImage, String description,
                        String grade, String admissionYear, String department, LocalDateTime createdAt,
                        LocalDateTime updatedAt) {
        this.id = id;
        this.member = member;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
