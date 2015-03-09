package main.java.fr.unice.polytech.ogl.islbb;

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
    HashMap<String,ArrayList<String>>resultscoot;//résultat des scoots ex{"N",{"WOOD","FLOWER"};...}
    String action; //dernière action réalise
    JSONObject obj;//résultat envoyé par Acknoledgeresult

    public Comportement(){
        resultscoot=new HashMap<String,ArrayList<String>>();
        obj = new JSONObject();
        action="";
    }
    public Comportement(String str){
        resultscoot=new HashMap<String,ArrayList<String>>();
        obj = new JSONObject();
        action=str;
    }
/*
Renvoie true si staut=OK
 */
    public Boolean getStatus(){
        if(obj.getString("status").equals("OK")) {
            return true;
        }
        else return false;
    }
    /*
    Traite les informations des scoot et les inserre dans la HASHMAP
     */
    public void getscout(String str){
        int i;
        ArrayList<String>result=new ArrayList<String>();
        if(this.getStatus()){
            JSONArray tab=obj.getJSONObject("extras").getJSONArray("resources");
            for(i=0;i<tab.length();i++){
                result.add(tab.getString(i));
            }
            resultscoot.put(str,result);
        }
    }

    /*
    renvoie la quentité de ressource
     */
    public int Exploitresult(){
        int result;
        //try
        String num=obj.getJSONObject("extras").getString("amount");
        result=Integer.valueOf(num);
        return result;
    }

    public String takeDirection(){
        String cle;
        ArrayList<String>liste=new ArrayList<String>();
        HashMap<String,ArrayList<String>>trie=new HashMap<String,ArrayList<String>>();
        Set cles = resultscoot.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()){
            cle=(String)it.next();
            liste=resultscoot.get(cle);
            if(!liste.contains("WATER")&&liste.contains("WOOD")) {
                trie.put(cle, liste);
                if (!liste.contains("WATER")) {
                    trie.put(cle, liste);
                }
            }
        }if(trie.keySet().iterator().next()!=null)
            return trie.keySet().iterator().next();
            else return "rien";
    }
    /*
    setters
     */
    public void setAction(String str){
        this.action=str;
    }
    /*
    setters de notre retour d'action
     */
    public void setObj(String str){
        obj=new JSONObject(str);
    }
}

