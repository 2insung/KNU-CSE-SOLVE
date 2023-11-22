package com.project.web.repository.post;

import com.project.web.domain.post.PostRecommendCount;
import com.project.web.domain.post.PostScrapCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostScrapCountRepository extends JpaRepository<PostScrapCount, Integer> {
    /*
     게시글의 스크랩 수 출력 함수.
     * postId에 해당하는 게시글의 스크랩 수를 출력함.
     * post의 정보도 사용하기 위해서 fetch join 함.
    */
    @Query("select psc from PostScrapCount psc join fetch psc.post where psc.post.id = :postId")
    Optional<PostScrapCount> findWithPostByPostId(Integer postId);

    /*
     게시글의 스크랩 수 업데이트 함수.
     * postId에 해당하는 게시글의 스크랩 수를 업데이트함.
    */
    @Modifying
    @Query("update PostScrapCount psc set psc.scrapCount = psc.scrapCount + :value where psc.post.id = :postId")
    int updateByPostId(Integer postId, Integer value);

    /*
     게시글의 스크랩 수 삭제 함수.
     * post에 해당하는 게시글의 스크랩 수를 삭제함.
    */
    @Modifying
    @Query("delete from PostScrapCount psc where psc.post.id = :postId")
    int deleteByPostId(Integer postId);
}

