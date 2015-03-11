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
    public void getscout(String str,String dir){
        int i;
        if(resultscoot.size()==4) {
            resultscoot.clear();
        }
        this.setObj(str);
        ArrayList<String>result=new ArrayList<String>();
        if(this.getStatus()){
            JSONArray tab=obj.getJSONObject("extras").getJSONArray("resources");
            for(i=0;i<tab.length();i++){
                result.add(tab.getString(i));
            }
            resultscoot.put(dir,result);
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
        HashMap<String,ArrayList<String>>bois=new HashMap<String,ArrayList<String>>();
        Set cles = resultscoot.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()){
            cle=(String)it.next();
            liste=resultscoot.get(cle);
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
    public boolean haswood(String direction){
        if (resultscoot.get(direction).contains("WOOD")){
            return true;
        }
        else return false;
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

    public static void main(String[] args)
    {
        Comportement test=new Comportement();
        String r1="{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":-23}}";
        String r2="{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":-23}}";
        String r3="{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"WATER\"],\"altitude\":-23}}";
        String r4="{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"WOOD\"],\"altitude\":-23}}";
        //String r5="{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"WOOD\"],\"altitude\":-23}}";
        test.getscout(r1,"N");
        test.getscout(r2,"S");
        test.getscout(r3,"E");
        test.getscout(r4,"O");
        //test.getscout(r5,"N");
        Set cles = test.resultscoot.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()){
            String clef = (String)it.next();
            System.out.println(test.resultscoot.get(clef));
        }
        System.out.println(test.resultscoot);
        System.out.println(test.takeDirection());
        System.out.println(test.haswood(test.takeDirection()));
    }
}

