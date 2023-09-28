package com.project.web.repository;

import com.project.web.domain.BoardAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardAccessRepository extends JpaRepository<BoardAccess, Long> {
}
