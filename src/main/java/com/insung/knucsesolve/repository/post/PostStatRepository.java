package com.insung.knucsesolve.repository.post;

import com.insung.knucsesolve.domain.post.PostStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostStatRepository extends JpaRepository<PostStat, Integer> {
    /*
     게시글의 스크랩 수 출력 함수.
     * postId에 해당하는 게시글의 스크랩 수를 출력함.
     * post의 정보도 사용하기 위해서 fetch join 함.
    */
    @Query("select ps from PostStat ps join fetch ps.post where ps.post.id = :postId")
    Optional<PostStat> findWithPostByPostId(Integer postId);

    /*
     게시글의 스크랩 수 출력 함수.
     * postId에 해당하는 게시글의 스크랩 수를 출력함.
     * post의 정보도 사용하기 위해서 fetch join 함.
    */
    @Query("select ps from PostStat ps join fetch ps.post where ps.post.id = :postId")
    Optional<PostStat> findByPostId(Integer postId);

    /*
     게시글의 스크랩 수 업데이트 함수.
     * postId에 해당하는 게시글의 스크랩 수를 업데이트함.
    */
    @Modifying
    @Query("update PostStat ps set ps.scrapCount = ps.scrapCount + :value where ps.post.id = :postId")
    int updateScrapCountByPostId(Integer postId, Integer value);

    /*
     게시글의 추천수 업데이트 함수.
     * postId에 해당하는 게시글의 추천수를 업데이트함.
    */
    @Modifying
    @Query("update PostStat ps set ps.recommendCount = ps.recommendCount + :value where ps.post.id = :postId")
    int updateRecommedCountByPostId(Integer postId, Integer value);

    /*
     게시글의 조회수 업데이트 함수.
     * postId에 해당하는 게시글의 조회수를 업데이트함.
    */
    @Modifying
    @Query("update PostStat ps set ps.hitCount = ps.hitCount + :value where ps.post.id = :postId")
    int updateHitCountByPostId(Integer postId, Integer value);

    /*
     게시글의 댓글 수 업데이트 함수.
     * 댓글 등록, 삭제 시 사용됨.
     * commentCount는 실제로 보여줄 댓글 개수, totalCommentCount는 댓글 페이지에 사용할 댓글 개수임.
    */
    @Modifying
    @Query("update PostStat ps " +
            "set ps.commentCount = ps.commentCount + :value, " +
            "ps.totalCommentCount = ps.totalCommentCount + :totalValue " +
            "where ps.post.id = :postId")
    int updateCommentCountByPostId(Integer postId, Integer value, Integer totalValue);

    /*
     게시글의 스크랩 수 삭제 함수.
     * post에 해당하는 게시글의 스크랩 수를 삭제함.
    */
    @Modifying
    @Query("delete from PostStat ps where ps.post.id = :postId")
    int deleteByPostId(Integer postId);
}
