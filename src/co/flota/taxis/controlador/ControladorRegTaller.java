package co.flota.taxis.controlador;


import co.flota.taxis.Main;
import co.flota.taxis.modelo.Taller;
import co.flota.taxis.negocio.TallerNegocio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ControladorRegTaller {
	
	private TallerNegocio tallerNeg;
	private Main mainApp;
	
	public ControladorRegTaller(){
		this.tallerNeg = new TallerNegocio();
	}
	
	public void setMainApp(Main main){
		this.mainApp = main;
	}
	
	@FXML
	private TextField campoCodigo;
	
	@FXML
	private TextField campoDireccion;
	
	@FXML
	private TextField campoCiudad;
	
	@FXML
	private TextField campoHorario;
	
	@FXML
	private void initialize(){		
	}
	
	@FXML
	private void presionarAceptar(){
		
		if(validarCamposObligatorios()){
			
			String codigo = campoCodigo.getText().trim();
			String direccion = campoDireccion.getText().trim();
			String ciudad = campoCiudad.getText().trim();
			String horario = campoHorario.getText().trim();

			Taller taller = new Taller(codigo, ciudad, direccion, horario);
			
			if(this.tallerNeg.registrarTaller(taller)){
				
				Alert campos = new Alert(AlertType.CONFIRMATION);
				campos.setTitle("Registro Existoso");
				campos.setHeaderText("El taller ha sido registrado correctamente");
				campos.showAndWait();
				
				this.mainApp.mostrarEscena();
				
			this.mainApp.mostrarArbol(this.tallerNeg.getArbolTaller());
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
		String codigo = campoCodigo.getText().trim();
		if("".equals(codigo) || codigo.length() == 0){
			mensaje = mensaje + "El c�digo es obligatorio\n";
		}
		String direccion = campoDireccion.getText().trim();
		if("".equals(direccion) || direccion.length() == 0){
			mensaje = mensaje + "La direcci�n es obligatoria\n";
		}
		String ciudad = campoCiudad.getText().trim();
		if("".equals(ciudad) || ciudad.length() == 0){
			mensaje = mensaje + "La ciudad es obligatoria\n";
		}
		String horario = campoHorario.getText().trim();
		if("".equals(horario) || horario == null){
			mensaje = mensaje + "El horario es obligatorio";
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
