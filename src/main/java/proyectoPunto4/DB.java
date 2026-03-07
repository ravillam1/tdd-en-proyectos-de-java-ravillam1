package proyectoPunto4;

import java.util.List;

public interface DB {
	public void createTodo(ToDo t);
	public ToDo readTodo(String id);
	public void updateTodo(ToDo t);
	public void deleteTodo(ToDo t);
	public void saveEmail(String email);
	public List<String> getEmails();
	public List<ToDo> getAllTodos();
}
