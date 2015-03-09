package fr.unice.polytech.ogl.islbb;
import junit.framework.*;


import main.java.fr.unice.polytech.ogl.islbb.Exploring;
import org.json.JSONObject;

/**
 * Created by Ulysse RICCIO on 09/03/15.
 */
public class ExploringTest extends TestCase  {

    public void testExplore() throws Exception {
            Exploring test = new Exploring();
            JSONObject equal = new JSONObject("action , explore");
            assertEquals("Les deux objects sont identique", test, equal);
            }

    public void testRetrive()
    {

    }

}

