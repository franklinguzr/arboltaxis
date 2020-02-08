package co.flota.taxis.modelo;

import java.time.LocalDate;

public class Conductor extends Persona{

	private String contraseña;
	private boolean estado;
	
	public Conductor(){}

	public Conductor(String nombre, String apellido, String correo, String id, String contraseña, String celular,
			LocalDate fechaIngreso, char genero, boolean estado) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.id = id;
		this.contraseña = contraseña;
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

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
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
