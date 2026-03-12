package proyectoPunto4;

import java.util.List;
/**
 * Interfaz que representa a la base de datos CAMBIO DESDE STABLE
 */
public interface DB {
	/*
	 * Almacenar un ToDo
	 * @param t ToDo a almacenar
	 */
	public void createTodo(ToDo t);
	/*
	 * Obtener un ToDo
	 * @param id id del ToDo
	 */
	public ToDo readTodo(String id);
	/*
	 * Actualizar ToDo
	 * @param t ToDo a actualizar
	 */
	public void updateTodo(ToDo t);
	/*
	 * Borrar ToDo
	 * @param t ToDo a borrar
	 */
	public void deleteTodo(ToDo t);
	/*
	 * Guardar email
	 * @param email email a guardar
	 */
	public void saveEmail(String email);
	/*
	 * Obtener todos los emails
	 * @return lista de emails de la BD
	 */
	public List<String> getEmails();
	/*
	 * Obtener todos los ToDos
	 * @return lista de ToDos de la BD
	 */
	public List<ToDo> getAllTodos();
}
