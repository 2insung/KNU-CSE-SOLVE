package com.project.web.repository.comment;

import com.project.web.domain.comment.DeletedComment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeletedCommentJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<DeletedComment> deletedComments) {
        String sql = "insert into deleted_comment(comment_id, member_id, post_id, parent_member_id, root_comment_id, is_root, is_root_child, body, recommend_count)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                deletedComments,
                deletedComments.size(),
                (PreparedStatement ps, DeletedComment deletedComment) -> {
                    ps.setInt(1, deletedComment.getCommentId());
                    ps.setInt(2, deletedComment.getMemberId());
                    ps.setInt(3, deletedComment.getPostId());
                    ps.setInt(4, deletedComment.getParentMemberId());
                    ps.setInt(5, deletedComment.getRootCommentId());
                    ps.setBoolean(6, deletedComment.getIsRoot());
                    ps.setBoolean(7, deletedComment.getIsRootChild());
                    ps.setString(8, deletedComment.getBody());
                    ps.setInt(9, deletedComment.getRecommendCount());
                });
    }
}
