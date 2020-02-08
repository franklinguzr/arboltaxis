package co.flota.taxis.dao;

import java.util.List;

import co.flota.taxis.modelo.Turno;
import co.flota.taxis.util.BtreeT;

public interface TurnoDAO {
	
	public boolean guardarTurno(Turno turno);
	public boolean turnoExistente(String codigo);
	public Turno obtenerTurno(String codigo);
	public List<Turno> getAllTurnos();
	public BtreeT getArbol();

}
