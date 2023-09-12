package com.project.web.repository;

import com.project.web.domain.PostTypeAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTypeAccessRepository extends JpaRepository<PostTypeAccess, Long> {
}
