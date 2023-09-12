package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_level")
public class MemberLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    Member member;

    @OneToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    Level level;

    @Builder
    public MemberLevel(Long id, Member member, Level level){
        this.id = id;
        this.member = member;
        this.level = level;
    }

}
