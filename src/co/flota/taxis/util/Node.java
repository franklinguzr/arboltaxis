package co.flota.taxis.util;

/**
 * This is the the class of {@link btree.Btree} Node. In the class, we declare and define the the variables that we
 * need in the node of the {@link btree.Btree}. Also, we define the constructor of the node class.
 * 
 * for CS 380 - Final Project: Implementation of B-Tree Data Structure
 * @since 12/01/2011
 * @author Abdulrhman Alkhodiry, Ahmed Alsalama
 */
public class Node {

    /**
     * number of nodes
     */
    public int numberOfNodes;              // number of nodes
    /**
     * the array that holds the keys value.
     */
    public String key[];                      // the array that holds the keys value.
    /**
     * the array that holds the references of the keys in the node.
     */
    public Node children[];                // the array that holds the references of the keys in the node.
    /**
     * the variable to deterimed if the node is is Leaf or not.
     */
    public boolean isLeaf;                   // the variable to deterime if the node is is Leaf or not.

    /**
     * The constructor of the node class
     * The node can have at most 3 keys. We have 4 references for each node, and assign the node to be isLeaf.
     */
   
    
    public Termino []terminos;
    
    Node() {
        
        key = new String[3];             // The node can have at most 3 keys
        children = new Node[4];       // We have 4 references for each node
        isLeaf = true;                  // assign the node to be Leaf.
        terminos= new Termino[3];
    }

   
    public String mostrar() {
        StringBuilder retorno  = new StringBuilder();
         retorno.append('[');
        for (int i = 0; i < 3; i++) {
            if(terminos[i]!=null){
            retorno.append(terminos[i].valor);
            }
        }
        retorno.append(']');
        return retorno.toString();
    }
    
    
    
    
}
