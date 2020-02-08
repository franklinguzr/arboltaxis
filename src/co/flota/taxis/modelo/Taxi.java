package co.flota.taxis.modelo;

import java.util.ArrayList;
import java.util.List;

public class Taxi {

	private String placa, modelo, marca, combustible;
	private List<Propietario> propietarios;
	
	public Taxi(){}

	public Taxi(String placa, String modelo, String marca, String combustible) {
		this.placa = placa;
		this.modelo = modelo;
		this.marca = marca;
		this.combustible = combustible;
		this.propietarios = new ArrayList<Propietario>();
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	public List<Propietario> getPropietarios() {
		return propietarios;
	}

	public void setCajeros(List<Propietario> propietarios) {
		this.propietarios = propietarios;
	}
	
}
