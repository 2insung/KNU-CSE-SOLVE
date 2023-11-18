package com.project.web.repository.post;

import com.project.web.domain.post.PostHitCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostHitCountRepository extends JpaRepository<PostHitCount, Integer> {
    /*
     게시글의 조회수 업데이트 함수.
     * postId에 해당하는 게시글의 조회수를 업데이트함.
    */
    @Modifying
    @Query("update PostHitCount phc set phc.hitCount = phc.hitCount + :value where phc.post.id = :postId")
    int updateByPostId(Integer postId, Integer value);

    /*
     게시글의 조회수 삭제 함수.
     * postId에 해당하는 게시글의 조회수를 삭제함.
    */
    @Modifying
    @Query("delete from PostHitCount phc where phc.post.id = :postId")
    int deleteByPostId(Integer postId);
}
