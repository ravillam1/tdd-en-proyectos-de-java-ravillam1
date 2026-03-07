package proyectoPunto4;

import java.sql.Date;
import java.util.List;

public class Servicio {
	private Repositorio repositorio;
	private Mailer mailer;
	private static int idCounter = 1;
	
	public Servicio(Repositorio repositorio, Mailer mailer) {
		this.repositorio = repositorio;
		this.mailer = mailer;
	}

	public void crearTodo(String nombre, Date fecha_limite) {
		boolean b=comprobarToDosSinCompletar();
		ToDo todo = new ToDo(idCounter, nombre, "", fecha_limite, false);
		idCounter++;
		repositorio.guardarToDo(todo);
	}

	public void agregarDireccionCorreoAAgenda(String direccion) {
		comprobarToDosSinCompletar();
		repositorio.guardarEmail(direccion);
	}

	public void marcarTareaFinalizada(String id) {
		comprobarToDosSinCompletar();
		repositorio.marcarCompletadoToDo(id);
	}

	public List<ToDo> consultarToDosSinCompletar(String direccion) {
		comprobarToDosSinCompletar();
		return repositorio.obtenerSinCompletar();
	}

	private boolean comprobarToDosSinCompletar() {
	    List<ToDo> vencidos = repositorio.obtenerTodosVencidos();
	    
	    if (!vencidos.isEmpty()) {
	        List<String> emails = repositorio.obtenerEmails();
	        String mensaje = "Las siguientes tareas han superado su fecha límite: ";
	        
	        for (int i = 0; i < vencidos.size(); i++) {
	            ToDo t = vencidos.get(i);
	            mensaje = mensaje + "[" + t.getNombre() + " - " + t.getFecha_limite() + "] ";
	        }
	        
	        mensaje = mensaje.trim();
	        
	        for (int j = 0; j < emails.size(); j++) {
	            String email = emails.get(j);
	            mailer.enviarCorreo(email, mensaje);
	        }
	        
	        return true;
	    }
	    
	    return false;
	}
}
