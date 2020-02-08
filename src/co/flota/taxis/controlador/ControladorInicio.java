package co.flota.taxis.controlador;

import co.flota.taxis.Main;
import co.flota.taxis.negocio.InicioNegocio;
import javafx.fxml.FXML;

public class ControladorInicio {
	
	private InicioNegocio iniNeg;
	
	private Main mainApp;
	
	public ControladorInicio(){
		this.iniNeg = new InicioNegocio();
	}
	
	public void setMainApp(Main main){
		this.mainApp = main;
	}

	@FXML
    private void initialize(){
		this.iniNeg.inicializarApp();
    }
	
	@FXML
    private void presionarTaxi(){
		this.mainApp.mostrarRegTaxi();
    }
	
	@FXML
    private void presionarTaller(){
		this.mainApp.mostrarRegTaller();
    }
	
	@FXML
    private void presionarPropietario(){
		this.mainApp.mostrarRegPropietario();
    }
	
	@FXML
    private void presionarTurno(){
		this.mainApp.mostrarRegTurno();
    }
	
	@FXML
    private void presionarConductor(){
		this.mainApp.mostrarRegConductor();
    }
	
	@FXML
    private void presionarReportes(){
		this.mainApp.mostrarTiposReportes();
    }
   
	
}
