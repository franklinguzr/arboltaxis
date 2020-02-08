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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.flota.taxis.dao.PropietarioDAO;
import co.flota.taxis.modelo.Propietario;
import co.flota.taxis.util.BtreeT;





public class FilePropietarioDAO implements PropietarioDAO{
	
	private static final String NOMBRE_ARCHIVO = "Propietarios";
	private static final Path file= Paths.get(NOMBRE_ARCHIVO);
	private static final int LONGITUD_REGISTRO = 146;
	private static final int IDENTIFICACION_LONGITUD = 10;
	private static final int NOMBRES_LONGITUD = 20;
	private static final int APELLIDOS_LONGITUD = 20;
	private static final int CORREO_LONGITUD = 40;
	private static final int CELULAR_LONGITUD = 10;
	private static final int DIRECCION_LONGITUD = 30;
	private static final int PRIMERA_COMPRA_LONGITUD = 5;
	private static final int FECHA_LONGITUD = 10;
	private int posicion;
	
	private BtreeT indices = new BtreeT();
	
	public FilePropietarioDAO(){		
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
	public boolean propietarioExistente(String id) {
		
		int dir = this.indices.buscar(id);
		
		if(dir == -1){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean guardarPropietario(Propietario propietario) {
		
		String registro = parseString(propietario);
		
		byte data[] = registro.getBytes();
		ByteBuffer out = ByteBuffer.wrap(data);	
		try (FileChannel fc = (FileChannel.open(file, APPEND))) {
			fc.write(out);
			indices.insertar(propietario.getId(), posicion++);
			return true;
		} catch (IOException x) {
			System.out.println("I/O Exception: " + x);
		}
		return false;
		
	}
	@Override
	public Propietario obtenerPropietario(String id) {
		
		int dir = this.indices.buscar(id);
		
		if(dir == -1){
			return null;
		}		
		return obtenerPropietario(dir);
	}
	
	@Override
	public List<Propietario> getAllPropietarios(){
		
		List<Propietario> propietarios = new ArrayList<Propietario>();
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
		    ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);  
		    String encoding = System.getProperty("file.encoding");
		    while (sbc.read(buf) > 0) {
		        buf.rewind();
		        Propietario propietario = parsePropietario(Charset.forName(encoding).decode(buf));
		        buf.flip();
		        propietarios.add(propietario);		        
		    }
		} catch (IOException x) {
		    System.out.println("caught exception: " + x);
		}
		return propietarios;
		
	}
	
	private Propietario obtenerPropietario(int direccion){
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
			ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
			sbc.position(LONGITUD_REGISTRO * direccion);
			String encoding = System.getProperty("file.encoding");
			sbc.read(buf);
			buf.rewind();
			CharBuffer registro = Charset.forName(encoding).decode(buf);
			Propietario propietario = parsePropietario(registro);
			buf.flip();
			return propietario;
		} catch (IOException x) {
			System.out.println("caught exception: " + x);
		}
		return null;
		
	}
	
	private void crearIndice(){
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
			ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);		
			String encoding = System.getProperty("file.encoding");
			posicion=0;
			while (sbc.read(buf) > 0) {
				buf.rewind();
				CharBuffer registro = Charset.forName(encoding).decode(buf);
				String key = registro.subSequence(PRIMERA_COMPRA_LONGITUD, PRIMERA_COMPRA_LONGITUD + IDENTIFICACION_LONGITUD).toString().trim();
				this.indices.insertar(key, posicion++);				
				buf.flip();
			}
		} catch (IOException x) {
			System.out.println("caught exception: " + x);
		}
		
	}
	
	private Propietario parsePropietario(CharBuffer registro){
		
		boolean primeraCompra = Boolean.parseBoolean(registro.subSequence(0, PRIMERA_COMPRA_LONGITUD).toString().trim());
		registro.position(PRIMERA_COMPRA_LONGITUD);
		registro = registro.slice();
		
		String id = registro.subSequence(0, IDENTIFICACION_LONGITUD).toString().trim();
		registro.position(IDENTIFICACION_LONGITUD);
		registro = registro.slice();
				
		String nombres = registro.subSequence(0, NOMBRES_LONGITUD).toString().trim();
		registro.position(NOMBRES_LONGITUD);	
		registro = registro.slice();		
		
		String apellidos = registro.subSequence(0, APELLIDOS_LONGITUD).toString().trim();
		registro.position(APELLIDOS_LONGITUD);
		registro = registro.slice();
		
		String direccion = registro.subSequence(0, DIRECCION_LONGITUD).toString().trim();
		registro.position(DIRECCION_LONGITUD);
		registro = registro.slice();
				
		String correo = registro.subSequence(0, CORREO_LONGITUD).toString().trim();
		registro.position(CORREO_LONGITUD);	
		registro = registro.slice();		
		
		String celular = registro.subSequence(0, CELULAR_LONGITUD).toString().trim();
		registro.position(CELULAR_LONGITUD);
		registro = registro.slice();
		
		LocalDate fecha = LocalDate.parse(registro.subSequence(0, FECHA_LONGITUD));
		registro.position(FECHA_LONGITUD);
		registro = registro.slice();
				
		char genero = registro.charAt(0);		
		
		Propietario p = new Propietario(nombres, apellidos, correo, id, direccion, celular, fecha, genero, primeraCompra);
		return p;
		
	}
	
	private String parseString(Propietario propietario) {
		
		StringBuilder registro = new StringBuilder(LONGITUD_REGISTRO);
		registro.append(completarCampoConEspacios(propietario.isPrimeraCompra().toString(),PRIMERA_COMPRA_LONGITUD));
		registro.append(completarCampoConEspacios(propietario.getId(),IDENTIFICACION_LONGITUD));
		registro.append(completarCampoConEspacios(propietario.getNombre(), NOMBRES_LONGITUD));
		registro.append(completarCampoConEspacios(propietario.getApellido(),APELLIDOS_LONGITUD));
		registro.append(completarCampoConEspacios(propietario.getDireccion(),DIRECCION_LONGITUD));
		registro.append(completarCampoConEspacios(propietario.getCorreo(),CORREO_LONGITUD));
		registro.append(completarCampoConEspacios(propietario.getCelular(), CELULAR_LONGITUD));
		registro.append(propietario.getFechaIngreso().toString());
		registro.append(propietario.getGenero());
				
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
