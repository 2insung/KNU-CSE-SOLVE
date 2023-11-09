//package com.project.web.service.my;
//
//import com.project.web.controller.auth.dto.PrincipalDetails;
//import com.project.web.controller.my.dto.MyEditFormRequestDto;
//import com.project.web.controller.my.dto.MyEditPageResponseDto;
//import com.project.web.controller.my.dto.MyPageResponseDto;
//import com.project.web.domain.member.MemberDetail;
//import com.project.web.exception.Error404Exception;
//import com.project.web.repository.member.MemberDetailRepository;
//import com.project.web.repository.member.MemberRepository;
//import com.project.web.service.board.S3UploaderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.session.Session;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.session.FindByIndexNameSessionRepository;
//
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class MyService {
//    private final S3UploaderService s3UploaderService;
//    private final MemberRepository memberRepository;
//    private final MemberDetailRepository memberDetailRepository;
//    private final FindByIndexNameSessionRepository jdbcIndexedSessionRepository;
//
//    @Transactional(readOnly = true)
//    public Boolean checkValidNickname(String nickname) {
//        return memberDetailRepository.existsByNickname(nickname);
//    }
//
//    @Transactional
//    public void editMemberProfile(String prevNickname, MyEditFormRequestDto requestDto, PrincipalDetails principal) throws IOException {
//        MemberDetail memberDetail = memberDetailRepository.findByNickname(prevNickname)
//                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
//
//        memberDetail.updateNickname(requestDto.getNickname());
//
//        if (requestDto.getFile() != null && !requestDto.getFile().isEmpty()) {
//            String imageUrl = s3UploaderService.upload(requestDto.getFile(), "images");
//            memberDetail.updateProfileImage(imageUrl);
//        }
//        memberDetail.updateGrade(requestDto.getGrade());
//        memberDetail.updateDescription(requestDto.getDescription());
//        memberDetail.updateAdmissionYear(requestDto.getAdmissionYear());
//        memberDetail.updateDepartment(requestDto.getDepartment());
//
//        Map<String, ? extends Session> sessionMap = jdbcIndexedSessionRepository.findByPrincipalName(principal.getUsername());
//
//        for (String sessionId : sessionMap.keySet()) {
//            Session session = sessionMap.get(sessionId);
//
//            SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
//            if (securityContext == null) {
//                continue;
//            }
//
//            Authentication authentication = securityContext.getAuthentication();
//
//            PrincipalDetails principalDetail = PrincipalDetails.builder()
//                    .userId(principal.getUserId())
//                    .username(principal.getUsername())
//                    .password(principal.getPassword())
//                    .level(principal.getLevel())
//                    .nickname(memberDetail.getNickname())
//                    .profileImage(memberDetail.getProfileImage())
//                    .role(principal.getRole())
//                    .attributes(principal.getAttributes())
//                    .build();
//
//            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
//                    principalDetail,
//                    authentication.getCredentials(),
//                    authentication.getAuthorities()
//            );
//
//            securityContext.setAuthentication(newAuthentication);
//            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//            jdbcIndexedSessionRepository.save(session);
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public MyEditPageResponseDto getMyEditInfo(String nickname, Integer memberId) {
//        MemberDetail memberDetail = memberDetailRepository.findByNickname(nickname)
//                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
//
//        Integer memberId = memberDetail.getMember().getId();
//        String userNickname = memberDetail.getNickname();
//        String profileImage = memberDetail.getProfileImage();
//        String description = memberDetail.getDescription();
//        String grade = memberDetail.getGrade();
//        String admissionYear = memberDetail.getAdmissionYear();
//        String department = memberDetail.getDepartment();
//        Boolean isMine =
//
//        return MyEditPageResponseDto.builder()
//                .memberId(memberId)
//                .nickname(userNickname)
//                .profileImage(profileImage)
//                .description(description)
//                .grade(grade)
//                .admissionYear(admissionYear)
//                .department(department)
//                .build();
//    }
//
//    @Transactional(readOnly = true)
//    public MyPageResponseDto getMyInfo(String nickname, Integer memberId) {
//        Object result = memberRepository.findByNicknameForMy(nickname)
//                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
//        Object[] arr = (Object[]) result;
//        Boolean isDeleted = (Boolean) arr[0];
//        String username = (String) arr[1];
//        String level = (String) arr[2];
//        String userNickname = (String) arr[3];
//        String profileImage = (String) arr[4];
//        String description = (String) arr[5];
//        String grade = (String) arr[6];
//        String admissionYear = (String) arr[7];
//        String department = (String) arr[8];
//        LocalDateTime createdAt = ((Timestamp) arr[9]).toLocalDateTime();
//        Integer postCount = (Integer) arr[10];
//        Integer commentCount = (Integer) arr[11];
//
//        return MyPageResponseDto.builder()
//                .isDeleted(isDeleted)
//                .username(username)
//                .level(level)
//                .nickname(userNickname)
//                .profileImage(profileImage)
//                .description(description)
//                .grade(grade)
//                .admissionYear(admissionYear)
//                .department(department)
//                .createdAt(createdAt)
//                .postCount(postCount)
//                .commentCount(commentCount)
//                .build();
//    }
//
//}
