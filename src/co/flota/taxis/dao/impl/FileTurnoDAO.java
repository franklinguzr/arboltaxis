package co.flota.taxis.dao.impl;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.nio.file.StandardOpenOption.APPEND;

import co.flota.taxis.modelo.Turno;
import co.flota.taxis.dao.TurnoDAO;
import co.flota.taxis.util.BtreeT;

public class FileTurnoDAO implements TurnoDAO{

	private static final String NOMBRE_ARCHIVO = "Turnos";
	private static final Path file = Paths.get(NOMBRE_ARCHIVO);
	private static final int LONGITUD_REGISTRO = 28;
	private static final int CODIGO_LONGITUD = 10;
	private static final int PLACA_TAXI_LONGITUD = 8;
	private static final int FECHA_LONGITUD = 10;
	private int posicion;

	private BtreeT indices = new BtreeT();

	public FileTurnoDAO(){
		if(!Files.exists(file)){
			try{
				Files.createFile(file);
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		crearIndice();
	}



	@Override
	public boolean guardarTurno(Turno turno){
		String registro = parseString(turno);

		byte data[] = registro.getBytes();
		ByteBuffer out = ByteBuffer.wrap(data);
		try(FileChannel fc = (FileChannel.open(file, APPEND))){
			fc.write(out);
			indices.insertar(turno.getCodigo(), posicion++);
			return true;
		} catch (IOException x){
			System.out.println("I/O Exception: " + x);
		}
		return false;
	}

	@Override
	public boolean turnoExistente(String codigo){

		int dir = this.indices.buscar(codigo);

		if(dir == -1){
			return false;
		}
		return true;
	}
	@Override
	public Turno obtenerTurno(String codigo){

		int dir = this.indices.buscar(codigo);

		if(dir == -1){
			return null;
		}
		return obtenerTurno(dir);
	}

	@Override
	public List<Turno> getAllTurnos(){

		List<Turno> turnos = new ArrayList<Turno>();

		try(SeekableByteChannel sbc = Files.newByteChannel(file)){
			ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
			String encoding = System.getProperty("file.encoding");
			while(sbc.read(buf) > 0){
				buf.rewind();
				Turno turno = parseTurno(Charset.forName(encoding).decode(buf));
				buf.flip();
				turnos.add(turno);
			}
		} catch(IOException x){
			System.out.println("caught exception: " + x);
		}
		return turnos;
	}

	private Turno obtenerTurno(int direccion){

		try(SeekableByteChannel sbc = Files.newByteChannel(file)){
			ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
			sbc.position(LONGITUD_REGISTRO * direccion);
			String encoding = System.getProperty("file.encoding");
			sbc.read(buf);
			buf.rewind();
			CharBuffer registro = Charset.forName(encoding).decode(buf);
			Turno turno = parseTurno(registro);
			buf.flip();
			return turno;
		} catch (IOException x){
			System.out.println("caught exception: " + x);
		}
		return null;
	}

	private void crearIndice(){

		try(SeekableByteChannel sbc = Files.newByteChannel(file)){
			ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
			String encoding = System.getProperty("file.encoding");
			posicion = 0;
			while (sbc.read(buf) > 0) {
				buf.rewind();
				CharBuffer registro = Charset.forName(encoding).decode(buf);
				String key = registro.subSequence(0, CODIGO_LONGITUD).toString().trim();
				this.indices.insertar(key, posicion++);
				buf.flip();
			}
		}catch (IOException x) {
			System.out.println("caught exception: " + x);
		}
	}

	private	Turno parseTurno(CharBuffer registro){
		String codigo = registro.subSequence(0, CODIGO_LONGITUD).toString().trim();
		registro.position(CODIGO_LONGITUD);
		registro = registro.slice();

		String placaTaxi = registro.subSequence(0, PLACA_TAXI_LONGITUD).toString().trim();
		registro.position(PLACA_TAXI_LONGITUD);
		registro = registro.slice();

		String fecha = registro.subSequence(0, FECHA_LONGITUD).toString().trim();
		registro.position(FECHA_LONGITUD);
		registro = registro.slice();

		Turno tu = new Turno(codigo, placaTaxi, fecha);
		return tu;
	}

	private String parseString(Turno turno) {

		StringBuilder registro = new StringBuilder(LONGITUD_REGISTRO);
		registro.append(completarCampoConEspacios(turno.getCodigo(),CODIGO_LONGITUD));
		registro.append(completarCampoConEspacios(turno.getPlacaTaxi(),PLACA_TAXI_LONGITUD));
		registro.append(completarCampoConEspacios(turno.getFecha(), FECHA_LONGITUD));

		return registro.toString();
	}

	private String completarCampoConEspacios(String campo, int tama?o){
		if(campo.length()>tama?o){
			campo=campo.substring(0, tama?o);
			return campo;
		}
		return String.format("%1$-" + tama?o + "s", campo);
	}

	@Override
	public BtreeT getArbol() {
		return this.indices;
	}
}