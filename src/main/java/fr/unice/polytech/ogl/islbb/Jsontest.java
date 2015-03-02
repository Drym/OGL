package fr.unice.polytech.ogl.islbb;
import org.json.JSONObject;


/**
 * Created by user on 02/03/2015.
 */
public class Jsontest {
    public static void main(String[] args)
    {
        JSONObject obj = new JSONObject();

        System.out.print(Scoot.scoot("N")+"\n");
        System.out.println(Exit.exit());
        System.out.println(Land.land("IDDD",10));
    }
}


