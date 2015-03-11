package fr.unice.polytech.ogl.islbb;
import fr.unice.polytech.ogl.islbb.actions.Explore;
import junit.framework.*;


import org.json.JSONObject;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ulysse RICCIO on 09/03/15.
 * Classe de test pour la classe Explore
 */
public class ExploreTest extends TestCase  {

    public void testExplore() throws Exception {
            Explore test = new Explore();
            JSONObject equal = new JSONObject("action , explore");
            assertTrue(!test.equals(null));
            assertEquals("Les deux objects sont identique", test, equal);
            }

    public void testRetrive()
    {

    }

}

