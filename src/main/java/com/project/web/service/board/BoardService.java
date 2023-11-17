package com.project.web.service.board;

import com.project.web.controller.community.dto.board.BoardDto;
import com.project.web.controller.community.dto.board.BoardPreviewDto;
import com.project.web.domain.board.Board;
import com.project.web.domain.board.BoardPostCount;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.board.BoardPostCountRepository;
import com.project.web.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardPostCountRepository boardPostCountRepository;

    /*
      boardType으로 존재하는 게시판인지 true/false로 반환하는 함수.
    */
    @Transactional(readOnly = true)
    public void existsByBoardType(String boardType) {
        if (!boardRepository.existsByType(boardType)) {
            throw new Error404Exception("존재하지 않는 게시판입니다.");
        }
    }

    /*
      게시판 정보 출력 함수.
     * 현재 게시판의 정보를 출력하는 함수.
    */
    @Transactional(readOnly = true)
    public BoardDto getBoard(String boardType) {
        Board board = boardRepository.findByType(boardType)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시판입니다."));

        return BoardDto.builder()
                .id(board.getId())
                .type(board.getType())
                .alias(board.getAlias())
                .description(board.getDescription())
                .postCount(null)
                .build();
    }

    /*
      게시판 정보 출력 함수(2).
     * 현재 게시판의 정보를 출력하는 함수.
     * 게시판의 게시글 수에 대한 정보도 제공함.
    */
    @Transactional(readOnly = true)
    public BoardDto getBoardWithPostCount(String boardType) {
        BoardPostCount boardPostCount = boardPostCountRepository.findWithBoardByBoardType(boardType)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시판입니다."));
        Board board = boardPostCount.getBoard();

        return BoardDto.builder()
                .id(board.getId())
                .type(board.getType())
                .alias(board.getAlias())
                .description(board.getDescription())
                .postCount(boardPostCount.getPostCount())
                .hotPostCount(boardPostCount.getHotPostCount())
                .build();
    }

    /*
      게시판 리스트 출력 함수.
     * 모든 게시판의 정보를 출력하는 함수.
    */
    @Transactional(readOnly = true)
    public List<BoardPreviewDto> getBoardPreviewList() {
        List<Board> boardList = boardRepository.findAll();

        return boardList.stream()
                .map((board) ->
                        BoardPreviewDto.builder()
                                .type(board.getType())
                                .alias(board.getAlias())
                                .category(board.getCategory())
                                .build()
                )
                .collect(Collectors.toList());
    }

    /*
      게시판 저장 함수.
     * 새로운 게시판을 저장하는 함수.
    */
    @Transactional
    public void saveBoard(String type, String alias, String description, String category) {
        if (boardRepository.existsByType(type)) {
            throw new Error404Exception("존재하는 타입입니다.");
        }
        if (boardRepository.existsByAlias(alias)) {
            throw new Error404Exception("존재하는 이름입니다.");
        }

        Board board = Board.builder()
                .type(type)
                .alias(alias)
                .description(description)
                .category(category)
                .build();
        boardRepository.save(board);

        BoardPostCount boardPostCount = BoardPostCount.builder()
                .board(board)
                .postCount(0)
                .hotPostCount(0)
                .build();
        boardPostCountRepository.save(boardPostCount);
    }
}
