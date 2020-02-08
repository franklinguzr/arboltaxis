package co.flota.taxis.util;

public class ArbolAVL {

	private Nodo raiz;
	
	public void insertar(String key, int dir){
		raiz = insertar(key, dir, raiz);
	}
	
	public Nodo getRaiz(){
		return raiz;
	}
	
	public int buscar(String key){
		return buscar(key, raiz);
	}
	
	private int buscar(String key, Nodo t){
		int dir = -1;
		 if(t==null){
			 return dir;
		}
		if(key.compareTo(t.getKey()) == 0){
			dir = t.getDir();
		}else if(key.compareTo(t.getKey()) > 0 && t.getLigaDer() != null){
			dir = buscar(key, t.getLigaDer());
		}else if(key.compareTo(t.getKey()) < 0 && t.getLigaIzq() != null){
			dir = buscar(key, t.getLigaIzq());
		}
		return dir;
	}
	
	private Nodo insertar(String key, int dir, Nodo t){
		if (t == null) {
            t = new Nodo(key, dir);	
		}else if(key.compareTo(t.getKey()) == 0){
			return null;
        }else if (key.compareTo(t.getKey()) < 0) {
            t.setLigaIzq(insertar(key, dir, t.getLigaIzq()));
            t.setAltura(calcularAltura(t));
            int fe = altura(t.getLigaDer()) - altura(t.getLigaIzq());
            if (fe == 2 || fe == -2) {
                if (key.compareTo(t.getLigaIzq().getKey()) < 0) {
                    t = rotacionSimpleIzquierda(t);
                }else{
                    t = rotacionDobleIzquierda(t);
                }
            }            
        }else if (key.compareTo(t.getKey()) > 0) {
            t.setLigaDer(insertar(key, dir, t.getLigaDer()));
            t.setAltura(calcularAltura(t));
            int fe = altura(t.getLigaDer()) - altura(t.getLigaIzq());
            if (fe == 2 || fe == -2) {
                if (key.compareTo(t.getLigaDer().getKey()) > 0) {
                    t = rotacionSimpleDerecha(t);
                }else{
                    t = rotacionDobleDerecha(t);
                }
            }
        }  
        return t;
	}
	
	private Nodo rotacionSimpleIzquierda(Nodo t){
		Nodo aux2 = t.getLigaIzq();
		t.setLigaIzq(aux2.getLigaDer());
		aux2.setLigaDer(t);
		t.setAltura(max(altura(t.getLigaIzq()), altura(t.getLigaDer())) + 1);
		aux2.setAltura(max(altura(aux2.getLigaIzq()), t.getAltura()) + 1);
		return aux2;
	}
		   
	private Nodo rotacionSimpleDerecha(Nodo t){
	   Nodo aux2 = t.getLigaDer();
	   t.setLigaDer(aux2.getLigaIzq());
	   aux2.setLigaIzq(t);
	   t.setAltura(max(altura(t.getLigaIzq()),altura(t.getLigaDer())) + 1);
	   aux2.setAltura(max(altura(aux2.getLigaDer()),t.getAltura()) + 1);
	   return aux2;
	}
		   
	private Nodo rotacionDobleIzquierda(Nodo aux){
		aux.setLigaIzq(rotacionSimpleDerecha(aux.getLigaIzq()));
		return rotacionSimpleIzquierda(aux);
	}
		   
	private Nodo rotacionDobleDerecha(Nodo aux){
       aux.setLigaDer(rotacionSimpleIzquierda(aux.getLigaDer()));
       return rotacionSimpleDerecha(aux);
	}
	
	private int calcularAltura(Nodo actual){
       if (actual == null) {
           return -1;
       }else{
           return 1 + max(calcularAltura(actual.getLigaIzq()), calcularAltura(actual.getLigaDer()));
       }
	}
	
	private int max(int izquerdaAltura, int derechaAltura){
		return izquerdaAltura > derechaAltura ? izquerdaAltura:derechaAltura;
	}
	
	private int altura(Nodo t){
		return t == null ? -1 : t.getAltura();
	}
	
}
