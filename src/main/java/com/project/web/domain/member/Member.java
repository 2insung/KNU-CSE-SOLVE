package com.project.web.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

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

    @Builder
    public Member(Integer id, Boolean isDeleted){
        this.id = id;
        this.isDeleted = isDeleted;
    }
}
