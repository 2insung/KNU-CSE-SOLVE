package com.project.web.service.page.general.my;

import com.project.web.repository.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyGlobalService {
    private final MemberProfileRepository memberProfileRepository;


}
