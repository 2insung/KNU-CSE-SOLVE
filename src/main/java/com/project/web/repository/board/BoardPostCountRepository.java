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
     * 게시판의 전체 게시글 수는 postCount, 인기 게시글 수는 hotPostCount임.
    */
    @Modifying
    @Query("update BoardPostCount bpc set bpc.postCount = bpc.postCount + :value, bpc.hotPostCount = bpc.hotPostCount + :hotValue where bpc.board.id = :boardId")
    int updateByBoardId(Integer boardId, Integer value, Integer hotValue);

    /*
     게시판의 게시글 수 출력 함수.
     * 게시판의 게시글 수를 출력함.
     * board의 속성도 사용하기 위해서 fetch join함. 
    */
    @Query("select bpc from BoardPostCount bpc join fetch bpc.board where bpc.board.type = :boardType")
    Optional<BoardPostCount> findWithBoardByBoardType(String boardType);
}
