package com.project.web.repository.board;

import com.project.web.domain.board.BoardPostCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardPostCountRepository extends JpaRepository<BoardPostCount, Integer> {
    /*
     게시판의 게시글 수 업데이트 함수.
     * 게시글 등록, 삭제 시 사용됨.
    */
    @Modifying
    @Query("update BoardPostCount bpc set bpc.postCount = bpc.postCount + :value where bpc.board.id = :boardId")
    int updateByBoardId(Integer boardId, Integer value);

    @Query("select bpc from BoardPostCount bpc where bpc.board.id = :boardId")
    Optional<BoardPostCount> findByBoardId(Integer boardId);

    @Query("select bpc from BoardPostCount bpc join fetch bpc.board where bpc.board.type = :boardType")
    Optional<BoardPostCount> findWithBoardByBoardType(String boardType);
}
