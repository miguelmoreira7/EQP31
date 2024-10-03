package com.eqp3e1.repository;

import com.eqp3e1.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @Query("SELECT DISTINCT a FROM Aluno a LEFT JOIN FETCH a.candidaturas")
    List<Aluno> findAllWithCandidaturas();

    Optional<Aluno> findByUsername(String username);
}