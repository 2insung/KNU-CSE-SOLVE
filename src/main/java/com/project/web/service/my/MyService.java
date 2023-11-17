package com.project.web.service.my;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.my.dto.*;
import com.project.web.domain.member.*;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.comment.CommentRepository;
import com.project.web.repository.member.MemberAuthRepository;
import com.project.web.repository.member.MemberDetailRepository;
import com.project.web.repository.member.MemberPasswordRepository;
import com.project.web.repository.member.MemberRepository;
import com.project.web.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.session.FindByIndexNameSessionRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyService {

    private final MemberRepository memberRepository;
    private final MemberPasswordRepository memberPasswordRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void updateMy(Integer userId, String nickname, String profileImage, String grade,
                         String description, String admissionYear, String department) {
        MemberDetail memberDetail = memberDetailRepository.findByMember_Id(userId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        if (nickname != null) {
            if(!memberDetail.getNickname().equals(nickname)){
                if (memberDetailRepository.existsByNickname(nickname)) {
                    throw new Error404Exception("이미 존재하는 닉네임입니다.");
                }
            }
            memberDetail.updateNickname(nickname);
        }
        if (profileImage != null) {
            memberDetail.updateProfileImage(profileImage);
        }
        memberDetail.updateGrade(grade);
        memberDetail.updateDescription(description);
        memberDetail.updateAdmissionYear(admissionYear);
        memberDetail.updateDepartment(department);
    }

    @Transactional(readOnly = true)
    public MyEditDto getMyEdit(Integer userId) {
        MemberDetail memberDetail = memberDetailRepository.findByMember_Id(userId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        Integer memberId = memberDetail.getMember().getId();
        String resultNickname = memberDetail.getNickname();
        String profileImage = memberDetail.getProfileImage();
        String description = memberDetail.getDescription();
        String grade = memberDetail.getGrade();
        String admissionYear = memberDetail.getAdmissionYear();
        String department = memberDetail.getDepartment();

        return MyEditDto.builder()
                .userId(memberId)
                .nickname(resultNickname)
                .profileImage(profileImage)
                .description(description)
                .grade(grade)
                .admissionYear(admissionYear)
                .department(department)
                .build();
    }

    @Transactional(readOnly = true)
    public MyDto getMy(Integer userId) {
        Object result = memberRepository.findMyById(userId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
        Object[] arr = (Object[]) result;
        Boolean isDeleted = (Boolean) arr[0];
        String username = (String) arr[1];
        Level level = (Level) arr[2];
        String userNickname = (String) arr[3];
        String profileImage = (String) arr[4];
        String description = (String) arr[5];
        String grade = (String) arr[6];
        String admissionYear = (String) arr[7];
        String department = (String) arr[8];
        LocalDateTime createdAt = (LocalDateTime) arr[9];

        return MyDto.builder()
                .isDeleted(isDeleted)
                .username(username)
                .level(level)
                .nickname(userNickname)
                .profileImage(profileImage)
                .description(description)
                .grade(grade)
                .admissionYear(admissionYear)
                .department(department)
                .createdAt(createdAt)
                .build();
    }

    @Transactional(readOnly = true)
    public List<MyPostDto> getMyPostList(Integer userId, Integer pageNumber) {
        if (!memberRepository.existsById(userId)) {
            throw new Error404Exception("존재하지 않는 사용자입니다.");
        }

        Integer pageSize = 3;
        List<Object[]> results = postRepository.findMyPostByMemberId(userId, pageSize, pageSize * (pageNumber - 1));
        List<MyPostDto> myPostDtoList = results.stream()
                .map((result) -> {
                    Integer postId = (Integer) result[0];
                    Integer boardId = (Integer) result[1];
                    String boardType = (String) result[2];
                    String boardAlias = (String) result[3];
                    String title = (String) result[4];
                    LocalDateTime createdAt = ((Timestamp) result[5]).toLocalDateTime();
                    Integer postAuthorId = (Integer) result[6];

                    return MyPostDto.builder()
                            .postId(postId)
                            .boardId(boardId)
                            .boardType(boardType)
                            .boardAlias(boardAlias)
                            .title(title)
                            .createdAt(createdAt)
                            .postAuthorId(postAuthorId)
                            .build();
                })
                .collect(Collectors.toList());

        return myPostDtoList;
    }

    @Transactional(readOnly = true)
    public Integer getMyPostCount(Integer userId) {
        if (!memberRepository.existsById(userId)) {
            throw new Error404Exception("존재하지 않는 사용자입니다.");
        }

        return postRepository.countMyPost(userId, 20000);
    }


    @Transactional(readOnly = true)
    public List<MyCommentDto> getMyCommentList(Integer userId, Integer pageNumber) {
        if (!memberRepository.existsById(userId)) {
            throw new Error404Exception("존재하지 않는 사용자입니다.");
        }


        Integer pageSize = 3;
        List<Object[]> results = commentRepository.findMyCommentByMemberId(userId, pageSize, pageSize * (pageNumber - 1));
        List<MyCommentDto> myCommentDtoList = results.stream()
                .map((result) -> {
                    Integer commentId = (Integer) result[0];
                    Integer commentAuthorId = (Integer) result[1];
                    Integer postId = (Integer) result[2];
                    Boolean isDeleted = (Boolean) result[3];
                    String body = (String) result[4];
                    LocalDateTime createdAt = ((Timestamp) result[5]).toLocalDateTime();
                    Integer boardId = (Integer) result[6];
                    String boardType = (String) result[7];
                    String boardAlias = (String) result[8];
                    String title = (String) result[9];

                    return MyCommentDto.builder()
                            .commentId(commentId)
                            .commentAuthorId(commentAuthorId)
                            .isDeleted(isDeleted)
                            .postId(postId)
                            .body(body)
                            .createdAt(createdAt)
                            .boardId(boardId)
                            .boardType(boardType)
                            .boardAlias(boardAlias)
                            .title(title)
                            .build();
                })
                .collect(Collectors.toList());

        return myCommentDtoList;
    }

    @Transactional(readOnly = true)
    public Integer getMyCommentCount(Integer userId) {
        if (!memberRepository.existsById(userId)) {
            throw new Error404Exception("존재하지 않는 사용자입니다.");
        }

        return commentRepository.countMyComment(userId, 20000);
    }

    @Transactional
    public void updateMyPassword(Integer userId, String currentPassword, String changePassword){
        MemberPassword memberPassword = memberPasswordRepository.findByMemberId(userId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        if(!passwordEncoder.matches(currentPassword, memberPassword.getPassword())){
            throw new Error404Exception("현재 비밀번호가 일치하지 않습니다.");
        }

        memberPassword.updatePassword(passwordEncoder.encode(changePassword));

    }

    @Transactional(readOnly = true)
    public Integer getMyId(Integer userId){
        if(memberRepository.existsById(userId)){
            return userId;
        }
        else{
            return null;
        }
    }

    @Transactional
    public Boolean withdraw(Integer userId){
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
        if(memberRepository.updateIsDeleted(true, member.getId()) == 1){
            return true;
        }
        else{
            return false;
        }
    }

}
