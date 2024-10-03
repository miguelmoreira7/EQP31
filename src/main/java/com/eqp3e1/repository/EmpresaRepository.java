package com.eqp3e1.repository;

import com.eqp3e1.model.Empresa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

  Optional<Empresa> findByEmail(String email);
}
