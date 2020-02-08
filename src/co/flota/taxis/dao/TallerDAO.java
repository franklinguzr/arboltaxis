package co.flota.taxis.dao;

import java.util.List;

import co.flota.taxis.modelo.Taller;
import co.flota.taxis.util.BtreeT;


public interface TallerDAO {
	
	public boolean guardarTaller(Taller cajero);
	public boolean tallerExistente(String codigo);
	public Taller obtenerTaller(String codigo);
	public List<Taller> getAllTalleres();
	public BtreeT getArbol();

}
