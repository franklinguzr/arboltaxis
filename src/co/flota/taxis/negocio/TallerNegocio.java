package co.flota.taxis.negocio;


import co.flota.taxis.dao.TallerDAO;
import co.flota.taxis.dao.impl.FileTallerDAO;
import co.flota.taxis.modelo.Taller;
import co.flota.taxis.util.BtreeT;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TallerNegocio {
	
	private TallerDAO tallerDAO;
	
	public TallerNegocio(){
		this.tallerDAO = new FileTallerDAO();
	}
	
	public boolean registrarTaller(Taller taller){
		
		if(validarCamposObligatorios(taller)){
			
			if(this.tallerDAO.tallerExistente(taller.getCodigo())){
				
				Alert campos = new Alert(AlertType.ERROR);
				campos.setTitle("Error");
				campos.setHeaderText("El taller ingresado ya se encuentra registrado");
				campos.setContentText("Por favor verifique todos los campos");
				campos.showAndWait();
				
				return false;
				
			}
			
			return this.tallerDAO.guardarTaller(taller);
			
		}
		
		return false;
	}
	
	private boolean validarCamposObligatorios(Taller taller){
		
		String mensaje = "";
		String codigo = taller.getCodigo();
		if("".equals(codigo) || codigo.length() == 0){
			mensaje = mensaje + "El c�digo es obligatorio\n";
		}
		String direccion = taller.getDireccion();
		if("".equals(direccion) || direccion.length() == 0){
			mensaje = mensaje + "La direcci�n es obligatoria\n";
		}
		String ciudad = taller.getCiudad();
		if("".equals(ciudad) || ciudad.length() == 0){
			mensaje = mensaje + "La ciudad es obligatoria\n";
		}
		String horario = taller.getHorario();
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

	public BtreeT getArbolTaller() {
		return this.tallerDAO.getArbol();
	}

}
