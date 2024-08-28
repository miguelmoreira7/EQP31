package com.eqp3e1.repository;

import com.eqp3e1.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
