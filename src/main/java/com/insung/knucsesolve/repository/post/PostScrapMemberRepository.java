package com.insung.knucsesolve.repository.post;

import com.insung.knucsesolve.domain.post.PostScrapMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostScrapMemberRepository extends JpaRepository<PostScrapMember, Integer> {
    /*
     사용자의 특정 게시글에 스크랩한 기록 삭제 함수.
     * 매개변수로 입력된 post id, member id에 일치하는 레코드를 삭제.
    */
    @Modifying
    @Query("delete from PostScrapMember psm where psm.post.id = :postId and psm.member.id = :memberId")
    int deleteByPostAndMemberId(Integer postId, Integer memberId);

    /*
     게시글에 스크랩한 사용자가 이전에 스크랩했는지 true/false로 반환하는 함수.
     * 매개변수로 입력된 post id, member id에 일치하는 레코드가 존재한다면 count(psm)이 1 이상이기 때문에, true 반환.
    */
    @Query("select count(psm) > 0 from PostScrapMember psm where psm.post.id = :postId and psm.member.id = :memberId")
    Boolean existsByPostAndMemberId(Integer postId, Integer memberId);

    /*
     사용자 스크랩 게시글 페이지(MyScrap) 출력 함수.
     * limit -> 한 페이지에 출력할 댓글 개수
     * offset -> 출력을 시작할 위치, 현재 page * limit
     * from 절 안의 서브 쿼리를 통해 출력할 게시글의 id를 먼저 출력. where 및 order 절에 사용된 속성은 하나의 결합 인덱스로 이루어짐.
     * from 절 안의 서브 쿼리를 작성하기 위해서 네이티브 쿼리 사용.
    */
    @Query(nativeQuery = true,
            value = "select psm.member_id, p.id, p.created_at, pc.title, b.alias " +
                    "from (select tp.id from post_scrap_member tp where tp.member_id = :memberId order by tp.scrapped_at desc limit :limit offset :offset) as temp " +
                    "inner join post_scrap_member psm on psm.id = temp.id " +
                    "inner join post p on p.id = psm.post_id " +
                    "inner join post_content pc on p.id = pc.post_id " +
                    "inner join board b on p.board_id = b.id " +
                    "order by psm.scrapped_at desc")
    List<Object[]> findMyScrapDtosByMemberId(Integer memberId, Integer limit, Integer offset);

    /*
     사용자 스크랩 게시글 개수 출력 함수.
     * 사용자가 스크랩한 전체 게시글 개수를 구하는 것이 아님.
     * 만약 사용자가 작성한 게시글 수가 limit 개를 넘어간다면, count는 limit까지만 함.
    */
    @Query(nativeQuery = true,
            value = "select count(*) from (select id from post_scrap_member where member_id = :memberId limit :limit) as temp")
    int countMyScraps(Integer memberId, Integer limit);

    /*
     게시글에 스크랩한 멤버 삭제 함수.
     * 매개변수로 입력된 post id에 일치하는 레코드를 삭제.
    */
    @Modifying
    @Query("delete from PostScrapMember psm where psm.post.id = :postId")
    int deleteByPostId(Integer postId);
}
