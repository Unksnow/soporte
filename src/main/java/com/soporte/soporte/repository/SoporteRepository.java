package com.soporte.soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soporte.soporte.model.Soporte;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Integer>{



}
