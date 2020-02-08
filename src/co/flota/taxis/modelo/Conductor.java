package co.flota.taxis.modelo;

import java.time.LocalDate;

public class Conductor extends Persona{

	private String contrase�a;
	private boolean estado;
	
	public Conductor(){}

	public Conductor(String nombre, String apellido, String correo, String id, String contrase�a, String celular,
			LocalDate fechaIngreso, char genero, boolean estado) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.id = id;
		this.contrase�a = contrase�a;
		this.celular = celular;
		this.fechaIngreso = fechaIngreso;
		this.genero = genero;
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	
}
