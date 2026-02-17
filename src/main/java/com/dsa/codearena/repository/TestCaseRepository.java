package com.dsa.codearena.repository;

import com.dsa.codearena.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Integer> {
    List<TestCase> findByProblemId(Integer problemId);
}
