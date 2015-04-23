package fr.unice.polytech.ogl.islbb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by user on 07/03/2015.
 * Retour des actions réalisées par le robot
 */

public class Comportement {

    /*
    HashMap<String,ArrayList<String>> scoutResults; //résultat des scoots ex{"N",{"WOOD","FLOWER"};...}
    String action; //dernière action réalisée
    JSONObject obj; //résultat envoyé par Acknoledgeresult
    String lastDirection; //dernière direction prise

    public Comportement(){
        scoutResults = new HashMap<String,ArrayList<String>>();
        obj = new JSONObject();
        action = "";
    }
    public Comportement(String str){
        scoutResults = new HashMap<String,ArrayList<String>>();
        obj = new JSONObject();
        action = str;
    }


//Renvoie true si staut=OK

    public Boolean getStatus(){
        if(obj.getString("status").equals("OK")) {
            return true;
        }
        else return false;
    }

    //Traite les informations des scoot et les inserre dans la HASHMAP

    public void getScout(String str, String dir){
        int i;
        if(scoutResults.size() == 4) {
            scoutResults.clear();
        }
        this.setObj(str);
        ArrayList<String>result=new ArrayList<String>();
        if(this.getStatus()){
            JSONArray tab=obj.getJSONObject("extras").getJSONArray("resources");
            for(i=0;i<tab.length();i++){
                result.add(tab.getString(i));
            }
            scoutResults.put(dir, result);
        }
    }


    //renvoie la quantité de ressource

    public int getExploitAmount(){
        int result;
        //try
        String num = obj.getJSONObject("extras").getString("amount");
                    result=Integer.valueOf(num);
                    return result;
                }

        public String takeDirection(){
            String cle;
            ArrayList<String>liste = new ArrayList<String>();
            HashMap<String,ArrayList<String>>trie = new HashMap<String,ArrayList<String>>();
            HashMap<String,ArrayList<String>>bois = new HashMap<String,ArrayList<String>>();
            Set cles = scoutResults.keySet();
            Iterator it = cles.iterator();
            while (it.hasNext()){
                cle=(String)it.next();
                liste= scoutResults.get(cle);
                if(!liste.contains("WATER")&&liste.contains("WOOD")) {
                    bois.put(cle, liste);
                }
                if (!liste.contains("WATER")) {
                    trie.put(cle, liste);
                }
        }
        if(!trie.keySet().iterator().hasNext()){
            liste.add("WATER");
            trie.put("N",liste);
        }
        if(bois.keySet().iterator().hasNext())
            return (String)bois.keySet().iterator().next().toString();
            else return trie.keySet().iterator().next().toString();
    }
    public boolean hasWood(String direction){
        if (scoutResults.get(direction).contains("WOOD")){
            return true;
        }
        else return false;
    }

    //setters

    public void setAction(String str){
        this.action=str;
    }

    //setters de notre retour d'action

    public void setObj(String str){
        obj=new JSONObject(str);
    }

*/
}

