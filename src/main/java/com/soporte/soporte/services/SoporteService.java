package com.soporte.soporte.services;

import java.util.List;
import java.util.Optional;

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

    public boolean esCritico(int ticket) {
        Optional<Soporte> optionalSoporte = SoporteRepository.findById(ticket);
        return optionalSoporte.map(soporte -> {
        String texto = (soporte.getMensajes() + " " + soporte.getComentarios()).toLowerCase();
        return texto.contains("urgente") || texto.contains("error") || texto.contains("fallo");
        }).orElse(false);


    }
}