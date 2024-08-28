package com.eqp3e1.repository;

import com.eqp3e1.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
