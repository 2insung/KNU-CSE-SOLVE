package com.project.web.repository.post;

import com.project.web.domain.post.DeletedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedPostRepository extends JpaRepository<DeletedPost, Integer> {
}
