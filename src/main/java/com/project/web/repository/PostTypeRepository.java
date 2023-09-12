package com.project.web.repository;

import com.project.web.domain.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType, Long> {
    Optional<PostType> findByType(String type);
}
