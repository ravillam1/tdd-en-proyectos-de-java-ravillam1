package proyectoPunto4;

import java.io.Serializable;
import java.sql.Date;

public class ToDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String descripcion;
	private Date fecha_limite;
	private boolean completado;
	
	public ToDo() {}
	public ToDo(int id, String nombre, String descripcion, Date fecha_limite, boolean completado) {
		this.id=id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha_limite = fecha_limite;
		this.completado = completado;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha_limite() {
		return fecha_limite;
	}
	public void setFecha_limite(Date fecha_limite) {
		this.fecha_limite = fecha_limite;
	}
	public boolean isCompletado() {
		return completado;
	}
	public void setCompletado(boolean completado) {
		this.completado = completado;
	}
}
