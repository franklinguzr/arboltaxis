package co.flota.taxis.util;

public class Numerico {
	public static boolean esNumerico(String cadena){
		
		try{
			Integer.parseInt(cadena);
			
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
}
