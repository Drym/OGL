package fr.unice.polytech.ogl.islbb;
import junit.framework.*;


import fr.unice.polytech.ogl.islbb.actions.Exploring;
import org.json.JSONObject;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ulysse RICCIO on 09/03/15.
 * Classe de test pour la classe Exploring
 */
public class ExploringTest extends TestCase  {

    public void testExplore() throws Exception {
            Exploring test = new Exploring();
            JSONObject equal = new JSONObject("action , explore");
            assertTrue(!test.equals(null));
            assertEquals("Les deux objects sont identique", test, equal);
            }

    public void testRetrive()
    {

    }

}

