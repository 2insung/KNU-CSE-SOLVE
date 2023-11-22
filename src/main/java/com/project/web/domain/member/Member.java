package com.project.web.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Member(Integer id, Boolean isDeleted){
        this.id = id;
        this.isDeleted = isDeleted;
    }
}
