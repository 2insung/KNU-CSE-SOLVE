package com.project.web.service.my;

import com.project.web.controller.my.dto.*;
import com.project.web.domain.member.*;
import com.project.web.exception.Error400Exception;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.comment.CommentRepository;
import com.project.web.repository.member.MemberDetailRepository;
import com.project.web.repository.member.MemberPasswordRepository;
import com.project.web.repository.member.MemberRepository;
import com.project.web.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberPasswordRepository memberPasswordRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /*
      사용자 정보 출력 함수.
     * memberId에 해당하는 사용자 정보를 출력하는 함수임.
    */
    @Transactional(readOnly = true)
    public MyDto getMy(Integer memberId) {
        Object result = memberRepository.findMyById(memberId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
        Object[] arr = (Object[]) result;
        Boolean resultIsDeleted = (Boolean) arr[0];
        String resultUsername = (String) arr[1];
        Role resultRole = (Role) arr[2];
        String resultUserNickname = (String) arr[3];
        String resultProfileImage = (String) arr[4];
        String resultDescription = (String) arr[5];
        String resultGrade = (String) arr[6];
        String resultAdmissionYear = (String) arr[7];
        String resultDepartment = (String) arr[8];
        LocalDateTime resultCreatedAt = (LocalDateTime) arr[9];

        return MyDto.builder()
                .isDeleted(resultIsDeleted)
                .username(resultUsername)
                .role(resultRole)
                .nickname(resultUserNickname)
                .profileImage(resultProfileImage)
                .description(resultDescription)
                .grade(resultGrade)
                .admissionYear(resultAdmissionYear)
                .department(resultDepartment)
                .createdAt(resultCreatedAt)
                .build();
    }

    /*
      사용자 이전 정보 출력 함수.
     * memberId에 해당하는 사용자 이전 정보를 출력하는 함수임.
    */
    @Transactional(readOnly = true)
    public MyEditDto getMyEdit(Integer memberId) {
        MemberDetail memberDetail = memberDetailRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        Integer resultMemberId = memberDetail.getMember().getId();
        String resultNickname = memberDetail.getNickname();
        String resultProfileImage = memberDetail.getProfileImage();
        String resultDescription = memberDetail.getDescription();
        String resultGrade = memberDetail.getGrade();
        String resultAdmissionYear = memberDetail.getAdmissionYear();
        String resultDepartment = memberDetail.getDepartment();

        return MyEditDto.builder()
                .memberId(resultMemberId)
                .nickname(resultNickname)
                .profileImage(resultProfileImage)
                .description(resultDescription)
                .grade(resultGrade)
                .admissionYear(resultAdmissionYear)
                .department(resultDepartment)
                .build();
    }

    /*
      사용자 작성 게시글 출력 함수.
     * memberId에 해당하는 사용자 작성 게시글을 출력하는 함수임.
    */
    @Transactional(readOnly = true)
    public List<MyPostDto> getMyPostList(Integer userId, Integer memberId, Integer pageSize, Integer pageNumber) {
        if (!memberRepository.existsById(memberId)) {
            throw new Error404Exception("존재하지 않는 사용자입니다.");
        }

        List<Object[]> results = postRepository.findMyPostByMemberId(memberId, pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer resultPostId = (Integer) result[0];
                    Integer resultPostAuthorId = (Integer) result[1];
                    LocalDateTime resultCreatedAt = ((Timestamp) result[2]).toLocalDateTime();
                    Integer resultBoardId = (Integer) result[3];
                    String resultBoardType = (String) result[4];
                    String resultBoardAlias = (String) result[5];
                    String resultTitle = (String) result[6];
                    Boolean isMine = userId != null && userId.equals(resultPostAuthorId);

                    return MyPostDto.builder()
                            .id(resultPostId)
                            .authorId(resultPostAuthorId)
                            .createdAt(resultCreatedAt)
                            .boardId(resultBoardId)
                            .boardType(resultBoardType)
                            .boardAlias(resultBoardAlias)
                            .title(resultTitle)
                            .isMine(isMine)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /*
      사용자 작성 게시글 개수 출력 함수.
     * memberId 해당하는 사용자 작성 게시글의 개수를 출력하는 함수임.
     * 만약 사용자가 작성한 게시글이 20000개가 넘어갈 경우 20000개의 레코드만 계산함.
    */
    @Transactional(readOnly = true)
    public Integer getMyPostCount(Integer memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new Error404Exception("존재하지 않는 사용자입니다.");
        }

        return postRepository.countMyPost(memberId, 20000);
    }

    /*
      사용자 작성 댓글 출력 함수.
     * memberId에 해당하는 사용자 작성 댓글을 출력하는 함수임.
    */
    @Transactional(readOnly = true)
    public List<MyCommentDto> getMyCommentList(Integer userId, Integer memberId, Integer pageSize, Integer pageNumber) {
        if (!memberRepository.existsById(memberId)) {
            throw new Error404Exception("존재하지 않는 사용자입니다.");
        }

        List<Object[]> results = commentRepository.findMyCommentByMemberId(memberId, pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer resultCommentId = (Integer) result[0];
                    Integer resultCommentAuthorId = (Integer) result[1];
                    Integer resultPostId = (Integer) result[2];
                    Boolean resultIsDeleted = (Boolean) result[3];
                    String resultBody = (String) result[4];
                    LocalDateTime resultCreatedAt = ((Timestamp) result[5]).toLocalDateTime();
                    Integer resultBoardId = (Integer) result[6];
                    String resultBoardType = (String) result[7];
                    String resultBoardAlias = (String) result[8];
                    String resultTitle = (String) result[9];
                    Boolean isMine = userId != null && userId.equals(resultCommentAuthorId);

                    return MyCommentDto.builder()
                            .id(resultCommentId)
                            .authorId(resultCommentAuthorId)
                            .postId(resultPostId)
                            .isDeleted(resultIsDeleted)
                            .body(resultBody)
                            .createdAt(resultCreatedAt)
                            .boardId(resultBoardId)
                            .boardType(resultBoardType)
                            .boardAlias(resultBoardAlias)
                            .title(resultTitle)
                            .isMine(isMine)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /*
       사용자 작성 댓글 개수 출력 함수.
      * memberId 해당하는 사용자 작성 댓글의 개수를 출력하는 함수임.
      * 만약 사용자가 작성한 댓글이 20000개가 넘어갈 경우 20000개의 레코드만 계산함.
     */
    @Transactional(readOnly = true)
    public Integer getMyCommentCount(Integer memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new Error404Exception("존재하지 않는 사용자입니다.");
        }

        return commentRepository.countMyComment(memberId, 20000);
    }

    /*
      사용자 id 출력 함수.
     * memberId에 해당하는 사용자 id를 출력하는 함수임.
    */
    @Transactional(readOnly = true)
    public Integer getMyId(Integer memberId) {
        if (memberRepository.existsById(memberId)) {
            return memberId;
        }
        else {
            return null;
        }
    }

    /*
      사용자 정보 업데이트 함수.
     * memberId에 해당하는 사용자 정보를 수정하는 함수임.
     * 입력받은 profileImage가 null인 경우는 프로필 이미지를 변경하지 않겠다는 의미임.
    */
    @Transactional
    public void updateMy(Integer memberId, String nickname, String profileImage, String grade, String description,
                         String admissionYear, String department) {
        MemberDetail memberDetail = memberDetailRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        if (nickname != null) {
            if (!memberDetail.getNickname().equals(nickname) && memberDetailRepository.existsByNickname(nickname)) {
                throw new Error404Exception("이미 존재하는 닉네임입니다.");
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

    /*
     사용자 비밀번호 업데이트 함수.
    * memberId에 해당하는 사용자 비밀번호를 수정하는 함수임.
   */
    @Transactional
    public void updateMyPassword(Integer memberId, String currentPassword, String changePassword) {
        MemberPassword memberPassword = memberPasswordRepository.findByMemberId(memberId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(currentPassword, memberPassword.getPassword())) {
            throw new Error404Exception("현재 비밀번호가 일치하지 않습니다.");
        }

        memberPassword.updatePassword(passwordEncoder.encode(changePassword));
    }

    /*
      사용자 비활성화 함수.
     * memberId에 해당하는 사용자의 계정을 비활성화시킴.
    */
    @Transactional
    public Boolean withdraw(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
        if (memberRepository.updateIsDeleted(true, member.getId()) == 1) {
            return true;
        }
        else {
            return false;
        }
    }
}
