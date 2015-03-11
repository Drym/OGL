package fr.unice.polytech.ogl.islbb;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 09/03/2015.
 */
public class Data {

    ArrayList<String> cardinaux;
    HashMap<Integer,String>card;

    public Data() {
        card=new HashMap<Integer,String>();
        card.put(1,"N");
        card.put(2,"N");
        card.put(3,"N");
        card.put(4,"N");
        cardinaux = new ArrayList<String>();
        cardinaux.add("N");
        cardinaux.add("E");
        cardinaux.add("S");
        cardinaux.add("W");
    }
    public String getCardinaux(int i){
        return this.cardinaux.get(i);
    }
}
