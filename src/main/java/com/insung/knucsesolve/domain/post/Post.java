package com.insung.knucsesolve.domain.post;

import com.insung.knucsesolve.domain.board.Board;
import com.insung.knucsesolve.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "is_notice")
    private Boolean isNotice;

    @Column(name = "is_hot")
    private Boolean isHot;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "hot_registered_at")
    private LocalDateTime hotRegisteredAt;

    @Column(name = "category", nullable = false)
    private String category;

    @Builder
    public Post(Integer id, Member member, Board board, Integer priority, Boolean isNotice,
                Boolean isHot, Boolean isDeleted, LocalDateTime createdAt, LocalDateTime hotRegisteredAt, String category) {
        this.id = id;
        this.member = member;
        this.board = board;
        this.priority = priority;
        this.isNotice = isNotice;
        this.isHot = isHot;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.hotRegisteredAt = hotRegisteredAt;
        this.category = category;
    }

    public void updatePriority(Integer priority) {
        this.priority = priority;
    }

    public void updateIsNotice(Boolean isNotice) {
        this.isNotice = isNotice;
    }

    public void updateIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public void updateHotRegisteredAt(LocalDateTime hotRegisteredAt) {
        this.hotRegisteredAt = hotRegisteredAt;
    }

    public void updateCategory(String category) {
        this.category = category;
    }
}
