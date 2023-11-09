package com.project.web.service.board;

import com.project.web.controller.community.dto.board.BoardDto;
import com.project.web.domain.board.Board;
import com.project.web.domain.board.BoardPostCount;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.board.BoardPostCountRepository;
import com.project.web.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardPostCountRepository boardPostCountRepository;

    @Transactional(readOnly = true)
    public void existsByBoardType(String boardType) {
        if (!boardRepository.existsByType(boardType)) {
            throw new Error404Exception("존재하지 않는 게시판입니다.");
        }
    }

    @Transactional(readOnly = true)
    public BoardDto getBoard(String boardType) {
        Board board = boardRepository.findByType(boardType)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시판입니다."));

        return BoardDto.builder()
                .boardId(board.getId())
                .type(board.getType())
                .alias(board.getAlias())
                .description(board.getDescription())
                .postCount(null)
                .build();
    }

    @Transactional(readOnly = true)
    public BoardDto getBoardWithPostCount(String boardType) {
        BoardPostCount boardPostCount = boardPostCountRepository.findWithBoardByBoardType(boardType)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시판입니다."));
        Board board = boardPostCount.getBoard();

        return BoardDto.builder()
                .boardId(board.getId())
                .type(board.getType())
                .alias(board.getAlias())
                .description(board.getDescription())
                .postCount(boardPostCount.getPostCount())
                .build();
    }
}
