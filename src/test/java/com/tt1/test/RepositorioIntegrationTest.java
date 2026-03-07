package com.tt1.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import proyectoPunto4.DBStub;
import proyectoPunto4.Repositorio;
import proyectoPunto4.ToDo;

class RepositorioIntegrationTest {

	private Repositorio repositorio;

	private static final Date FECHA_PASADA = Date.valueOf("2020-01-01");
	private static final Date FECHA_FUTURA = Date.valueOf("2099-12-31");

	@BeforeEach
	void setUp() {
		repositorio = new Repositorio(new DBStub());
	}

	@Test
	void testGuardarYEncontrarTodo() {
		ToDo original = new ToDo(1, "Integración", "Desc", FECHA_FUTURA, false);
		repositorio.guardarToDo(original);

		ToDo recuperado = repositorio.encontrarToDo("1");

		assertNotNull(recuperado);
		assertEquals("Integración", recuperado.getNombre());
	}

	@Test
	void testMarcarCompletadoActualizaElEstado() {
		repositorio.guardarToDo(new ToDo(1, "Tarea", "", FECHA_FUTURA, false));
		repositorio.marcarCompletadoToDo("1");

		assertTrue(repositorio.encontrarToDo("1").isCompletado());
	}

	@Test
	void testGuardarYRecuperarEmail() {
		repositorio.guardarEmail("int@test.com");
		List<String> emails = repositorio.obtenerEmails();

		assertTrue(emails.contains("int@test.com"));
	}

	@Test
	void testObtenerVencidosConMezclaDeTareas() {
		repositorio.guardarToDo(new ToDo(1, "Vencida",    "", FECHA_PASADA, false));
		repositorio.guardarToDo(new ToDo(2, "Completada", "", FECHA_PASADA, true));
		repositorio.guardarToDo(new ToDo(3, "Pendiente",  "", FECHA_FUTURA, false));

		List<ToDo> vencidos = repositorio.obtenerTodosVencidos();

		assertEquals(1, vencidos.size());
		assertEquals("Vencida", vencidos.get(0).getNombre());
	}

	@Test
	void testObtenerSinCompletarExcluyeCompletadas() {
		repositorio.guardarToDo(new ToDo(1, "Pendiente",  "", FECHA_FUTURA, false));
		repositorio.guardarToDo(new ToDo(2, "Completada", "", FECHA_FUTURA, true));

		List<ToDo> sinCompletar = repositorio.obtenerSinCompletar();

		assertEquals(1, sinCompletar.size());
	}

	@Test
	void testFlujoCompletoGuardarMarcarYVerificar() {
		repositorio.guardarToDo(new ToDo(10, "Flujo completo", "", FECHA_FUTURA, false));
		assertFalse(repositorio.encontrarToDo("10").isCompletado());

		repositorio.marcarCompletadoToDo("10");
		assertTrue(repositorio.encontrarToDo("10").isCompletado());

		assertTrue(repositorio.obtenerSinCompletar().isEmpty());
	}
}
