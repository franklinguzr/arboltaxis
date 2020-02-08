package co.flota.taxis.modelo;

public class Turno {
	
	private String codigo, placaTaxi, fecha;
	
	public Turno(){
		
	}
	
	public Turno(String codigo, String placaTaxi, String fecha){
		this.codigo = codigo;
		this.placaTaxi = placaTaxi;
		this.fecha = fecha;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPlacaTaxi() {
		return placaTaxi;
	}

	public void setPlacaTaxi(String placaTaxi) {
		this.placaTaxi = placaTaxi;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
