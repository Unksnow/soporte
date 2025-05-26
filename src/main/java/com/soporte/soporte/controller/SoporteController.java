package com.soporte.soporte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soporte.soporte.model.Soporte;
import com.soporte.soporte.services.SoporteService;

@RestController
@RequestMapping("/api/v0/soporte")
public class SoporteController {
    @Autowired
    private SoporteService ServiceSoporte;

    @GetMapping()
        public List<Soporte> obtenerSoporte(){
            return ServiceSoporte.obtenerSoporte();
        }
    @PostMapping()
        public Soporte guardarSoporte(@RequestBody Soporte sop){
            return ServiceSoporte.guardarSoporte(sop);
        }
    @GetMapping("{ticket}")
        public Soporte buscarSoporte(@PathVariable int ticket){
            return ServiceSoporte.buscarSoporte(ticket);
        }
    @PutMapping("{ticket}")
        public Soporte actualizarSoporte(@PathVariable int ticket, @RequestBody Soporte sop){
            sop.setTicket(ticket);
            return ServiceSoporte.actualizarSoporte(sop);
        }

    @PutMapping("/{ticket}/eliminar")
        public Soporte eliminarLogico(@PathVariable int ticket) {
            return ServiceSoporte.eliminarLogicamente(ticket);
        }

    @DeleteMapping("{ticket}")
        public String eliminarProgreso(@PathVariable int ticket){
            return ServiceSoporte.eliminarSoporte(ticket); 
        }     

    @GetMapping("/{ticket}/es-critico")
        public boolean esCritico(@PathVariable int ticket) {
            return ServiceSoporte.esCritico(ticket);
        }           

}
