package fr.unice.polytech.ogl.islbb.actions;
import fr.unice.polytech.ogl.islbb.Command;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * Created by Thibault on 02/03/2015.
 * Classe permetant d'automatiser l'action scout pour l'explorer.
 */
public final class Scout extends Command {

    public static String scout(String dir) {
        JSONObject obj = new JSONObject();
        LinkedHashMap par = new LinkedHashMap();
        par.put("direction",dir);
        obj.put("action", "scoot");
        obj.put("parameters",par);
        return obj.toString();
    }

}
