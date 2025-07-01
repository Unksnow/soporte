package com.soporte.soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soporte.soporte.dto.Soporte;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Integer>{



}
