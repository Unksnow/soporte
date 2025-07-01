package com.soporte.soporte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soporte.soporte.dto.Soporte;
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
        public ResponseEntity<String> guardarSoporte(@RequestBody Soporte sop) {
        Soporte guardado = ServiceSoporte.guardarSoporte(sop);
        return ResponseEntity.ok("<h3>Soporte guardado exitosamente con ticket #" + guardado.getTicket() + "</h3>");
    }
    @GetMapping("{ticket}")
        public ResponseEntity<?> buscarSoporte(@PathVariable int ticket) {
        Soporte sop = ServiceSoporte.buscarSoporte(ticket);
        if (sop == null) {
            return ResponseEntity.status(404).body("<h3 style='color:red'>Soporte no encontrado con ticket #" + ticket + "</h3>");
        }
        return ResponseEntity.ok(sop);
    }
    @PutMapping("{ticket}")
        public ResponseEntity<String> actualizarSoporte(@PathVariable int ticket, @RequestBody Soporte sop) {
        sop.setTicket(ticket);
        Soporte actualizado = ServiceSoporte.actualizarSoporte(sop);
        return ResponseEntity.ok("<h3>Soporte actualizado correctamente para el ticket #" + ticket + "</h3>");
    }

    @DeleteMapping("/{ticket}/eliminar")
        public ResponseEntity<String> eliminarLogico(@PathVariable int ticket) {
        Soporte eliminado = ServiceSoporte.eliminarLogicamente(ticket);
        if (eliminado == null) {
            return ResponseEntity.status(404).body("<h3 style='color:red'>No se encontró el ticket #" + ticket + " para eliminación lógica</h3>");
        }
        return ResponseEntity.ok("<h3>Soporte marcado como eliminado (lógicamente) para ticket #" + ticket + "</h3>");
    }

    @DeleteMapping("{ticket}")
        public ResponseEntity<String> eliminarProgreso(@PathVariable int ticket) {
        String mensaje = ServiceSoporte.eliminarSoporte(ticket);
        return ResponseEntity.ok("<h3>" + mensaje + "</h3>");
    }    

    @GetMapping("/{ticket}/es-critico")
        public ResponseEntity<String> esCritico(@PathVariable int ticket) {
        boolean critico = ServiceSoporte.esCritico(ticket);
        String respuesta = critico
                ? "<h3 style='color:red'>El ticket #" + ticket + " es crítico</h3>"
                : "<h3>El ticket #" + ticket + " no es crítico</h3>";
        return ResponseEntity.ok(respuesta);
    }          

}
