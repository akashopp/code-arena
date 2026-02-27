package com.dsa.codearena.repository;

import com.dsa.codearena.entity.ProblemDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemDetailsRepository extends JpaRepository<ProblemDetails, Integer> {
    List<ProblemDetails> findByTopicId(Integer topicId);
    List<ProblemDetails> findByTopicSlug(String slug);
    Optional <ProblemDetails> findBySlug(String slug);
    Boolean existsBySlug(String slug);
}
