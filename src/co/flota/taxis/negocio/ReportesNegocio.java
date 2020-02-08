package co.flota.taxis.negocio;

import java.util.List;

import co.flota.taxis.dao.ConductorDAO;
import co.flota.taxis.dao.PropietarioDAO;
import co.flota.taxis.dao.TallerDAO;
import co.flota.taxis.dao.TaxiDAO;
import co.flota.taxis.dao.impl.FileConductorDAO;
import co.flota.taxis.dao.impl.FilePropietarioDAO;
import co.flota.taxis.dao.impl.FileTallerDAO;
import co.flota.taxis.dao.impl.FileTaxiDAO;
import co.flota.taxis.modelo.Conductor;
import co.flota.taxis.modelo.Propietario;
import co.flota.taxis.modelo.Taller;
import co.flota.taxis.modelo.Taxi;
import co.flota.taxis.util.BtreeT;

public class ReportesNegocio {
	
	private TaxiDAO taxiDAO;
	private PropietarioDAO propietarioDAO;
	private ConductorDAO conductorDAO;
	private TallerDAO tallerDAO;
	
	public ReportesNegocio(){
		this.taxiDAO = new FileTaxiDAO();
		this.propietarioDAO = new FilePropietarioDAO();
		this.conductorDAO = new FileConductorDAO();
		this.tallerDAO = new FileTallerDAO();
	}
	
	public BtreeT getArbolTaxis(){
		return this.taxiDAO.getArbol();
	}
	
	public BtreeT getArbolPropietarios(){
		return this.propietarioDAO.getArbol();
	}
	
	public BtreeT getArbolConductores(){
		return this.conductorDAO.getArbol();
	}
	
	public BtreeT getArbolTaller(){
		return this.tallerDAO.getArbol();
	}
	
	public List<Taxi> reporteTaxis(){
		return this.taxiDAO.getAllTaxis();
	}
	
	public List<Propietario> reportePropietarios(){
		return this.propietarioDAO.getAllPropietarios();
	}
	
	public List<Conductor> reporteConductores(){
		return this.conductorDAO.getAllConductores();
	}
	
	public List<Taller> reporteTalleres(){
		return this.tallerDAO.getAllTalleres();
	}
	

	
	

}
