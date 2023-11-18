package com.project.web.repository.post;

import com.project.web.domain.post.PostRecommendCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRecommendCountRepository extends JpaRepository<PostRecommendCount, Integer> {
    /*
     게시글의 추천수 출력 함수.
     * postId에 해당하는 게시글의 추천수를 출력함.
     * post의 정보도 사용하기 위해서 fetch join 함.
    */
    @Query("select prc from PostRecommendCount prc join fetch prc.post where prc.post.id = :postId")
    Optional<PostRecommendCount> findWithPostByPostId(Integer postId);

    /*
     게시글의 추천수 업데이트 함수.
     * postId에 해당하는 게시글의 추천수를 업데이트함.
    */
    @Modifying
    @Query("update PostRecommendCount prc set prc.recommendCount = prc.recommendCount + :value where prc.post.id = :postId")
    int updateByPostId(Integer postId, Integer value);

    /*
     게시글의 추천수 삭제 함수.
     * postId에 해당하는 게시글의 추천수를 삭제함.
    */
    @Modifying
    @Query("delete from PostRecommendCount prc where prc.post.id = :postId")
    int deleteByPostId(Integer postId);
}
