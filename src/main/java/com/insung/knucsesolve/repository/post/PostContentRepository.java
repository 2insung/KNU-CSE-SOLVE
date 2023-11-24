package com.insung.knucsesolve.repository.post;

import com.insung.knucsesolve.domain.post.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostContentRepository extends JpaRepository<PostContent, Integer> {
    /*
     게시글의 내용 출력 함수.
     * postId에 해당하는 게시글의 내용을 출력함.
     * post의 정보도 사용하기 위해서 fetch join 함.
    */
    @Query("select pc from PostContent pc join fetch pc.post where pc.post.id = :postId")
    Optional<PostContent> findWithPostByPostId(Integer postId);

    /*
     게시글의 내용 삭제 함수.
     * post에 해당하는 게시글의 내용을 삭제함.
    */
    @Modifying
    @Query("delete from PostContent pc where pc.post.id = :postId")
    int deleteByPostId(Integer postId);
}
