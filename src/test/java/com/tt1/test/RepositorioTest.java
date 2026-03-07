package com.tt1.test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import proyectoPunto4.DBStub;
import proyectoPunto4.Repositorio;
import proyectoPunto4.ToDo;

class RepositorioTest {

    private Repositorio repositorio;

    private static final Date FECHA_PASADA = Date.valueOf("2020-01-01");
    private static final Date FECHA_FUTURA = Date.valueOf("2099-12-31");

    @BeforeEach
    void setUp() {
        repositorio = new Repositorio(new DBStub());
    }

    @Test
    void testEncontrarToDoDevuelveElTodoCorrecto() {
        repositorio.guardarToDo(new ToDo(1, "Leer libro", "", FECHA_FUTURA, false));
        assertEquals("Leer libro", repositorio.encontrarToDo("1").getNombre());
    }

    @Test
    void testEncontrarToDoInexistenteDevuelveNull() {
        assertNull(repositorio.encontrarToDo("999"));
    }

    @Test
    void testMarcarCompletadoCambiaEstadoATrue() {
        repositorio.guardarToDo(new ToDo(1, "Tarea", "", FECHA_FUTURA, false));
        repositorio.marcarCompletadoToDo("1");
        assertTrue(repositorio.encontrarToDo("1").isCompletado());
    }

    @Test
    void testMarcarCompletadoConIdInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> repositorio.marcarCompletadoToDo("404"));
    }

    @Test
    void testGuardarTodoSeAlmacenaCorrectamente() {
        repositorio.guardarToDo(new ToDo(7, "Siete", "", FECHA_FUTURA, false));
        assertNotNull(repositorio.encontrarToDo("7"));
    }

    @Test
    void testGuardarEmailSeAlmacenaCorrectamente() {
        repositorio.guardarEmail("correo@dominio.com");
        assertTrue(repositorio.obtenerEmails().contains("correo@dominio.com"));
    }

    @Test
    void testObtenerVencidosDevuelveTareaConFechaPasadaNoCompletada() {
        repositorio.guardarToDo(new ToDo(1, "Vencida", "", FECHA_PASADA, false));
        assertEquals(1, repositorio.obtenerTodosVencidos().size());
    }

    @Test
    void testObtenerVencidosNoDevuelveTareaCompletada() {
        repositorio.guardarToDo(new ToDo(1, "Completada", "", FECHA_PASADA, true));
        assertTrue(repositorio.obtenerTodosVencidos().isEmpty());
    }

    @Test
    void testObtenerVencidosNoDevuelveTareaConFechaFutura() {
        repositorio.guardarToDo(new ToDo(1, "Futura", "", FECHA_FUTURA, false));
        assertTrue(repositorio.obtenerTodosVencidos().isEmpty());
    }

    @Test
    void testObtenerSinCompletarDevuelveSoloLasPendientes() {
        repositorio.guardarToDo(new ToDo(1, "Pendiente", "", FECHA_FUTURA, false));
        repositorio.guardarToDo(new ToDo(2, "Completada", "", FECHA_FUTURA, true));
        List<ToDo> sinCompletar = repositorio.obtenerSinCompletar();
        assertEquals(1, sinCompletar.size());
    }

    @Test
    void testObtenerEmailsDevuelveLosEmailsAlmacenados() {
        repositorio.guardarEmail("a@b.com");
        repositorio.guardarEmail("c@d.com");
        assertEquals(2, repositorio.obtenerEmails().size());
    }
}