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

import co.flota.taxis.dao.TallerDAO;
import co.flota.taxis.modelo.Taller;
import co.flota.taxis.util.BtreeT;



public class FileTallerDAO implements TallerDAO{

	private static final String NOMBRE_ARCHIVO = "Talleres";
	private static final Path file= Paths.get(NOMBRE_ARCHIVO);
	private static final int LONGITUD_REGISTRO = 56;
	private static final int CIUDAD_LONGITUD = 15;
	private static final int CODIGO_LONGITUD = 10;
	private static final int DIRECCION_LONGITUD = 20;
	private static final int HORARIO_LONGITUD = 11;
	private int posicion;
	
	private BtreeT indices = new BtreeT();
        
	public FileTallerDAO() {
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
	public boolean guardarTaller(Taller taller) {
		String registro = parseString(taller);
		
		byte data[] = registro.getBytes();
		ByteBuffer out = ByteBuffer.wrap(data);	
		try (FileChannel fc = (FileChannel.open(file, APPEND))) {
			fc.write(out);
			indices.insertar(taller.getCodigo(), posicion++);
			return true;
		} catch (IOException x) {
			System.out.println("I/O Exception: " + x);
		}
		return false;
	}
	@Override
	public boolean tallerExistente(String codigo) {
		
		int dir = this.indices.buscar(codigo);
		
		if(dir == -1){
			return false;
		}
		return true;
	}
	@Override
	public Taller obtenerTaller(String codigo) {
		
		int dir = this.indices.buscar(codigo);
		
		if(dir == -1){
			return null;
		}		
		return obtenerTaller(dir);
	}
	
	@Override
	public List<Taller> getAllTalleres(){
		
		List<Taller> talleres = new ArrayList<Taller>();
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
		    ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);  
		    String encoding = System.getProperty("file.encoding");
		    while (sbc.read(buf) > 0) {
		        buf.rewind();
		        Taller taller = parseTaller(Charset.forName(encoding).decode(buf));
		        buf.flip();
		        talleres.add(taller);		        
		    }
		} catch (IOException x) {
		    System.out.println("caught exception: " + x);
		}
		return talleres;
		
	}
	
	private Taller obtenerTaller(int direccion){
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
			ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
			sbc.position(LONGITUD_REGISTRO * direccion);
			String encoding = System.getProperty("file.encoding");
			sbc.read(buf);
			buf.rewind();
			CharBuffer registro = Charset.forName(encoding).decode(buf);
			Taller taller = parseTaller(registro);
			buf.flip();
			return taller;
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
				String key = registro.subSequence(0, CODIGO_LONGITUD).toString().trim();
				this.indices.insertar(key, posicion++);				
				buf.flip();
			}
		} catch (IOException x) {
			System.out.println("caught exception: " + x);
		}
		
	}
	
	private Taller parseTaller(CharBuffer registro){
		
		String codigo = registro.subSequence(0, CODIGO_LONGITUD).toString().trim();
		registro.position(CODIGO_LONGITUD);
		registro = registro.slice();
				
		String ciudad = registro.subSequence(0, CIUDAD_LONGITUD).toString().trim();
		registro.position(CIUDAD_LONGITUD);	
		registro = registro.slice();		
		
		String direccion = registro.subSequence(0, DIRECCION_LONGITUD).toString().trim();
		registro.position(DIRECCION_LONGITUD);
		registro = registro.slice();
		
		String horario = registro.subSequence(0, HORARIO_LONGITUD).toString().trim();
		registro.position(HORARIO_LONGITUD);
		registro = registro.slice();
				
		Taller t = new Taller(codigo, ciudad, direccion, horario);
		return t;
		
	}
	
	private String parseString(Taller taller) {
		
		StringBuilder registro = new StringBuilder(LONGITUD_REGISTRO);
		registro.append(completarCampoConEspacios(taller.getCodigo(),CODIGO_LONGITUD));
		registro.append(completarCampoConEspacios(taller.getCiudad(),CIUDAD_LONGITUD));
		registro.append(completarCampoConEspacios(taller.getDireccion(), DIRECCION_LONGITUD));
		registro.append(completarCampoConEspacios(taller.getHorario(),HORARIO_LONGITUD));
				
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
