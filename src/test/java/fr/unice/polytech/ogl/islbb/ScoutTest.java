package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Scout;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 09/03/2015.
 * Classe de test pour la classe Scout
 */
public class ScoutTest {


    @Test public void testScout() {

        //Test si l'action retourne la bonne chose
        assertEquals(Scout.scout("N"), "{\"action\":\"scout\",\"parameters\":{\"direction\":\"N\"}}" );
        assertEquals(Scout.scout(0), "{\"action\":\"scout\",\"parameters\":{\"direction\":\"N\"}}" );
        assertEquals(Scout.scout(0, "exemple debug"), "{\"debug\":\"exemple debug\",\"action\":\"scout\",\"parameters\":{\"direction\":\"N\"}}" );

    }

}
