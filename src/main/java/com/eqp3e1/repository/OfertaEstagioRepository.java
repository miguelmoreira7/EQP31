package com.eqp3e1.repository;

import com.eqp3e1.model.OfertaEstagio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfertaEstagioRepository extends JpaRepository<OfertaEstagio, Long> {
}
