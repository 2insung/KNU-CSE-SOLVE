package com.project.web.repository;

import com.project.web.domain.BoardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardDetailRepository extends JpaRepository<BoardDetail, Long> {
    @Query("SELECT bd FROM BoardDetail bd JOIN FETCH bd.board WHERE bd.board.type = :type")
    Optional<BoardDetail> findByBoardTypeWithFetchJoin(String type);
}
