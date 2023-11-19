package com.project.web.repository.comment;

import com.project.web.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    /*
     댓글 페이지 출력 함수.
     * limit -> 한 페이지에 출력할 댓글 개수
     * offset -> 출력을 시작할 위치, 현재 page * limit
     * from 절 안의 서브 쿼리를 통해 출력할 댓글의 id를 먼저 출력. where 및 order 절에 사용된 속성은 하나의 결합 인덱스로 이루어짐.
     * from 절 안의 서브 쿼리를 작성하기 위해서 네이티브 쿼리 사용.
    */
    @Query(nativeQuery = true,
            value = "select c.id, c.post_id, c.member_id, md.nickname as authorNickname, md.profile_image, c.parent_member_id, md2.nickname as parentAuthorNickname, c.is_post_author, c.is_root, c.is_root_child, c.is_deleted, c.body, c.created_at, crc.recommend_count " +
                    "from (select tc.id from comment tc where tc.post_id = :postId order by tc.group_created_at, tc.root_comment_id, tc.created_at limit :limit offset :offset) as temp " +
                    "inner join comment c on c.id = temp.id " +
                    "inner join member_detail md on c.member_id = md.member_id " +
                    "inner join member_detail md2 on c.parent_member_id = md2.member_id " +
                    "inner join comment_recommend_count crc on c.id = crc.comment_id " +
                    "order by c.group_created_at, c.root_comment_id, c.created_at")
    List<Object[]> findPageByPostId(Integer postId, Integer limit, Integer offset);

    /*
     사용자 작성 댓글 페이지 출력 함수.
     * limit -> 한 페이지에 출력할 댓글 개수
     * offset -> 출력을 시작할 위치, 현재 page * limit
     * from 절 안의 서브 쿼리를 통해 출력할 댓글의 id를 먼저 출력. where 및 order 절에 사용된 속성은 하나의 결합 인덱스로 이루어짐.
     * from 절 안의 서브 쿼리를 작성하기 위해서 네이티브 쿼리 사용.
    */
    @Query(nativeQuery = true,
            value = "select c.id, c.member_id, c.post_id, c.is_deleted, c.body, c.created_at, b.id as boardId, b.type, b.alias, pc.title " +
                    "from (select tc.id from comment tc where tc.member_id = :memberId order by tc.created_at desc limit :limit offset :offset) as temp " +
                    "inner join comment c on c.id = temp.id " +
                    "inner join post p on c.post_id = p.id " +
                    "inner join post_content pc on p.id = pc.post_id " +
                    "inner join board b on b.id = p.board_id " +
                    "order by c.created_at desc")
    List<Object[]> findMyCommentByMemberId(Integer memberId, Integer limit, Integer offset);

    /*
     사용자 작성 댓글 개수 출력 함수.
     * 사용자가 작성한 전체 댓글 개수를 구하는 것이 아님.
     * 만약 사용자가 작성한 댓글 수가 limit 개를 넘어간다면, count는 limit까지만 함.
    */
    @Query(nativeQuery = true,
            value = "select count(*) from (select id from comment where member_id = :memberId limit :limit) as temp")
    int countMyComment(Integer memberId, Integer limit);

    /*
     게시글의 댓글 id 출력 함수
     * postId에 해당하는 게시글의 댓글들의 id를 출력함.
    */
    @Query("select c.id from Comment c where c.post.id = :postId")
    List<Integer> findIdsByPostId(Integer postId);

    /*
     댓글의 isDeleted 속성 업데이트 함수.
     * 삭제될 댓글에 사용됨.
     * 만약 삭제될 댓글이 루트 댓글이며, 자식 댓글이 삭제하는 시점에도 존재하는 경우, 삭제된 댓글임을 나타낼 수 있음.
    */
    @Modifying
    @Query("update Comment c set c.isDeleted = :isDeleted where c.id = :commentId and c.isDeleted = false")
    int updateIsDeleted(Integer commentId, Boolean isDeleted);

    /*
     댓글 삭제 함수.
     * comment id에 해당하는 댓글 레코드들을 삭제함.
    */
    @Modifying
    @Query("delete from Comment c where c.id in :commentIds")
    int deleteByCommentIds(List<Integer> commentIds);

    /*
     댓글의 자식 댓글 수 업데이트 함수.
     * 매개변수로 입력된 value 를 더함.
     * 대댓글에는 이 함수가 사용되지 않을 것임. 루트 댓글에만 자식 댓글 수의 변경이 있기 때문.
    */
    @Modifying
    @Query("update Comment c set c.childCount = c.childCount + :value where c.id = :commentId")
    int updateChildCountByCommentId(Integer commentId, Integer value);
}
