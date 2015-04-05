package fr.unice.polytech.ogl.islbb.actions;

import fr.unice.polytech.ogl.islbb.Data;
import org.json.JSONObject;

/**
 * Created by Lucas SAUVAGE on 19/03/2015.
 * Classe permetant d'automatiser l'action glimpse pour l'explorer.
 */
public class Glimpse {

    public static String glimpse(String direction, int range){

        JSONObject obj = new JSONObject();
        JSONObject param = new JSONObject();

        param.put("direction",direction);
        param.put("range",range);

        obj.put("action","glimpse");
        obj.put("parameters",param);

        return obj.toString();

    }

    public static String glimpse(int direction, int range) {

        Data directions = new Data();

        JSONObject obj = new JSONObject();
        JSONObject param = new JSONObject();

        param.put("direction", directions.getCardinaux(direction));
        param.put("range",range);

        obj.put("action","glimpse");
        obj.put("parameters",param);

        return obj.toString();

    }

    public static String glimpse(int direction, int range, String debug) {

        Data directions = new Data();

        JSONObject obj = new JSONObject();
        JSONObject param = new JSONObject();

        param.put("direction", directions.getCardinaux(direction));
        param.put("range",range);

        obj.put("action","glimpse");
        obj.put("parameters",param);
        obj.put("debug", debug);

        return obj.toString();

    }
}
