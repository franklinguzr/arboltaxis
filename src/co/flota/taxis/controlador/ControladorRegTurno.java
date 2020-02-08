package co.flota.taxis.controlador;

import javafx.scene.control.TextField;
import co.flota.taxis.Main;
import co.flota.taxis.modelo.Turno;
import co.flota.taxis.negocio.TurnoNegocio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControladorRegTurno {
	
	private TurnoNegocio turnoNeg;
	private Main mainApp;
	
	public ControladorRegTurno(){
		this.turnoNeg = new TurnoNegocio();
	}
	
	public void setMainApp(Main main){
		this.mainApp = main;
	}
	
	@FXML
	private TextField campoCodigo;
	
	@FXML
	private TextField campoPlacaTaxi;
	
	@FXML
	private TextField campoFecha;
	
	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void presionarAceptar(){
		
		if(validarCamposObligatorios()){
			
			String codigo = campoCodigo.getText().trim();
			String placaTaxi = campoPlacaTaxi.getText().trim();
			String fecha = campoFecha.getText().trim();
			
			Turno turno = new Turno(codigo, placaTaxi, fecha);
			
			if(this.turnoNeg.registrarTurno(turno)){
				
				Alert campos = new Alert(AlertType.CONFIRMATION);
				campos.setTitle("Turno asignado exitosamente");
				campos.setHeaderText("El taxi con esta placa ya se encuentra en turno");
				campos.showAndWait();
				
				this.mainApp.mostrarEscena();
                                
			this.mainApp.mostrarArbol(this.turnoNeg.getArbolTurno());
			}else{
				
				Alert campos = new Alert(AlertType.ERROR);
				campos.setTitle("Error");
				campos.setHeaderText("Ha ocurrido un error al asignar el turno");
				campos.setContentText("Por favor verifique todos los campos");
				campos.showAndWait();
			}
			
		}
	}
	
	@FXML
	private void presionarCancelar(){
		this.mainApp.mostrarEscena();
	}
	
	private boolean validarCamposObligatorios(){
		
		String mensaje = "";
		String codigo = campoCodigo.getText().trim();
		if("".equals(codigo) || codigo.length() == 0){
			mensaje = mensaje + "El c�digo es obligatorio\n";
		}
		String placaTaxi = campoPlacaTaxi.getText().trim();
		if("".equals(placaTaxi) || placaTaxi.length() == 0){
			mensaje = mensaje + "La placa del taxi es obligatoria\n";
		}
		String fecha = campoFecha.getText().trim();
		if("".equals(fecha) || fecha.length() == 0){
			mensaje = mensaje + "La fecha del d�a del turno es obligatoria\n";
		}
		
		if(mensaje.length() == 0 || "".equals(mensaje)){
			return true;
		}
		
		Alert campos = new Alert(AlertType.INFORMATION);
		campos.setTitle("Faltan campos");
		campos.setHeaderText(mensaje);
		campos.setContentText("Por favor ingrese todos los campos con *");
		campos.showAndWait();
		
		return false;
	}

}
