package fr.unice.polytech.ogl.islbb;

import org.json.JSONObject;
import java.util.LinkedHashMap;

/**
 * Created by Lucas on 02/03/2015.
 * Classe permetant d'automatiser l'action land pour l'explorer.
 */
public final class Land extends Command {

    public static JSONObject land( String id, int people){
        JSONObject obj = new JSONObject();
        LinkedHashMap param = new LinkedHashMap();

        param.put("creek",id);
        param.put("people",people);
        obj.put("action","land");
        obj.put("parameters",param);

        return obj;
    }

}
