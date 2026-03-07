package com.tt1.test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import proyectoPunto4.DBStub;
import proyectoPunto4.MailerStub;
import proyectoPunto4.Repositorio;
import proyectoPunto4.Servicio;
import proyectoPunto4.ToDo;

class ServicioTest {

    private Repositorio repositorio;
    private Servicio servicio;

    private static final Date FECHA_FUTURA = Date.valueOf("2099-12-31");
    private static final Date FECHA_PASADA = Date.valueOf("2020-01-01");

    @BeforeEach
    void setUp() {
        repositorio = new Repositorio(new DBStub());
        servicio = new Servicio(repositorio, new MailerStub());
    }

    @Test
    void testCrearTodoSeAlmacenaEnElRepositorio() {
        servicio.crearTodo("Mi tarea", FECHA_FUTURA);
        assertEquals(1, repositorio.obtenerSinCompletar().size());
    }

    @Test
    void testCrearVariasTareasSeAlmacenanTodasCorrectamente() {
        servicio.crearTodo("Tarea A", FECHA_FUTURA);
        servicio.crearTodo("Tarea B", FECHA_FUTURA);
        assertEquals(2, repositorio.obtenerSinCompletar().size());
    }

    @Test
    void testAgregarEmailSeAlmacenaEnElRepositorio() {
        servicio.agregarDireccionCorreoAAgenda("usuario@dominio.com");
        assertTrue(repositorio.obtenerEmails().contains("usuario@dominio.com"));
    }

    @Test
    void testMarcarTareaFinalizadaLaQuitaDePendientes() {
        servicio.crearTodo("Tarea", FECHA_FUTURA);
        String id = String.valueOf(repositorio.obtenerSinCompletar().get(0).getId());
        servicio.marcarTareaFinalizada(id);
        assertTrue(repositorio.obtenerSinCompletar().isEmpty());
    }

    @Test
    void testMarcarTareaInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> servicio.marcarTareaFinalizada("404"));
    }

    @Test
    void testConsultarSinCompletarDevuelveSoloLasPendientes() {
        servicio.crearTodo("Pendiente", FECHA_FUTURA);
        servicio.crearTodo("A completar", FECHA_FUTURA);
        String id = String.valueOf(repositorio.obtenerSinCompletar().get(0).getId());
        servicio.marcarTareaFinalizada(id);
        assertEquals(1, servicio.consultarToDosSinCompletar("").size());
    }

    @Test
    void testConTareaVencidaNoLanzaExcepcionAlOperar() {
        repositorio.guardarToDo(new ToDo(999, "Vencida", "", FECHA_PASADA, false));
        repositorio.guardarEmail("alerta@test.com");
        assertDoesNotThrow(() -> servicio.crearTodo("Nueva", FECHA_FUTURA));
    }
}