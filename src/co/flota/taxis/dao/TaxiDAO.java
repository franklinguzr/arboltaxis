package co.flota.taxis.dao;

import java.util.List;


import co.flota.taxis.modelo.Taxi;
import co.flota.taxis.util.BtreeT;

public interface TaxiDAO {
	
	public boolean guardarTaxi(Taxi cajero);
	public boolean taxiExistente(String codigo);
	public Taxi obtenerTaxi(String codigo);
	public List<Taxi> getAllTaxis();
	public BtreeT getArbol();

}
