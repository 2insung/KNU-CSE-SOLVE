package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @Size(max = 50)
    private String username;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Builder
    public Member(Long id, String username, Level level, Boolean isDelete) {
        this.id = id;
        this.username = username;
        this.level = level;
        this.isDelete = isDelete;
    }
}
