package fr.unice.polytech.ogl.islbb.actions;

import org.json.JSONObject;

/**
 * Created by Lucas SAUVAGE on 19/03/2015.
 * Classe permetant d'automatiser l'action glimpse pour l'explorer.
 */
public class Glimpse {

    public static String glimpse(String direction, int range){

        JSONObject obj = new JSONObject();
        //TODO Demander a Mosser, si l'ordre est important
        JSONObject param = new JSONObject();

        param.put("direction",direction);
        param.put("range",range);

        obj.put("action","glimpse");
        obj.put("parameters",param);

        return obj.toString();

    }
}
