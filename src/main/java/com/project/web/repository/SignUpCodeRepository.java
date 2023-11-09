package com.project.web.repository;

import com.project.web.domain.SignUpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignUpCodeRepository extends JpaRepository<SignUpCode, Integer> {
    Optional<SignUpCode> findByUsername(String username);
    Boolean existsByUsername(String username);
}
