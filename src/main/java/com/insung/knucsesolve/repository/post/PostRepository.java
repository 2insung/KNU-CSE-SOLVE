package com.insung.knucsesolve.repository.post;

import com.insung.knucsesolve.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    /*
     게시글 페이지(Post Preview) 출력 함수.
     * limit -> 한 페이지에 출력할 댓글 개수
     * offset -> 출력을 시작할 위치, 현재 page * limit
     * from 절 안의 서브 쿼리를 통해 출력할 게시글의 id를 먼저 출력. where 및 order 절에 사용된 속성은 하나의 결합 인덱스로 이루어짐.
     * from 절 안의 서브 쿼리를 작성하기 위해서 네이티브 쿼리 사용.
    */
    @Query(nativeQuery = true,
            value = "select p.id, p.member_id, p.is_notice, p.is_hot, p.created_at, pc.title, pc.summary, pc.thumbnail, ps.hit_count, ps.recommend_count, ps.comment_count, md.nickname, md.profile_image  " +
                    "from (select tp.id from post tp where tp.board_id = :boardId order by tp.priority desc, tp.created_at desc limit :limit offset :offset) as temp " +
                    "inner join post p on p.id = temp.id " +
                    "inner join post_content pc on p.id = pc.post_id " +
                    "inner join post_stat ps on p.id = ps.post_id " +
                    "inner join member_detail md on p.member_id = md.member_id " +
                    "order by p.priority desc, p.created_at desc")
    List<Object[]> findPostPreviewDtosByBoardId(Integer boardId, Integer limit, Integer offset);

    /*
     인기 게시글 페이지(Hot Post Preview) 출력 함수.
     * limit -> 한 페이지에 출력할 댓글 개수
     * offset -> 출력을 시작할 위치, 현재 page * limit
     * from 절 안의 서브 쿼리를 통해 출력할 게시글의 id를 먼저 출력. where 및 order 절에 사용된 속성은 하나의 결합 인덱스로 이루어짐.
     * from 절 안의 서브 쿼리를 작성하기 위해서 네이티브 쿼리 사용.
    */
    @Query(nativeQuery = true,
            value = "select p.id, p.member_id, p.is_notice, p.is_hot, p.created_at, pc.title, pc.summary, pc.thumbnail, ps.hit_count, ps.recommend_count, ps.comment_count, md.nickname, md.profile_image  " +
                    "from (select tp.id from post tp where tp.board_id = :boardId and tp.is_hot = true order by tp.hot_registered_at desc limit :limit offset :offset) as temp " +
                    "inner join post p on p.id = temp.id " +
                    "inner join post_content pc on p.id = pc.post_id " +
                    "inner join post_stat ps on p.id = ps.post_id " +
                    "inner join member_detail md on p.member_id = md.member_id " +
                    "order by p.hot_registered_at desc")
    List<Object[]> findHotPostPreviewDtosByBoardId(Integer boardId, Integer limit, Integer offset);

    /*
     사용자 작성 게시글 페이지(My Post Preview) 출력 함수.
     * limit -> 한 페이지에 출력할 댓글 개수
     * offset -> 출력을 시작할 위치, 현재 page * limit
     * from 절 안의 서브 쿼리를 통해 출력할 게시글의 id를 먼저 출력. where 및 order 절에 사용된 속성은 하나의 결합 인덱스로 이루어짐.
     * from 절 안의 서브 쿼리를 작성하기 위해서 네이티브 쿼리 사용.
    */
    @Query(nativeQuery = true,
            value = "select p.id, p.member_id, p.created_at, pc.title, b.id as board_id, b.alias " +
                    "from (select tp.id from post tp where tp.member_id = :memberId order by tp.created_at desc limit :limit offset :offset) as temp " +
                    "inner join post p on p.id = temp.id " +
                    "inner join post_content pc on p.id = pc.post_id " +
                    "inner join board b on p.board_id = b.id " +
                    "order by p.created_at desc")
    List<Object[]> findMyPostDtosByMemberId(Integer memberId, Integer limit, Integer offset);

    /*
     사용자 작성 게시글 개수 출력 함수.
     * 사용자가 작성한 전체 게시글 개수를 구하는 것이 아님.
     * 만약 사용자가 작성한 게시글 수가 limit 개를 넘어간다면, count는 limit까지만 함.
    */
    @Query(nativeQuery = true,
            value = "select count(*) from (select id from post where member_id = :memberId limit :limit) as temp")
    int countMyPosts(Integer memberId, Integer limit);

    /*
     게시글(Post) 출력 함수.
     * '/board/{type}/post/{postId}?page=xxx...' 요청 시 Post 관련 정보 출력.
    */
    @Query("select p.id, p.member.id, p.isNotice, p.isHot, p.createdAt, p.category, pc.title, pc.body, pc.updatedAt, ps.hitCount, ps.recommendCount, ps.commentCount, ps.totalCommentCount, ps.scrapCount, md.nickname, md.profileImage " +
            "from Post p " +
            "inner join PostContent pc on pc.post = p " +
            "inner join PostStat ps on ps.post = p " +
            "inner join MemberDetail md on p.member = md.member " +
            "where p.id = :postId")
    Optional<Object> findPostDtoByPostId(Integer postId);

    /*
     게시판 출력 함수.
     * '/post/{postId}?page=xxx...' 요청 시 Board 관련 정보 출력.
    */
    @Query("select b.id, b.alias, b.description, bs.postCount, bs.hotPostCount " +
            "from Post p " +
            "inner join Board b on p.board = b " +
            "inner join BoardStat bs on bs.board = b " +
            "where p.id = :postId")
    Optional<Object> findBoardDtoByPostId(Integer postId);

    /*
     게시글의 isDeleted 속성 업데이트 함수.
     * 삭제될 게시글에 사용됨.
    */
    @Modifying
    @Query("update Post p set p.isDeleted = :isDeleted where p.id = :postId and p.isDeleted = false")
    int updateIsDeleted(Integer postId, Boolean isDeleted);

    /*
     게시글 삭제 함수.
     * post 테이블에 존재하는 레코드를 삭제함.
    */
    @Modifying
    @Query("delete from Post p where p.id = :postId")
    int deleteByPostId(Integer postId);

    /*
     게시판의 상위 limit개 게시글 출력 함수.
     * 상위 limit개의 게시글(생성시간 기준)을 출력하는 함수.
    */
    @Query(nativeQuery = true,
            value = "select p.id, p.created_at, pc.title " +
                    "from post p " +
                    "inner join post_content pc on pc.post_id = p.id " +
                    "inner join board b on p.board_id = b.id " +
                    "where p.board_id = :boardId " +
                    "order by p.created_at desc " +
                    "limit :limit")
    List<Object[]> findTopPostDtos(Integer boardId, Integer limit);

    /*
     상위 인기 게시글 출력 함수.
     * 모든 게시판 중에 상위 인기글(인기글 등록 시간 기준) limit개를 출력하는 함수.
    */
    @Query(nativeQuery = true,
            value = "select p.id, p.created_at, pc.title, ps.recommend_count, ps.comment_count " +
                    "from post p " +
                    "inner join board b on p.board_id = b.id  " +
                    "inner join post_content pc on pc.post_id = p.id " +
                    "inner join post_stat ps on ps.post_id = p.id " +
                    "where p.is_hot = true " +
                    "order by p.hot_registered_at desc " +
                    "limit :limit")
    List<Object[]> findTopHotPostDtos(Integer limit);
}
