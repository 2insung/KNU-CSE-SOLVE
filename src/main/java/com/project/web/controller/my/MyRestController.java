package com.project.web.controller.my;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.my.dto.MyEditFormRequestDto;
import com.project.web.controller.my.dto.UpdateMyRequestDto;
import com.project.web.controller.my.dto.UpdateMyResponseDto;
import com.project.web.service.my.MyService;
import com.project.web.service.upload.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MyRestController {
    private final MyService myService;
    private final ImageUploadService imageUploadService;

    @PreAuthorize("isAuthenticated() and ((#updateMyRequestDto.userId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @PostMapping("/api/update-my")
    public ResponseEntity<UpdateMyResponseDto> updateMy(@ModelAttribute UpdateMyRequestDto updateMyRequestDto,
                                                        @AuthenticationPrincipal PrincipalDetails principal) {
        Integer userId = updateMyRequestDto.getUserId();
        String nickname = updateMyRequestDto.getNickname();
        String imageURL = imageUploadService.uploadImage(updateMyRequestDto.getFile());
        String description = updateMyRequestDto.getDescription();
        String grade = updateMyRequestDto.getGrade();
        String admissionYear = updateMyRequestDto.getAdmissionYear();
        String department = updateMyRequestDto.getDepartment();

        myService.updateMy(userId, nickname, imageURL, grade, description, admissionYear, department);


        return ResponseEntity.ok(
                UpdateMyResponseDto.builder()
                        .nickname(nickname)
                        .build()
        );
    }


}
