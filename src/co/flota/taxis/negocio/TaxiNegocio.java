package co.flota.taxis.negocio;

import co.flota.taxis.dao.TaxiDAO;
import co.flota.taxis.dao.impl.FileTaxiDAO;
import co.flota.taxis.modelo.Taxi;
import co.flota.taxis.util.BtreeT;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TaxiNegocio {
	
	private TaxiDAO taxiDAO;
	
	public TaxiNegocio(){
		this.taxiDAO = new FileTaxiDAO();
	}
	
	public boolean registrarTaxi(Taxi taxi){
		
		if(validarCamposObligatorios(taxi)){
			
			if(this.taxiDAO.taxiExistente(taxi.getPlaca())){
				
				Alert campos = new Alert(AlertType.ERROR);
				campos.setTitle("Error");
				campos.setHeaderText("El taxi ingresado ya se encuentra registrado");
				campos.setContentText("Por favor verifique todos los campos");
				campos.showAndWait();
				
				return false;
				
			}
			
			return this.taxiDAO.guardarTaxi(taxi);
			
		}
		
		return false;
	}
	
	private boolean validarCamposObligatorios(Taxi taxi){
		
		String mensaje = "";
		String placa = taxi.getPlaca();
		if("".equals(placa) || placa.length() == 0){
			mensaje = mensaje + "La placa es obligatorio\n";
		}
		String marca = taxi.getMarca();
		if("".equals(marca) || marca.length() == 0){
			mensaje = mensaje + "La marca es obligatoria\n";
		}
		String modelo = taxi.getModelo();
		if("".equals(modelo) || modelo.length() == 0){
			mensaje = mensaje + "El modelo es obligatoria\n";
		}
		String combustible = taxi.getCombustible();
		if("".equals(combustible) || combustible == null){
			mensaje = mensaje + "El combustible es obligatorio";
		}
		
		if(mensaje.length() == 0 || "".equals(mensaje)){
			return true;
		}
		
		Alert campos = new Alert(AlertType.INFORMATION);
		campos.setTitle("Faltan Campos");
		campos.setHeaderText(mensaje);
		campos.setContentText("Por favor ingrese todos los campos con *");
		campos.showAndWait();
		
		return false;
	}

	public BtreeT getArbolTaxi() {
		return this.taxiDAO.getArbol();
	}

}
