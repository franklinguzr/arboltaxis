package co.flota.taxis.dao.impl;
import static java.nio.file.StandardOpenOption.APPEND;

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

import co.flota.taxis.dao.TaxiDAO;
import co.flota.taxis.modelo.Taxi;
import co.flota.taxis.util.BtreeT;


public class FileTaxiDAO implements TaxiDAO{

	private static final String NOMBRE_ARCHIVO = "Taxis";
	private static final Path file= Paths.get(NOMBRE_ARCHIVO);
	private static final int LONGITUD_REGISTRO = 56;
	private static final int PLACA_LONGITUD = 15;
	private static final int MARCA_LONGITUD = 10;
	private static final int MODELO_LONGITUD = 20;
	private static final int COMBUSTIBLE_LONGITUD = 11;
	private int posicion;
	
	
	private BtreeT indices = new BtreeT();
	public FileTaxiDAO() {
		if (!Files.exists(file)) {
			try {
				Files.createFile(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		crearIndice();
	}
	
	
	
	@Override
	public boolean guardarTaxi(Taxi taxi) {
		String registro = parseString(taxi);
		
		byte data[] = registro.getBytes();
		ByteBuffer out = ByteBuffer.wrap(data);	
		try (FileChannel fc = (FileChannel.open(file, APPEND))) {
			fc.write(out);
			indices.insertar(taxi.getPlaca(), posicion++);
			return true;
		} catch (IOException x) {
			System.out.println("I/O Exception: " + x);
		}
		return false;
	}
	@Override
	public boolean taxiExistente(String placa) {
		
		int dir = this.indices.buscar(placa);
		
		if(dir == -1){
			return false;
		}
		return true;
	}
	@Override
	public Taxi obtenerTaxi(String placa) {
		
		int dir = this.indices.buscar(placa);
		
		if(dir == -1){
			return null;
		}		
		return obtenerTaxi(dir);
	}
	
	@Override
	public List<Taxi> getAllTaxis(){
		
		List<Taxi> taxis = new ArrayList<Taxi>();
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
		    ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);  
		    String encoding = System.getProperty("file.encoding");
		    while (sbc.read(buf) > 0) {
		        buf.rewind();
		        Taxi taxi = parseTaxi(Charset.forName(encoding).decode(buf));
		        buf.flip();
		        taxis.add(taxi);		        
		    }
		} catch (IOException x) {
		    System.out.println("caught exception: " + x);
		}
		return taxis;
		
	}
	
	private Taxi obtenerTaxi(int direccion){
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
			ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
			sbc.position(LONGITUD_REGISTRO * direccion);
			String encoding = System.getProperty("file.encoding");
			sbc.read(buf);
			buf.rewind();
			CharBuffer registro = Charset.forName(encoding).decode(buf);
			Taxi taxi = parseTaxi(registro);
			buf.flip();
			return taxi;
		} catch (IOException x) {
			System.out.println("caught exception: " + x);
		}
		return null;
		
	}
	
	private void crearIndice(){
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
			ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);		
			String encoding = System.getProperty("file.encoding");
			posicion = 0;
			while (sbc.read(buf) > 0) {
				buf.rewind();
				CharBuffer registro = Charset.forName(encoding).decode(buf);
				String key = registro.subSequence(0, MARCA_LONGITUD).toString().trim();
				this.indices.insertar(key, posicion++);				
				buf.flip();
			}
		} catch (IOException x) {
			System.out.println("caught exception: " + x);
		}
		
	}
	
	private Taxi parseTaxi(CharBuffer registro){
		
		String marca = registro.subSequence(0, MARCA_LONGITUD).toString().trim();
		registro.position(MARCA_LONGITUD);
		registro = registro.slice();
				
		String placa = registro.subSequence(0, PLACA_LONGITUD).toString().trim();
		registro.position(PLACA_LONGITUD);	
		registro = registro.slice();		
		
		String modelo = registro.subSequence(0, MODELO_LONGITUD).toString().trim();
		registro.position(MODELO_LONGITUD);
		registro = registro.slice();
		
		String combustible = registro.subSequence(0, COMBUSTIBLE_LONGITUD).toString().trim();
		registro.position(COMBUSTIBLE_LONGITUD);
		registro = registro.slice();
				
		Taxi ta = new Taxi(marca, placa, modelo, combustible);
		return ta;
		
	}
	
	private String parseString(Taxi taxi) {
		
		StringBuilder registro = new StringBuilder(LONGITUD_REGISTRO);
		registro.append(completarCampoConEspacios(taxi.getMarca(),MARCA_LONGITUD));
		registro.append(completarCampoConEspacios(taxi.getPlaca(),PLACA_LONGITUD));
		registro.append(completarCampoConEspacios(taxi.getModelo(), MODELO_LONGITUD));
		registro.append(completarCampoConEspacios(taxi.getCombustible(),COMBUSTIBLE_LONGITUD));
				
		return registro.toString();
		
	}

	private String completarCampoConEspacios(String campo, int tamaño){
		if(campo.length()>tamaño){
			campo=campo.substring(0, tamaño);
			return campo;
		}
		return String.format("%1$-" + tamaño + "s", campo);
	}

    @Override
    public BtreeT getArbol() {
   return this.indices;
    }
	
}
