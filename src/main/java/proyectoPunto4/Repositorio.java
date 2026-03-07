package proyectoPunto4;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Repositorio {
	
	private DB db;

	public Repositorio(DB db) {
		this.db = db;
	}

	public ToDo encontrarToDo(String id) {
		return db.readTodo(id);
	}

	public void marcarCompletadoToDo(String id) {
		ToDo todo = db.readTodo(id);
		if (todo == null) {
			throw new IllegalArgumentException("No existe ningún ToDo con id: " + id);
		}
		todo.setCompletado(true);
		db.updateTodo(todo);
	}

	public void guardarToDo(ToDo t) {
		db.createTodo(t);
	}

	public void guardarEmail(String email) {
		db.saveEmail(email);
	}

	public List<ToDo> obtenerTodosVencidos() {
		Date hoy = new Date(System.currentTimeMillis());
		
		List<ToDo> vencidos = new ArrayList<>();
		List<ToDo> todos = db.getAllTodos();
		for (int i = 0; i < todos.size(); i++) {
	        ToDo t = todos.get(i);
	        if (!t.isCompletado() 
	                && t.getFecha_limite() != null 
	                && t.getFecha_limite().before(hoy)) {
	            
	            vencidos.add(t);
	        }
	    }
		return vencidos;
	}

	public List<ToDo> obtenerSinCompletar() {
	    List<ToDo> todos = db.getAllTodos();
	    List<ToDo> sinCompletar = new ArrayList<>();

	    for (int i = 0; i < todos.size(); i++) {
	        ToDo t = todos.get(i);
	        if (!t.isCompletado()) {
	            sinCompletar.add(t);
	        }
	    }

	    return sinCompletar;
	}

	public List<String> obtenerEmails() {
		return db.getEmails();
	}
}
