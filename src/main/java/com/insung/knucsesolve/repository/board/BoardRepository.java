package com.insung.knucsesolve.repository.board;

import com.insung.knucsesolve.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Boolean existsByAlias(String alias);

    /*
     상위 count개 게시판 출력
    */
    @Query("select b from Board b where b.id <= :count")
    List<Board> findBoards(Integer count);
}
