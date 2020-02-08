/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.flota.taxis.util;
/**
 *
 * @author Garces Usma
 */
public class Termino {
    public String valor;
    public int direccion;

    public Termino(String valor, int direccion) {
        this.valor = valor;
        this.direccion = direccion;
    }
    
    
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
    
}
