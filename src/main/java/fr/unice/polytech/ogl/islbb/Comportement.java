package main.java.fr.unice.polytech.ogl.islbb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 07/03/2015.
 * Retour des actions réalisées par le robot
 */
public class Comportement {
    HashMap<String,ArrayList<String>>resultscoot;
    String action;
    JSONObject obj;

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

    public Boolean getStatus(){
        if(obj.getString("status").equals("OK")) {
            return true;
        }
        else return false;
    }
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
    public int getresult(){
        int result;
        //try
        String num=obj.getJSONObject("extras").getString("amount");
        result=Integer.valueOf(num);
        return result;
    }

    public void setAction(String str){
        this.action=str;
    }
    public void setObj(String str){
        obj=new JSONObject(str);
    }
}

