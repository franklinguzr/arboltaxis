package co.flota.taxis.modelo;

import java.time.LocalDate;

public class Propietario extends Persona{

	private String direccion;
	private boolean primeraCompra;
	
	public Propietario(){}

	public Propietario(String nombre, String apellido, String correo, String id, String direccion, String celular,
			LocalDate fechaIngreso, char genero, boolean primeraCompra) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.id = id;
		this.direccion = direccion;
		this.celular = celular;
		this.fechaIngreso = fechaIngreso;
		this.genero = genero;
		this.primeraCompra = primeraCompra;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public Boolean isPrimeraCompra() {
		return primeraCompra;
	}

	public void setPrimeraCompra(boolean primeraCompra) {
		this.primeraCompra = primeraCompra;
	}
	
	
	
}
