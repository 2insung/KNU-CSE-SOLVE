package com.project.web.repository.board;

import com.project.web.domain.board.BoardStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardStatRepository extends JpaRepository<BoardStat, Integer> {
    /*
     게시판의 게시글 수 업데이트 함수.
     * 게시글 등록, 삭제 시 사용됨.
     * 게시판의 전체 게시글 수는 postCount, 인기 게시글 수는 hotPostCount임.
    */
    @Modifying
    @Query("update BoardStat bs set bs.postCount = bs.postCount + :value, bs.hotPostCount = bs.hotPostCount + :hotValue where bs.board.id = :boardId")
    int updateByBoardId(Integer boardId, Integer value, Integer hotValue);

    /*
     게시판의 통계 출력 함수.
     * 게시판의 통계를 출력함.
     * board의 속성도 사용하기 위해서 fetch join함. 
    */
    @Query("select bs from BoardStat bs join fetch bs.board where bs.board.type = :boardType")
    Optional<BoardStat> findWithBoardByBoardType(String boardType);
}
