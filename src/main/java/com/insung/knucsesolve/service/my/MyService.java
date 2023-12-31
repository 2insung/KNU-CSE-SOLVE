package com.insung.knucsesolve.service.my;

import com.insung.knucsesolve.controller.my.dto.view.*;
import com.insung.knucsesolve.domain.member.*;
import com.insung.knucsesolve.exception.Error404Exception;
import com.insung.knucsesolve.exception.Error500Exception;
import com.insung.knucsesolve.repository.comment.CommentRepository;
import com.insung.knucsesolve.repository.member.MemberAuthRepository;
import com.insung.knucsesolve.repository.member.MemberDetailRepository;
import com.insung.knucsesolve.repository.member.MemberRepository;
import com.insung.knucsesolve.repository.post.PostRepository;
import com.insung.knucsesolve.repository.post.PostScrapMemberRepository;
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
    private final MemberAuthRepository memberAuthRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final PostRepository postRepository;
    private final PostScrapMemberRepository postScrapMemberRepository;
    private final CommentRepository commentRepository;

    /*
      사용자 정보 출력 함수.
     * memberId에 해당하는 사용자 정보를 출력하는 함수임.
    */
    @Transactional(readOnly = true)
    public MyDto getMyDto(Integer userId, Integer memberId) {
        Object result = memberRepository.findMyDtoById(memberId)
                .orElseThrow(() -> new Error500Exception("존재하지 않는 사용자입니다."));

        Object[] arr = (Object[]) result;
        Integer resultId = (Integer) arr[0];
        Boolean resultIsDeleted = (Boolean) arr[1];
        LocalDateTime resultCreatedAt = (LocalDateTime) arr[2];
        String resultUsername = (String) arr[3];
        Role resultRole = (Role) arr[4];
        String resultNickname = (String) arr[5];
        String resultProfileImage = (String) arr[6];
        String resultDescription = (String) arr[7];
        String resultGrade = (String) arr[8];
        String resultAdmissionYear = (String) arr[9];
        String resultDepartment = (String) arr[10];
        Boolean isMine = userId != null && userId.equals(memberId);

        return MyDto.builder()
                .id(resultId)
                .isDeleted(resultIsDeleted)
                .username(resultUsername)
                .role(resultRole)
                .nickname(resultNickname)
                .profileImage(resultProfileImage)
                .description(resultDescription)
                .grade(resultGrade)
                .admissionYear(resultAdmissionYear)
                .department(resultDepartment)
                .createdAt(resultCreatedAt)
                .isMine(isMine)
                .build();
    }

    /*
      사용자 이전 정보 출력 함수.
     * memberId에 해당하는 사용자 이전 정보를 출력하는 함수임.
    */
    @Transactional(readOnly = true)
    public MyEditDto getMyEditDto(Integer memberId) {
        MemberDetail memberDetail = memberDetailRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new Error500Exception("존재하지 않는 사용자입니다."));

        String resultNickname = memberDetail.getNickname();
        String resultProfileImage = memberDetail.getProfileImage();
        String resultDescription = memberDetail.getDescription();
        String resultGrade = memberDetail.getGrade();
        String resultAdmissionYear = memberDetail.getAdmissionYear();
        String resultDepartment = memberDetail.getDepartment();

        return MyEditDto.builder()
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
    public List<MyPostDto> getMyPostDtos(Integer userId, Integer memberId, Integer pageSize, Integer pageNumber) {
        if (!memberRepository.existsById(memberId)) {
            throw new Error500Exception("존재하지 않는 사용자입니다.");
        }

        List<Object[]> results = postRepository.findMyPostDtosByMemberId(memberId, pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer resultId = (Integer) result[0];
                    Integer resultAuthorId = (Integer) result[1];
                    LocalDateTime resultCreatedAt = ((Timestamp) result[2]).toLocalDateTime();
                    String resultTitle = (String) result[3];
                    Integer resultBoardId = (Integer) result[4];
                    String resultBoardAlias = (String) result[5];
                    Boolean isMine = userId != null && userId.equals(resultAuthorId);

                    return MyPostDto.builder()
                            .id(resultId)
                            .authorId(resultAuthorId)
                            .createdAt(resultCreatedAt)
                            .title(resultTitle)
                            .boardId(resultBoardId)
                            .boardAlias(resultBoardAlias)
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
    public Integer getMyPostsCount(Integer memberId) {
        Integer postCountLimit = 20000;

        if (!memberRepository.existsById(memberId)) {
            throw new Error500Exception("존재하지 않는 사용자입니다.");
        }

        return postRepository.countMyPosts(memberId, postCountLimit);
    }

    /*
      사용자 작성 댓글 출력 함수.
     * memberId에 해당하는 사용자 작성 댓글을 출력하는 함수임.
    */
    @Transactional(readOnly = true)
    public List<MyCommentDto> getMyCommentDtos(Integer userId, Integer memberId, Integer pageSize, Integer pageNumber) {
        if (!memberRepository.existsById(memberId)) {
            throw new Error500Exception("존재하지 않는 사용자입니다.");
        }

        List<Object[]> results = commentRepository.findMyCommentDtosByMemberId(memberId, pageSize, pageSize * (pageNumber - 1));
        return results.stream()
                .map((result) -> {
                    Integer resultId = (Integer) result[0];
                    Integer resultAuthorId = (Integer) result[1];
                    Integer resultPostId = (Integer) result[2];
                    Boolean resultIsDeleted = (Boolean) result[3];
                    String resultBody = (String) result[4];
                    LocalDateTime resultCreatedAt = ((Timestamp) result[5]).toLocalDateTime();
                    String resultBoardAlias = (String) result[6];
                    String resultPostTitle = (String) result[7];
                    Boolean isMine = userId != null && userId.equals(resultAuthorId);

                    return MyCommentDto.builder()
                            .id(resultId)
                            .authorId(resultAuthorId)
                            .postId(resultPostId)
                            .isDeleted(resultIsDeleted)
                            .body(resultBody)
                            .createdAt(resultCreatedAt)
                            .boardAlias(resultBoardAlias)
                            .postTitle(resultPostTitle)
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
    public Integer getMyCommentsCount(Integer memberId) {
        Integer commentCountLimit = 20000;

        if (!memberRepository.existsById(memberId)) {
            throw new Error404Exception("존재하지 않는 사용자입니다.");
        }

        return commentRepository.countMyComments(memberId, commentCountLimit);
    }

    /*
       사용자 스크랩 게시글 개수 출력 함수.
      * memberId 해당하는 사용자 스크랩 게시글의 개수를 출력하는 함수임.
      * 만약 사용자가 작성한 게시글이 20000개가 넘어갈 경우 20000개의 레코드만 계산함.
     */
    @Transactional(readOnly = true)
    public List<MyScrapDto> getMyScrapDtos(Integer userId, Integer memberId, Integer pageSize, Integer pageNumber) {
        if (!memberRepository.existsById(memberId)) {
            throw new Error500Exception("존재하지 않는 사용자입니다.");
        }

        List<Object[]> results = postScrapMemberRepository.findMyScrapDtosByMemberId(memberId, pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer resultMemberId = (Integer) result[0];
                    Integer resultPostId = (Integer) result[1];
                    LocalDateTime resultCreatedAt = ((Timestamp) result[2]).toLocalDateTime();
                    String resultTitle = (String) result[3];
                    String resultBoardAlias = (String) result[4];
                    Boolean isMine = userId != null && userId.equals(resultMemberId);

                    return MyScrapDto.builder()
                            .memberId(resultMemberId)
                            .postId(resultPostId)
                            .createdAt(resultCreatedAt)
                            .title(resultTitle)
                            .boardAlias(resultBoardAlias)
                            .isMine(isMine)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /*
       사용자 스크랩 게시글 개수 출력 함수.
      * memberId 해당하는 사용자 스크랩 게시글의 개수를 출력하는 함수임.
      * 만약 사용자가 스크랩한 게시글이 20000개가 넘어갈 경우 20000개의 레코드만 계산함.
     */
    @Transactional(readOnly = true)
    public Integer getMyScrapsCount(Integer memberId) {
        Integer scrapCountLimit = 20000;

        if (!memberRepository.existsById(memberId)) {
            throw new Error500Exception("존재하지 않는 사용자입니다.");
        }

        return postScrapMemberRepository.countMyScraps(memberId, scrapCountLimit);
    }

    /*
      사용자 정보 업데이트 함수.
     * memberId에 해당하는 사용자 정보를 수정하는 함수임.
     * 입력받은 profileImage가 null인 경우는 프로필 이미지를 변경하지 않겠다는 의미임.
    */
    @Transactional
    public void updateMemberDetail(Integer memberId, String nickname, String profileImage, String grade, String description,
                                   String admissionYear, String department) {
        MemberDetail memberDetail = memberDetailRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new Error500Exception("존재하지 않는 사용자입니다."));

        if (nickname != null) {
            if (!memberDetail.getNickname().equals(nickname) && memberDetailRepository.existsByNickname(nickname)) {
                throw new Error500Exception("이미 존재하는 닉네임입니다.");
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
    public void updatePassword(Integer memberId, String currentPassword, String changePassword) {
        MemberAuth memberAuth = memberAuthRepository.findByMemberId(memberId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(currentPassword, memberAuth.getPassword())) {
            throw new Error404Exception("현재 비밀번호가 일치하지 않습니다.");
        }

        memberAuth.updatePassword(passwordEncoder.encode(changePassword));
    }

    /*
      사용자 비활성화 함수.
     * memberId에 해당하는 사용자의 계정을 비활성화시킴.
    */
    @Transactional
    public Boolean withdraw(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
        if (memberRepository.updateIsDeletedById(true, member.getId()) == 1) {
            return true;
        }
        else {
            return false;
        }
    }
}
