package com.soporte.soporte;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.soporte.soporte.dto.Soporte;
import com.soporte.soporte.repository.SoporteRepository;
import com.soporte.soporte.services.SoporteService;

@SpringBootTest
public class SoporteServiceTest {

    @Autowired
    private SoporteService soporteService;

    @MockitoBean
    private SoporteRepository soporteRepository;

    private Soporte soporte;

    @BeforeEach
    void setUp() {
        soporte = new Soporte();
        soporte.setTicket(1);
        soporte.setMensajes("URGENTE: error");
        soporte.setComentarios("Falló el sistema");
        soporte.setActivo(true);
    }

    @Test
    void testObtenerSoporte() {
        Soporte inactivo = new Soporte();
        inactivo.setTicket(2);
        inactivo.setActivo(false);

        when(soporteRepository.findAll()).thenReturn(Arrays.asList(soporte, inactivo));

        List<Soporte> activos = soporteService.obtenerSoporte();

        assertEquals(1, activos.size());
        assertTrue(activos.get(0).isActivo());
    }

    @Test
    void testBuscarSoporteExistente() {
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));

        Soporte resultado = soporteService.buscarSoporte(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTicket());
    }

    @Test
    void testGuardarSoporte() {
        when(soporteRepository.save(soporte)).thenReturn(soporte);

        Soporte guardado = soporteService.guardarSoporte(soporte);

        assertEquals(soporte, guardado);
    }

    @Test
    void testEliminarSoporte() {
        doNothing().when(soporteRepository).deleteById(1);

        String mensaje = soporteService.eliminarSoporte(1);

        assertEquals("Soporte Eliminado", mensaje);
    }

    @Test
    void testActualizarSoporte() {
        when(soporteRepository.save(soporte)).thenReturn(soporte);

        Soporte actualizado = soporteService.actualizarSoporte(soporte);

        assertNotNull(actualizado);
        assertEquals(soporte.getTicket(), actualizado.getTicket());
    }

    @Test
    void testEliminarLogicamente() {
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));
        when(soporteRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Soporte eliminado = soporteService.eliminarLogicamente(1);

        assertFalse(eliminado.isActivo());
    }

    @Test
    void testEsCritico() {
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));

        boolean critico = soporteService.esCritico(1);

        assertTrue(critico);
    }

    @Test
    void testNoEsCritico() {
    Soporte soporte = new Soporte();
    soporte.setTicket(1);
    soporte.setMensajes("Información general");
    soporte.setComentarios("Todo funcionando correctamente");
    soporte.setActivo(true);

    when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));

    boolean critico = soporteService.esCritico(1);

    assertFalse(critico);
}

    @Test
    void testBuscarSoporteInexistente() {
        when(soporteRepository.findById(999)).thenReturn(Optional.empty());

        Soporte resultado = soporteService.buscarSoporte(999);

        assertNull(resultado);
    }
}
