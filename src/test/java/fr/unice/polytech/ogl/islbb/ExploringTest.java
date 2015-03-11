package fr.unice.polytech.ogl.islbb;
import junit.framework.*;


import fr.unice.polytech.ogl.islbb.actions.Exploring;
import org.json.JSONObject;

/**
 * Created by Ulysse RICCIO on 09/03/15.
 * Classe de test pour la classe Exploring
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

