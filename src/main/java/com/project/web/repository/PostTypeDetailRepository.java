package com.project.web.repository;

import com.project.web.domain.PostTypeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTypeDetailRepository extends JpaRepository<PostTypeDetail, Long> {
    PostTypeDetail findByPostType_Type(String type);
}
