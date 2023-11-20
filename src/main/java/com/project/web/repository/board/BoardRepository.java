package com.project.web.repository.board;

import com.project.web.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Boolean existsByType(String type);

    Boolean existsByAlias(String alias);

    Optional<Board> findByType(String type);

    /*
     상위 count개 게시판 출력
    */
    @Query("select b from Board b where b.id <= :count")
    List<Board> findTopBoard(Integer count);
}
