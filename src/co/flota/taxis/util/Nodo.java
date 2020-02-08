package co.flota.taxis.util;

public class Nodo {
	
	private String key;
	private int dir, altura;
	private Nodo ligaIzq, ligaDer;
	
	public Nodo(String key, int dir){
		this(key, dir, null, null);
	}
	
	public Nodo(String key, int dir, Nodo ligaIzq, Nodo ligaDer){
		this.key = key;
		this.dir = dir;
		this.ligaDer = ligaDer;
		this.ligaIzq = ligaIzq;
		this.altura = 0;
	}
	
	public String getKey(){
		return key;
	}
	
	public void setKey(String key){
		this.key = key;
	}
	
	public int getDir(){
		return dir;
	}
	
	public void setDir(int dir){
		this.dir = dir;
	}
	
	public Nodo getLigaIzq(){
		return ligaIzq;
	}
	
	public void setLigaIzq(Nodo ligaIzq){
		this.ligaIzq = ligaIzq;
	}
	
	public Nodo getLigaDer(){
		return ligaDer;
	}
	
	public void setLigaDer(Nodo ligaDer){
		this.ligaDer = ligaDer;
	}
	
	public int getAltura(){
		return altura;
	}
	
	public void setAltura(int altura){
		this.altura = altura;
	}
}
