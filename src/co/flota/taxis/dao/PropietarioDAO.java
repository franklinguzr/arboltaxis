package co.flota.taxis.dao;

import java.util.List;

import co.flota.taxis.modelo.Propietario;
import co.flota.taxis.util.BtreeT;



public interface PropietarioDAO {

	boolean propietarioExistente(String id);
	boolean guardarPropietario(Propietario propietario);
	public Propietario obtenerPropietario(String id);
	public List<Propietario> getAllPropietarios();
	public BtreeT getArbol();

}
