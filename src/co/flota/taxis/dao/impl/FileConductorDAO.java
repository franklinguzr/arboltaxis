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

import co.flota.taxis.dao.ConductorDAO;
import co.flota.taxis.modelo.Conductor;
import co.flota.taxis.util.BtreeT;



public class FileConductorDAO implements ConductorDAO {

	private static final String NOMBRE_ARCHIVO = "conductor";
	private static final Path file= Paths.get(NOMBRE_ARCHIVO);
	private static final int LONGITUD_REGISTRO = 156;	
	private static final int IDENTIFICACION_LONGITUD = 10;
	private static final int NOMBRES_LONGITUD = 20;
	private static final int APELLIDOS_LONGITUD = 20;
	private static final int CORREO_LONGITUD = 40;
	private static final int CELULAR_LONGITUD = 10;
	private static final int CONTRASEÑA_LONGITUD = 30;
	private static final int ESTADO_LONGITUD = 5;
	private static final int FECHA_LONGITUD = 20;
	private int posicion;
	
	private BtreeT indices = new BtreeT();
        
	public FileConductorDAO(){		
		
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
	public boolean guardarConductor(Conductor conductor) {
		
		String registro = parseString(conductor);
		
		byte data[] = registro.getBytes();
		ByteBuffer out = ByteBuffer.wrap(data);	
		try (FileChannel fc = (FileChannel.open(file, APPEND))) {
			fc.write(out);
			indices.insertar(conductor.getId(), posicion++);
			return true;
		} catch (IOException x) {
			System.out.println("I/O Exception: " + x);
		}
		return false;
		
	}

	@Override
	public boolean conductorExistente(String id) {
		
		int dir = this.indices.buscar(id);
		
		if(dir == -1){
			return false;
		}
		return true;
		
	}
	
	@Override
	public Conductor obtenerConductor(String id) {
		
		int dir = this.indices.buscar(id);
		
		if(dir == -1){
			return null;
		}		
		return obtenerConductor(dir);
		
	}
	
	@Override
	public List<Conductor> getAllConductores(){
		
		List<Conductor> conductores = new ArrayList<Conductor>();
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
		    ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);  
		    String encoding = System.getProperty("file.encoding");
		    while (sbc.read(buf) > 0) {
		        buf.rewind();
		        Conductor conductor = parseConductor(Charset.forName(encoding).decode(buf));
		        buf.flip();
		        conductores.add(conductor);		        
		    }
		} catch (IOException x) {
		    System.out.println("caught exception: " + x);
		}
		return conductores;
		
	}
	
	private Conductor obtenerConductor(int direccion){
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
			ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
			sbc.position(LONGITUD_REGISTRO * direccion);
			String encoding = System.getProperty("file.encoding");
			sbc.read(buf);
			buf.rewind();
			CharBuffer registro = Charset.forName(encoding).decode(buf);
			Conductor conductor = parseConductor(registro);
			buf.flip();
			return conductor;
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
				String key = registro.subSequence(ESTADO_LONGITUD,ESTADO_LONGITUD + IDENTIFICACION_LONGITUD).toString().trim();
				this.indices.insertar(key, posicion++);				
				buf.flip();
			}
		} catch (IOException x) {
			System.out.println("caught exception: " + x);
		}
		
	}
	
	private Conductor parseConductor(CharBuffer registro){
		
		boolean estado = Boolean.parseBoolean(registro.subSequence(0, ESTADO_LONGITUD).toString().trim());
		registro.position(ESTADO_LONGITUD);
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
		
		String contraseña = registro.subSequence(0, CONTRASEÑA_LONGITUD).toString().trim();
		registro.position(CONTRASEÑA_LONGITUD);
		registro = registro.slice();
				
		String correo = registro.subSequence(0, CORREO_LONGITUD).toString().trim();
		registro.position(CORREO_LONGITUD);	
		registro = registro.slice();		
		
		String celular = registro.subSequence(0, CELULAR_LONGITUD).toString().trim();
		registro.position(CELULAR_LONGITUD);
		registro = registro.slice();
		
		LocalDate fecha = LocalDate.parse(registro.subSequence(0, FECHA_LONGITUD).toString().trim());
		registro.position(FECHA_LONGITUD);
		registro = registro.slice();
						
		char genero = registro.charAt(0);	
				
		Conductor c = new Conductor(nombres, apellidos, correo, id, contraseña, celular, fecha, genero, estado);
		return c;
		
	}
	
	private String parseString(Conductor conductor) {
		
		StringBuilder registro = new StringBuilder(LONGITUD_REGISTRO);
		registro.append(completarCampoConEspacios(conductor.getEstado().toString(),ESTADO_LONGITUD));
		registro.append(completarCampoConEspacios(conductor.getId(),IDENTIFICACION_LONGITUD));
		registro.append(completarCampoConEspacios(conductor.getNombre(), NOMBRES_LONGITUD));
		registro.append(completarCampoConEspacios(conductor.getApellido(),APELLIDOS_LONGITUD));
		registro.append(completarCampoConEspacios(conductor.getContraseña(),CONTRASEÑA_LONGITUD));
		registro.append(completarCampoConEspacios(conductor.getCorreo(),CORREO_LONGITUD));
		registro.append(completarCampoConEspacios(conductor.getCelular(), CELULAR_LONGITUD));
		registro.append(conductor.getFechaIngreso().toString());
		registro.append(conductor.getGenero());
				
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
