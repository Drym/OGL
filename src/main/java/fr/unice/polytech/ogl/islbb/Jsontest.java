package fr.unice.polytech.ogl.islbb;
import org.json.JSONObject;


/**
 * Created by user on 02/03/2015.
 */
public class Jsontest {
    public static void main(String[] args)
    {
        JSONObject obj = new JSONObject();
        Scoot a = new Scoot("N");
        obj.put("name", "foo");
        obj.put("num", new Integer(100));
        obj.put("balance", new Double(1000.21));
        obj.put("is_vip", new Boolean(true));

        System.out.print(a.obj);
    }
}


