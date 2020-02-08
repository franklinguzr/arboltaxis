package co.flota.taxis.controlador;

import java.util.List;

import co.flota.taxis.Main;
import co.flota.taxis.modelo.Conductor;
import co.flota.taxis.modelo.Propietario;
import co.flota.taxis.modelo.Taller;
import co.flota.taxis.modelo.Taxi;
import co.flota.taxis.negocio.ReportesNegocio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControladorVerReportes {
	
	private ReportesNegocio reporteNeg;
	
	private Main mainApp;
	
	public ControladorVerReportes(){
		this.reporteNeg = new ReportesNegocio();
	}
	
	public void setMainApp(Main main){
		this.mainApp = main;
	}
	
	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void presionarTaxis(){
		
		List<Taxi> taxis = this.reporteNeg.reporteTaxis();
		
		String reporte = "Taxis: \n";
		reporte = reporte + "Placa del taxi\n";
		
		for(Taxi ta:taxis){
			
			reporte = reporte + " "+ ta.getPlaca() + "\n";
		}
		
		Alert report = new Alert(AlertType.INFORMATION);
		report.setTitle("Reporte de taxis");
		report.setHeaderText(reporte);
		report.showAndWait();
		
		this.mainApp.mostrarArbol(this.reporteNeg.getArbolTaxis());
	}
	
	@FXML
	private void presionarPropietarios(){
		
		List<Propietario> propietarios = this.reporteNeg.reportePropietarios();
		
		String reporte = "Propietarios: \n";
		reporte = reporte + "Id de los propietarios\n";
		
		for(Propietario p:propietarios){
			
			reporte = reporte + " "+ p.getId() + "\n";
		}
		
		Alert report = new Alert(AlertType.INFORMATION);
		report.setTitle("Reporte de propietarios");
		report.setHeaderText(reporte);
		report.showAndWait();
		
		this.mainApp.mostrarArbol(this.reporteNeg.getArbolPropietarios());
	}
	
	@FXML
	private void presionarConductores(){
		
		List<Conductor> conductores = this.reporteNeg.reporteConductores();
		
		String reporte = "Conductores: \n";
		reporte = reporte + "Id de los conductores\n";
		
		for(Conductor c:conductores){
			
			reporte = reporte + " " + c.getId() + "\n";
		}
		
		Alert report = new Alert(AlertType.INFORMATION);
		report.setTitle("Reporte de conductores");
		report.setHeaderText(reporte);
		report.showAndWait();
		
		this.mainApp.mostrarArbol(this.reporteNeg.getArbolConductores());
	}
	
	@FXML
	private void presionarTalleres(){
		
		List<Taller> talleres = this.reporteNeg.reporteTalleres();
		
		String reporte = "Talleres: \n";
		reporte = reporte + "Codigo de los talleres\n";
		
		for(Taller ta:talleres){
			
			reporte = reporte + " " + ta.getCodigo() + "\n";
		}
		
		Alert report = new Alert(AlertType.INFORMATION);
		report.setTitle("Reporte de talleres");
		report.setHeaderText(reporte);
		report.showAndWait();
		
		this.mainApp.mostrarArbol(this.reporteNeg.getArbolTaller());
	}
	
	@FXML
	private void presionarCancelar(){
		this.mainApp.mostrarEscena();
	}

}
