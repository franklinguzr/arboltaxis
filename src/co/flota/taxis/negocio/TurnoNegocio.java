package co.flota.taxis.negocio;

import co.flota.taxis.dao.TurnoDAO;
import co.flota.taxis.dao.impl.FileTurnoDAO;
import co.flota.taxis.modelo.Turno;
import co.flota.taxis.util.BtreeT;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TurnoNegocio {
	
	private TurnoDAO turnoDAO;
	
	public TurnoNegocio(){
		this.turnoDAO = new FileTurnoDAO();
	}
	
	public boolean registrarTurno(Turno turno){
if(validarCamposObligatorios(turno)){
			
			if(this.turnoDAO.turnoExistente(turno.getCodigo())){
				
				Alert campos = new Alert(AlertType.ERROR);
				campos.setTitle("Error");
				campos.setHeaderText("El turno a este taxi ya se encuentra registrado");
				campos.setContentText("Por favor verifique todos los campos");
				campos.showAndWait();
				
				return false;
				
			}
			
			return this.turnoDAO.guardarTurno(turno);
			
		}
		
		return false;
	}
	
private boolean validarCamposObligatorios(Turno turno){
		
		String mensaje = "";
		String codigo = turno.getCodigo();
		if("".equals(codigo) || codigo.length() == 0){
			mensaje = mensaje + "El c�digo es obligatorio\n";
		}
		String placaTaxi = turno.getPlacaTaxi();
		if("".equals(placaTaxi) || placaTaxi.length() == 0){
			mensaje = mensaje + "La placa del taxi asignar es obligatoria\n";
		}
		String fecha = turno.getFecha();
		if("".equals(fecha) || fecha.length() == 0){
			mensaje = mensaje + "La fecha es obligatoria\n";
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

	public BtreeT getArbolTurno() {
		return this.turnoDAO.getArbol();
	}

}
