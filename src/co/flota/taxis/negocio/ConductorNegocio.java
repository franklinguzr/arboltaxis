package co.flota.taxis.negocio;

import java.time.LocalDate;

import co.flota.taxis.dao.ConductorDAO;
import co.flota.taxis.dao.impl.FileConductorDAO;
import co.flota.taxis.modelo.Conductor;
import co.flota.taxis.util.BtreeT;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConductorNegocio {
	
	private ConductorDAO conductorDAO;
	
	public ConductorNegocio(){
		this.conductorDAO = new FileConductorDAO();
	}
	
	public boolean registrarConductor(String nombre, String apellido, String id, String contrase?a, String correo,
			char genero, String celular){
		
		if(validarCamposObligatorios(nombre, apellido, id, contrase?a,genero)){
		
			if(this.conductorDAO.conductorExistente(id)){
				
				Alert campos = new Alert(AlertType.ERROR);
				campos.setTitle("Error");
				campos.setHeaderText("El conductor ingresado ya se encuentra registrado");
				campos.setContentText("Por favor verifique todos los campos");
				campos.showAndWait();
				
				return false;
				
			}
			
			Conductor conductor = new Conductor(nombre, apellido, correo, id, contrase?a, celular, LocalDate.now(), genero, true);
			
			return this.conductorDAO.guardarConductor(conductor);
			
		}
		
		return false;
	}
	
	private boolean validarCamposObligatorios(String nombre, String apellido, String id, String contrase?a, char genero){
		
		String mensaje = "";
		if("".equals(nombre) || nombre.length() == 0){
			mensaje = mensaje + "El nombre es obligatorio\n";
		}
		if("".equals(apellido) || apellido.length() == 0){
			mensaje = mensaje + "El apellido es obligatorio\n";
		}
		if("".equals(id) || id.length() == 0){
			mensaje = mensaje + "La identificaci???n es obligatoria\n";
		}
		if("".equals(contrase?a) || contrase?a.length() == 0){
			mensaje = mensaje + "La contrase???a es obligatoria\n";
		}
		if("".equals(genero)){
			mensaje = mensaje + "El g???nero es obligatorio\n";
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

	
	public BtreeT getArbolConductor() {
		return this.conductorDAO.getArbol();
	}

}
