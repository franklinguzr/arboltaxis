package co.flota.taxis.dao;

import java.util.List;

import co.flota.taxis.modelo.Conductor;
import co.flota.taxis.util.BtreeT;

public interface ConductorDAO {
	
	public boolean guardarConductor(Conductor conductor);
	public boolean conductorExistente(String id);
	public Conductor obtenerConductor(String id);
	public List<Conductor> getAllConductores();
	public BtreeT getArbol();

}
