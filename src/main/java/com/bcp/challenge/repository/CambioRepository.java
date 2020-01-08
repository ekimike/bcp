package com.bcp.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcp.challenge.entity.Cambio;

@Repository
public interface CambioRepository extends JpaRepository<Cambio, Long>{

	Optional<Cambio> findByOrigenAndDestino(String origen, String destino);
}
