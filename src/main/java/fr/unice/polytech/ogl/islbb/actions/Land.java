package fr.unice.polytech.ogl.islbb.actions;

import org.json.JSONObject;
import java.util.LinkedHashMap;

/**
 * Created by Lucas on 02/03/2015.
 * Classe permetant d'automatiser l'action land pour l'explorer.
 */
public final class Land {

    public static String land( String id, int people){
        JSONObject obj = new JSONObject();
        LinkedHashMap param = new LinkedHashMap();

        param.put("creek",id);
        param.put("people",people);
        obj.put("action","land");
        obj.put("parameters",param);

        return obj.toString();

    }

    public static String land( String id, int people, String debug){
        JSONObject obj = new JSONObject();
        LinkedHashMap param = new LinkedHashMap();

        param.put("creek",id);
        param.put("people",people);
        obj.put("action","land");
        obj.put("parameters",param);
        obj.put("debug", debug);

        return obj.toString();

    }

}
