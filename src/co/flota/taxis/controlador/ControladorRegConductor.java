package co.flota.taxis.controlador;

import co.flota.taxis.Main;
import co.flota.taxis.negocio.ConductorNegocio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ControladorRegConductor {
	
	private Main mainApp;
	private ConductorNegocio conductorNeg;
	
	public ControladorRegConductor(){		
		this.conductorNeg = new ConductorNegocio();		
	}
	
	public void setMainApp(Main main){
		this.mainApp = main;
	}
	
	@FXML
	private TextField campoNombre;
	
	@FXML
	private TextField campoApellido;
	
	@FXML
	private TextField campoId;
	
	@FXML
	private TextField campoCorreo;
	
	@FXML
	private ChoiceBox<String> campoGenero;
	
	@FXML
	private TextField campoCelular;
	
	@FXML
	private TextField campoContraseña;
	
	@FXML
	private void initialize(){
		campoGenero.getItems().addAll("M","F");
	}
	
	@FXML
	private void presionarAceptar(){
		
		if(validarCamposObligatorios()){
			
			String nombre = campoNombre.getText().trim();
			String apellido = campoApellido.getText().trim();
			String id = campoId.getText().trim();
			String contraseña = campoContraseña.getText().trim();
			String correo = campoCorreo.getText().trim();
			char genero = campoGenero.getSelectionModel().getSelectedItem().charAt(0);
			String celular = campoCelular.getText().trim();
						
			if(this.conductorNeg.registrarConductor(nombre, apellido, id, contraseña, correo, genero, celular)){
				
				Alert campos = new Alert(AlertType.CONFIRMATION);
				campos.setTitle("Registro Existoso");
				campos.setHeaderText("El conductor ha sido registrado correctamente");
				campos.showAndWait();
				this.mainApp.mostrarEscena();
                                this.mainApp.mostrarArbol(this.conductorNeg.getArbolConductor());
				
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
		String nombre = campoNombre.getText().trim();
		if("".equals(nombre) || nombre.length() == 0){
			mensaje = mensaje + "El nombre es obligatorio\n";
		}
		String apellido = campoApellido.getText().trim();
		if("".equals(apellido) || apellido.length() == 0){
			mensaje = mensaje + "El apellido es obligatorio\n";
		}
		String id = campoId.getText().trim();
		if("".equals(id) || id.length() == 0){
			mensaje = mensaje + "La identificaciï¿½n es obligatoria\n";
		}
		String contraseña = campoContraseña.getText().trim();
		if("".equals(contraseña) || contraseña.length() == 0){
			mensaje = mensaje + "La contraseï¿½a es obligatoria\n";
		}
		String genero = campoGenero.getSelectionModel().getSelectedItem();
		if("".equals(genero) || genero == null){
			mensaje = mensaje + "El gï¿½nero es obligatorio\n";
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
