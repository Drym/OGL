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

        Exit b = new Exit();

        System.out.print(a.getobj()+"\n");
        System.out.print(b.obj+"\n");
    }
}


