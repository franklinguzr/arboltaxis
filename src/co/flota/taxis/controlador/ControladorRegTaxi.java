package co.flota.taxis.controlador;


import co.flota.taxis.Main;
import co.flota.taxis.modelo.Taxi;
import co.flota.taxis.negocio.TaxiNegocio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ControladorRegTaxi {
	
	private TaxiNegocio taxiNeg;
	private Main mainApp;
	
	public ControladorRegTaxi(){
		this.taxiNeg = new TaxiNegocio();
	}
	
	public void setMainApp(Main main){
		this.mainApp = main;
	}
	
	@FXML
	private TextField campoPlaca;
	
	@FXML
	private TextField campoModelo;
	
	@FXML
	private TextField campoMarca;
	
	@FXML
	private TextField campoCombustible;
	
	@FXML
	private void initialize(){		
	}
	
	@FXML
	private void presionarAceptar(){
		
		if(validarCamposObligatorios()){
			
			String placa = campoPlaca.getText().trim();
			String modelo = campoModelo.getText().trim();
			String marca = campoMarca.getText().trim();
			String combustible = campoCombustible.getText().trim();

			Taxi taxi = new Taxi(placa, marca, modelo, combustible);
			
			if(this.taxiNeg.registrarTaxi(taxi)){
				
				Alert campos = new Alert(AlertType.CONFIRMATION);
				campos.setTitle("Registro Existoso");
				campos.setHeaderText("El taxi ha sido registrado correctamente");
				campos.showAndWait();
				
				this.mainApp.mostrarEscena();
				
			this.mainApp.mostrarArbol(this.taxiNeg.getArbolTaxi());
			}else{
				
				Alert campos = new Alert(AlertType.ERROR);
				campos.setTitle("Error");
				campos.setHeaderText("Ha ocurrido un error en el registro");
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
		String placa = campoPlaca.getText().trim();
		if("".equals(placa) || placa.length() == 0){
			mensaje = mensaje + "La placa es obligatorio\n";
		}
		String modelo = campoModelo.getText().trim();
		if("".equals(modelo) || modelo.length() == 0){
			mensaje = mensaje + "El modelo es obligatoria\n";
		}
		String marca = campoMarca.getText().trim();
		if("".equals(marca) || marca.length() == 0){
			mensaje = mensaje + "La marca es obligatoria\n";
		}
		String combustible = campoCombustible.getText().trim();
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

}
