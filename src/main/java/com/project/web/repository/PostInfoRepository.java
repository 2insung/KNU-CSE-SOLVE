package com.project.web.repository;

import com.project.web.domain.PostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInfoRepository extends JpaRepository<PostInfo, Long> {
}
