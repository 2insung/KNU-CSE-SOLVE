package com.project.web.service.page.general.my;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.domain.Member;
import com.project.web.exception.Error403Exception;
import com.project.web.exception.Error404Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class MyPwEditPageService {

}
