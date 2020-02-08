package co.flota.taxis.modelo;


public class Taller {

	private String codigo, ciudad, direccion, horario;
	
	public Taller(){}

	public Taller(String codigo, String ciudad, String direccion, String horario) {
		this.codigo = codigo;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.horario = horario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
	
}
