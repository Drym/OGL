package fr.unice.polytech.ogl.islbb;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by user on 07/03/2015.
 */
public class Comportement {
    String action;
    JSONObject obj;
    public Comportement(String str){
        obj = new JSONObject();
        action=str;
    }

    public void setJson(String str){
        obj=new JSONObject(str);
    }
    public Boolean getStatus(){
       if(obj.getString("status").equals("OK")) {
           return true;
       }
           else return false;
       }
    public boolean rscout(){
        int i;
        JSONArray tab=obj.getJSONObject("extras").getJSONArray("resources");
       for(i=0;i<tab.length();i++){
           if(tab.getString(i).equals("WOOD")) {
               return true;
           }
       }
        return false;
    }
}

