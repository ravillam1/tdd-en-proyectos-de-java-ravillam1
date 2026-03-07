package com.tt1.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import proyectoPunto4.ToDo;


class ToDoTest {

	private ToDo todo;
	private Date fecha;

	@BeforeEach
	void setUp() {
		fecha = Date.valueOf("2025-12-31");
		todo = new ToDo(1, "Estudiar TDD", "Repasar la práctica 3", fecha, false);
	}


	@Test
	void testConstructorAsignaIdCorrectamente() {
		assertEquals(1, todo.getId());
	}

	@Test
	void testConstructorAsignaNombreCorrectamente() {
		assertEquals("Estudiar TDD", todo.getNombre());
	}

	@Test
	void testConstructorAsignaDescripcionCorrectamente() {
		assertEquals("Repasar la práctica 3", todo.getDescripcion());
	}

	@Test
	void testConstructorAsignaFechaCorrectamente() {
		assertEquals(fecha, todo.getFecha_limite());
	}

	@Test
	void testConstructorAsignaCompletadoCorrectamente() {
		assertFalse(todo.isCompletado());
	}


	@Test
	void testConstructorVacioNoLanzaExcepcion() {
		assertDoesNotThrow(() -> new ToDo());
	}

	@Test
	void testConstructorVacioDejaAtributosEnValoresPorDefecto() {
		ToDo vacio = new ToDo();
		assertEquals(0, vacio.getId());
		assertNull(vacio.getNombre());
		assertNull(vacio.getDescripcion());
		assertNull(vacio.getFecha_limite());
		assertFalse(vacio.isCompletado());
	}

	// --- Setters ---

	@Test
	void testSetIdActualizaId() {
		todo.setId(99);
		assertEquals(99, todo.getId());
	}

	@Test
	void testSetNombreActualizaNombre() {
		todo.setNombre("Nuevo nombre");
		assertEquals("Nuevo nombre", todo.getNombre());
	}

	@Test
	void testSetDescripcionActualizaDescripcion() {
		todo.setDescripcion("Nueva descripcion");
		assertEquals("Nueva descripcion", todo.getDescripcion());
	}

	@Test
	void testSetFechaActualizaFecha() {
		Date nueva = Date.valueOf("2026-06-01");
		todo.setFecha_limite(nueva);
		assertEquals(nueva, todo.getFecha_limite());
	}

	@Test
	void testSetCompletadoActualizaCompletado() {
		todo.setCompletado(true);
		assertTrue(todo.isCompletado());
	}
}
