package com.project.web.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_detail")
public class MemberDetail {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "description")
    @Size(max = 100)
    private String description;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "admission_year")
    @Size(max = 10)
    private String admissionYear;

    @Column(name = "department")
    @Size(max = 30)
    private String department;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Builder
    public MemberDetail(Long id, Member member, String description, Integer grade,
                        String admissionYear, String department, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.member = member;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateDescription(String description){
        this.description = description;
    }

    public void updateGrade(Integer grade){
        this.grade = grade;
    }

    public void updateAdmissionYear(String admissionYear){
        this.admissionYear = admissionYear;
    }

    public void updateDepartment(String department){
        this.department = department;
    }

}
