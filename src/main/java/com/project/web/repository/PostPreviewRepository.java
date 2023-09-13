package com.project.web.repository;

import com.project.web.domain.PostPreview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPreviewRepository extends JpaRepository<PostPreview, Long>, JpaSpecificationExecutor<PostPreview> {
}
