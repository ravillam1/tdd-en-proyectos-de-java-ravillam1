package proyectoPunto4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBStub implements DB {

	private Map<String, ToDo> database = new HashMap<>();
	private List<String> emails = new ArrayList<>();

	@Override
	public void createTodo(ToDo t) {
		database.put(String.valueOf(t.getId()), t);
	}

	@Override
	public ToDo readTodo(String id) {
		return database.get(id);
	}

	@Override
	public void updateTodo(ToDo t) {
		database.put(String.valueOf(t.getId()), t);
	}

	@Override
	public void deleteTodo(ToDo t) {
		database.remove(String.valueOf(t.getId()));
	}

	@Override
	public void saveEmail(String email) {
		emails.add(email);
	}

	@Override
	public List<String> getEmails() {
		return new ArrayList<>(emails);
	}

	@Override
	public List<ToDo> getAllTodos() {
		return new ArrayList<>(database.values());
	}
}
