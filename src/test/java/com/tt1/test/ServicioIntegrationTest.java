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

class ServicioIntegrationTest {

	private Servicio servicio;
	private Repositorio repositorio;

	private static final Date FECHA_FUTURA = Date.valueOf("2099-12-31");
	private static final Date FECHA_PASADA = Date.valueOf("2020-06-01");

	@BeforeEach
	void setUp() {
		repositorio = new Repositorio(new DBStub());
		servicio = new Servicio(repositorio, new MailerStub());
	}


	@Test
	void testCrearTodoYComprobarQueExisteEnElRepositorio() {
		servicio.crearTodo("Estudiar JUnit", FECHA_FUTURA);

		List<ToDo> pendientes = repositorio.obtenerSinCompletar();
		assertEquals(1, pendientes.size());
		assertEquals("Estudiar JUnit", pendientes.get(0).getNombre());
	}

	@Test
	void testCrearVariasTareasYVerificarContador() {
		servicio.crearTodo("Tarea A", FECHA_FUTURA);
		servicio.crearTodo("Tarea B", FECHA_FUTURA);
		servicio.crearTodo("Tarea C", FECHA_FUTURA);

		assertEquals(3, repositorio.obtenerSinCompletar().size());
	}


	@Test
	void testAgregarEmailValidoYVerificarEnRepositorio() {
		servicio.agregarDireccionCorreoAAgenda("usuario@correo.com");
		assertTrue(repositorio.obtenerEmails().contains("usuario@correo.com"));
	}


	@Test
	void testFlujoCompletoCrearYMarcarComoFinalizada() {
		servicio.crearTodo("Tarea a completar", FECHA_FUTURA);
		String id = String.valueOf(repositorio.obtenerSinCompletar().get(0).getId());

		servicio.marcarTareaFinalizada(id);

		assertTrue(repositorio.obtenerSinCompletar().isEmpty());
	}


	@Test
	void testConsultarSinCompletarDevuelveSoloLasPendientes() {
		servicio.crearTodo("Pendiente 1", FECHA_FUTURA);
		servicio.crearTodo("Pendiente 2", FECHA_FUTURA);

		String id = String.valueOf(repositorio.obtenerSinCompletar().get(0).getId());
		servicio.marcarTareaFinalizada(id);

		List<ToDo> resultado = servicio.consultarToDosSinCompletar("");
		assertEquals(1, resultado.size());
	}


	@Test
	void testConTareaVencidaEnAgendaSeDisparaAlerta() {
		repositorio.guardarToDo(new ToDo(999, "Tarea vencida", "", FECHA_PASADA, false));
		repositorio.guardarEmail("alerta@empresa.com");

		assertDoesNotThrow(() -> servicio.crearTodo("Nueva tarea", FECHA_FUTURA));
	}


	@Test
	void testNombreVacioNoAlteraElEstadoDelRepositorio() {
		try { servicio.crearTodo("", FECHA_FUTURA); } catch (IllegalArgumentException ignored) {}
		assertTrue(repositorio.obtenerSinCompletar().isEmpty());
	}

	@Test
	void testEmailMalFormadoNoSeAlmacena() {
		try { servicio.agregarDireccionCorreoAAgenda("noEsUnEmail"); } catch (IllegalArgumentException ignored) {}
		assertTrue(repositorio.obtenerEmails().isEmpty());
	}
}
