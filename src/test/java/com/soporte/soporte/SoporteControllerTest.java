package com.soporte.soporte;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soporte.soporte.controller.SoporteController;
import com.soporte.soporte.dto.Soporte;
import com.soporte.soporte.services.SoporteService;

@WebMvcTest(SoporteController.class)
public class SoporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SoporteService soporteService;

    private Soporte soporte;

    @BeforeEach
    void setup() {
        soporte = new Soporte();
        soporte.setTicket(1);
        soporte.setMensajes("Esto es un error urgente");
        soporte.setComentarios("Sistema caído");
        soporte.setActivo(true);
    }

    @Test
    void testObtenerSoporte() throws Exception {
        when(soporteService.obtenerSoporte()).thenReturn(Arrays.asList(soporte));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/soporte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticket").value(1));
    }

    @Test
    void testGuardarSoporte() throws Exception {
        when(soporteService.guardarSoporte(any(Soporte.class))).thenReturn(soporte);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v0/soporte")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(soporte)))
                .andExpect(status().isOk())
                .andExpect(content().string("<h3>Soporte guardado exitosamente con ticket #1</h3>"));
    }

    @Test
    void testBuscarSoporteEncontrado() throws Exception {
        when(soporteService.buscarSoporte(1)).thenReturn(soporte);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/soporte/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket").value(1));
    }

    @Test
    void testBuscarSoporteNoEncontrado() throws Exception {
        when(soporteService.buscarSoporte(2)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/soporte/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("<h3 style='color:red'>Soporte no encontrado con ticket #2</h3>"));
    }

    @Test
    void testActualizarSoporte() throws Exception {
        when(soporteService.actualizarSoporte(any(Soporte.class))).thenReturn(soporte);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v0/soporte/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(soporte)))
                .andExpect(status().isOk())
                .andExpect(content().string("<h3>Soporte actualizado correctamente para el ticket #1</h3>"));
    }

    @Test
    void testEliminarLogico() throws Exception {
        when(soporteService.eliminarLogicamente(1)).thenReturn(soporte);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v0/soporte/1/eliminar"))
                .andExpect(status().isOk())
                .andExpect(content().string("<h3>Soporte marcado como eliminado (lógicamente) para ticket #1</h3>"));
    }

    @Test
    void testEliminarProgreso() throws Exception {
        when(soporteService.eliminarSoporte(1)).thenReturn("Soporte Eliminado");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v0/soporte/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("<h3>Soporte Eliminado</h3>"));
    }

    @Test
    void testEsCriticoTrue() throws Exception {
        when(soporteService.esCritico(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/soporte/1/es-critico"))
                .andExpect(status().isOk())
                .andExpect(content().string("<h3 style='color:red'>El ticket #1 es crítico</h3>"));
    }

    @Test
    void testEsCriticoFalse() throws Exception {
        when(soporteService.esCritico(1)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/soporte/1/es-critico"))
                .andExpect(status().isOk())
                .andExpect(content().string("<h3>El ticket #1 no es crítico</h3>"));
    }
}


