package com.project.web.service;

import com.project.web.controller.dto.post.PostPreviewResponseDto;
import com.project.web.domain.*;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.BoardDetailRepository;
import com.project.web.repository.BoardRepository;
import com.project.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {


}
