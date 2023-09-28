package com.project.web.service.page.general.board;

import com.project.web.domain.BoardDetail;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.BoardDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardGlobalService {
    private final BoardDetailRepository boardDetailRepository;

    public BoardDetail getBoardDetail(String type) {
        BoardDetail boardDetail = boardDetailRepository.findByBoardTypeWithFetchJoin(type)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시판입니다."));
        return boardDetail;
    }
}
