package com.soporte.soporte.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soporte.soporte.dto.Soporte;
import com.soporte.soporte.repository.SoporteRepository;

@Service
public class SoporteService {

    private final SoporteRepository SoporteRepository;

    @Autowired
    public SoporteService(SoporteRepository SoporteRepository) {
        this.SoporteRepository = SoporteRepository;
    }

    public List<Soporte> obtenerSoporte() {
        return SoporteRepository.findAll().stream()
            .filter(Soporte::isActivo)
            .collect(Collectors.toList());
    }
     
    public Soporte buscarSoporte(int ticket) {
        return SoporteRepository.findById(ticket).orElse(null);
    }

    public Soporte guardarSoporte(Soporte sop) {
        return SoporteRepository.save(sop);
    }

    public String eliminarSoporte(int ticket) {
        SoporteRepository.deleteById(ticket);
        return "Soporte Eliminado";
    }

    public Soporte actualizarSoporte(Soporte sop){
        return SoporteRepository.save(sop);
    }

    public Soporte eliminarLogicamente(int ticket) {
        Soporte soporte = SoporteRepository.findById(ticket)
        .orElseThrow(() -> new RuntimeException("Soporte no encontrado con el ticket: " + ticket));
        soporte.setActivo(false);
        return SoporteRepository.save(soporte);
    }

    public boolean esCritico(int ticket) {
        Optional<Soporte> optionalSoporte = SoporteRepository.findById(ticket);
        return optionalSoporte.map(soporte -> {
        String texto = (soporte.getMensajes() + " " + soporte.getComentarios()).toLowerCase();
        return texto.contains("urgente") || texto.contains("error") || texto.contains("fallo");
        }).orElse(false);


    }
}