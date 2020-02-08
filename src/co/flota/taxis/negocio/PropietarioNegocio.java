package co.flota.taxis.negocio;

import co.flota.taxis.dao.PropietarioDAO;
import co.flota.taxis.dao.impl.FilePropietarioDAO;
import co.flota.taxis.modelo.Propietario;
import co.flota.taxis.util.BtreeT;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PropietarioNegocio {
	
	private PropietarioDAO propietarioDAO;
	
	public PropietarioNegocio(){
		this.propietarioDAO = new FilePropietarioDAO();
	}

	public boolean registrarPropietario(Propietario propietario) {
		if(validarCamposObligatorios(propietario)){
			
			if(this.propietarioDAO.propietarioExistente(propietario.getId())){
				
				Alert campos = new Alert(AlertType.ERROR);
				campos.setTitle("Error");
				campos.setHeaderText("El propietario ingresado ya se encuentra registrado");
				campos.setContentText("Por favor verifique todos los campos");
				campos.showAndWait();
				
				return false;
				
			}
			
			return this.propietarioDAO.guardarPropietario(propietario);
			
		}
		
		return false;
	}
	
	private boolean validarCamposObligatorios(Propietario propietario){
		
		String mensaje = "";
		String nombre = propietario.getNombre();
		if("".equals(nombre) || nombre.length() == 0){
			mensaje = mensaje + "El nombre es obligatorio\n";
		}
		String apellido = propietario.getApellido();
		if("".equals(apellido) || apellido.length() == 0){
			mensaje = mensaje + "El apellido es obligatorio\n";
		}
		String id = propietario.getId();
		if("".equals(id) || id.length() == 0){
			mensaje = mensaje + "La identificaci�n es obligatoria\n";
		}
		char genero = propietario.getGenero();
		if("".equals(genero)){
			mensaje = mensaje + "El g�nero es obligatorio";
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

	public BtreeT getArbolPropietario() {
		return this.propietarioDAO.getArbol();
	}

}
