package com.soporte.soporte.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soporte.soporte.model.Soporte;
import com.soporte.soporte.repository.SoporteRepository;

@Service
public class SoporteService {

    @Autowired
    private SoporteRepository SoporteRepository;

    public List<Soporte> obtenerSoporte() {
        return SoporteRepository.findAll();
    }

    public Soporte buscarSoporteId(int ticket) {
        return SoporteRepository.findById(ticket)
    }

    public Soporte guardarSoporte(Soporte sop) {
        return SoporteRepository.save(sop);
    }

    public String eliminarSoporte(int ticket) {
        SoporteRepository.deleteById(ticket);
        return "Soporte Eliminado";
    }

}
