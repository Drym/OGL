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
        String d = "{ \"creek\" : \"creek_id\",\"budget\" : 600 , \"men\" : 50   }";
        String e = "{ \"creek\" : \"creek_id\",\"budget\" : 600 , \"men\" : 50  ,  \"objective\" : [ { \"resource\" :\"WOOD\" , \"amount\" : 600 } ] }";

        //JSONObject test=new JSONObject();
        //test.put("creek","creed_id");
        //Init c = new Init("{ \"creek\" : \"creek_id\" , \"budget\" : 600 , \"men\" : 50 ,  \"objective\" : [ { \"resource\" :\"WOOD\" , \"amount\" : 600 } ] ");
        Init c = new Init(e);

        System.out.print(a.getobj()+"\n");
        System.out.print(b.obj+"\n");

    }
}


