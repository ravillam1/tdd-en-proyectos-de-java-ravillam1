package com.tt1.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import proyectoPunto4.DBStub;
import proyectoPunto4.ToDo;

class DBStubTest {

	private DBStub db;
	private ToDo todo1;
	private ToDo todo2;

	@BeforeEach
	void setUp() {
		db = new DBStub();
		todo1 = new ToDo(1, "Tarea 1", "Descripción 1", Date.valueOf("2025-01-01"), false);
		todo2 = new ToDo(2, "Tarea 2", "Descripción 2", Date.valueOf("2025-06-01"), true);
	}


	@Test
	void testCreateYReadDevuelvenElMismoTodo() {
		db.createTodo(todo1);
		ToDo recuperado = db.readTodo("1");
		assertEquals(todo1.getNombre(), recuperado.getNombre());
	}

	@Test
	void testReadDeIdInexistenteDevuelveNull() {
		assertNull(db.readTodo("999"));
	}

	@Test
	void testSeAlmacenanVariosTodosSinConflicto() {
		db.createTodo(todo1);
		db.createTodo(todo2);
		assertEquals("Tarea 1", db.readTodo("1").getNombre());
		assertEquals("Tarea 2", db.readTodo("2").getNombre());
	}


	@Test
	void testUpdateModificaElTodoExistente() {
		db.createTodo(todo1);
		todo1.setNombre("Tarea modificada");
		db.updateTodo(todo1);
		assertEquals("Tarea modificada", db.readTodo("1").getNombre());
	}

	@Test
	void testUpdateMarcaCompletadoCorrectamente() {
		db.createTodo(todo1);
		todo1.setCompletado(true);
		db.updateTodo(todo1);
		assertTrue(db.readTodo("1").isCompletado());
	}


	@Test
	void testDeleteEliminaElTodo() {
		db.createTodo(todo1);
		db.deleteTodo(todo1);
		assertNull(db.readTodo("1"));
	}

	@Test
	void testDeleteNoAfectaAOtrosTodos() {
		db.createTodo(todo1);
		db.createTodo(todo2);
		db.deleteTodo(todo1);
		assertNotNull(db.readTodo("2"));
	}


	@Test
	void testSaveEmailAlmacenaEmail() {
		db.saveEmail("test@email.com");
		List<String> emails = db.getEmails();
		assertTrue(emails.contains("test@email.com"));
	}

	@Test
	void testGetEmailsDevuelveTodosLosEmails() {
		db.saveEmail("a@a.com");
		db.saveEmail("b@b.com");
		assertEquals(2, db.getEmails().size());
	}

	@Test
	void testGetEmailsDevuelveCopiaIndependiente() {
		db.saveEmail("a@a.com");
		List<String> copia = db.getEmails();
		copia.add("externo@x.com");
		assertEquals(1, db.getEmails().size()); // el original no se ve afectado
	}


	@Test
	void testGetAllTodosDevuelveTodosLosElementos() {
		db.createTodo(todo1);
		db.createTodo(todo2);
		assertEquals(2, db.getAllTodos().size());
	}

	@Test
	void testGetAllTodosEnBDVaciaDevuelveListaVacia() {
		assertTrue(db.getAllTodos().isEmpty());
	}
}
