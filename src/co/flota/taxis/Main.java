package co.flota.taxis;



import java.io.IOException;

import co.flota.taxis.controlador.ControladorInicio;
import co.flota.taxis.controlador.ControladorRegConductor;
import co.flota.taxis.controlador.ControladorRegPropietario;
import co.flota.taxis.controlador.ControladorRegTaller;
import co.flota.taxis.controlador.ControladorRegTaxi;
import co.flota.taxis.controlador.ControladorRegTurno;
import co.flota.taxis.controlador.ControladorVerReportes;
import co.flota.taxis.util.BtreeT;
import co.flota.taxis.util.Node;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	
	private Stage escenarioPrincipal;
	private BorderPane raiz;
	
	public void iniPlanoRaiz() {
	    try {
	        FXMLLoader cargador = new FXMLLoader();
	        cargador.setLocation(Main.class.getResource("vista/Raiz.fxml"));
	        raiz = (BorderPane) cargador.load();
	        
	        Scene scene = new Scene(raiz);
	        escenarioPrincipal.setScene(scene);
	        escenarioPrincipal.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void mostrarEscena() {
	    try {
	        FXMLLoader cargador = new FXMLLoader();
	        cargador.setLocation(Main.class.getResource("vista/Inicio.fxml"));
	        AnchorPane inicio = (AnchorPane) cargador.load();
	        
	        raiz.setCenter(inicio);
            
            ControladorInicio controlador = cargador.getController();
            controlador.setMainApp(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void mostrarRegPropietario() {
	    try {
	        FXMLLoader cargador = new FXMLLoader();
	        cargador.setLocation(Main.class.getResource("vista/RegPropietario.fxml"));
	        AnchorPane propietario = (AnchorPane) cargador.load();
	        
	        raiz.setCenter(propietario);
            
            ControladorRegPropietario controlador = cargador.getController();
            controlador.setMainApp(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void mostrarRegTurno() {
	    try {
	        FXMLLoader cargador = new FXMLLoader();
	        cargador.setLocation(Main.class.getResource("vista/RegTurno.fxml"));
	        AnchorPane turno = (AnchorPane) cargador.load();
	        
	        raiz.setCenter(turno);
            
            ControladorRegTurno controlador = cargador.getController();
            controlador.setMainApp(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void mostrarRegTaller() {
	    try {
	        FXMLLoader cargador = new FXMLLoader();
	        cargador.setLocation(Main.class.getResource("vista/RegTaller.fxml"));
	        AnchorPane taller = (AnchorPane) cargador.load();
	        
	        raiz.setCenter(taller);
            
            ControladorRegTaller controlador = cargador.getController();
            controlador.setMainApp(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void mostrarRegConductor() {
	    try {
	        FXMLLoader cargador = new FXMLLoader();
	        cargador.setLocation(Main.class.getResource("vista/RegConductor.fxml"));
	        AnchorPane conductor = (AnchorPane) cargador.load();
	        
	        raiz.setCenter(conductor);
            
            ControladorRegConductor controlador = cargador.getController();
            controlador.setMainApp(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void mostrarRegTaxi() {
	    try {
	        FXMLLoader cargador = new FXMLLoader();
	        cargador.setLocation(Main.class.getResource("vista/RegTaxi.fxml"));
	        AnchorPane taxi = (AnchorPane) cargador.load();
	        
	        raiz.setCenter(taxi);
            
            ControladorRegTaxi controlador = cargador.getController();
            controlador.setMainApp(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void mostrarTiposReportes() {
	    try {
	        FXMLLoader cargador = new FXMLLoader();
	        cargador.setLocation(Main.class.getResource("vista/VerReportes.fxml"));
	        AnchorPane reportes = (AnchorPane) cargador.load();
	        
	        raiz.setCenter(reportes);
            
            ControladorVerReportes controlador = cargador.getController();
            controlador.setMainApp(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void mostrarReporte(int tipo){
		 try {
	        FXMLLoader cargador = new FXMLLoader();
	        cargador.setLocation(Main.class.getResource("vista/VerReportes.fxml"));
	        AnchorPane reportes = (AnchorPane) cargador.load();
	        
	        raiz.setCenter(reportes);
            
            ControladorVerReportes controlador = cargador.getController();
            controlador.setMainApp(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void mostrarArbol(BtreeT arbol){
		try {
			Stage dialogo = new Stage();
			dialogo.setTitle("Arbol");
			Group root = new Group();
			Scene scene = new Scene(root,1000,800);

			double x = scene.getHeight() / 2;
			double y = 10;
			
			root = dibujarArbol(arbol.root, root, x, y);
			dialogo.setScene(scene);
			dialogo.centerOnScreen();
			dialogo.setResizable(false);
			dialogo.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Group dibujarArbol(Node t, Group root, double x, double y){
		
		if(t != null){
			root = dibujarNodo(x, y, 100, 50, root, t);
			if(t.children[0] != null){
				root = dibujarLinea(x + 100, y + 50, x + 150, y + 100, root);
				root = dibujarArbol(t.children[0], root, x + 150, y + 100);
			}
			if(t.children[1] != null){
				root = dibujarLinea(x, y + 50, x - 50, y + 100, root);
				root = dibujarArbol(t.children[1], root, x - 150, y + 100);
			}
                        if(t.children[2] != null){
				root = dibujarLinea(x + 100, y + 50, x + 150, y + 100, root);
				root = dibujarArbol(t.children[2], root, x + 150, y + 100);
			}
			if(t.children[3] != null){
				root = dibujarLinea(x, y + 50, x - 50, y + 100, root);
				root = dibujarArbol(t.children[3], root, x - 150, y + 100);
			}

		}
		
		return root;
		
	}
	
	private Group dibujarNodo(double x, double y, double ancho, double alto, Group root, Node t){
		
		Rectangle nodo = new Rectangle(x, y, ancho, alto);
		Text key = new Text(x + 10, y + 30, t.mostrar());
		nodo.setFill(Color.WHITE);
		nodo.setStroke(Color.BLACK);
		key.setFont(Font.font(18));
		root.getChildren().addAll(nodo, key);
		
		return root;
	}
	
	private Group dibujarLinea(double x1, double y1, double x2, double y2, Group root){
		
		Line linea = new Line(x1, y1, x2, y2);
		
		root.getChildren().add(linea);
		
		return root;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Flota taxis AFL");
        this.escenarioPrincipal.setResizable(false);
        
        iniPlanoRaiz();

        mostrarEscena();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}