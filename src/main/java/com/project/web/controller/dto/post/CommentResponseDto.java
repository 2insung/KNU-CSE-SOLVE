package com.project.web.controller.dto.post;

import com.project.web.domain.Comment;
import com.project.web.domain.MemberProfile;
import com.project.web.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long memberId;
    private String profileImage;
    private String author;
    private String createdAt;
    private String body;
    private Long parentCommentId;
    private Long groupCommentId;
    private Integer depth;
    private Integer commentOrder;


    public CommentResponseDto(Comment comment, Post post, MemberProfile memberProfile, MemberProfile parentMemberProfile){

    }

    private String createdAtFormatting(LocalDateTime createdAt){
        LocalDateTime now = LocalDateTime.now();

        if (createdAt.toLocalDate().isEqual(now.toLocalDate())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return createdAt.format(formatter);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
            return createdAt.format(formatter);
        }
    }
}
