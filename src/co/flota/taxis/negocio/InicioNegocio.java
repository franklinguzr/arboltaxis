package co.flota.taxis.negocio;

import co.flota.taxis.dao.impl.FilePropietarioDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InicioNegocio {
	
	public void inicializarApp() {
		
		Alert ini = new Alert(AlertType.INFORMATION);
		ini.setTitle("Indexando");
		ini.setHeaderText("La aplicación se está indexando...");
		ini.setContentText("Por favor espere");
		ini.show();
		
	//	new FileAlmacenDAO();
		//new FileCajeroDAO();
		new FilePropietarioDAO();
		//new FileColeccionDAO();
		//new FileProductoDAO();
		//new FileTipoProductoDAO();
		//new FileVentaDAO();
		
		ini.close();
		
	}

}
