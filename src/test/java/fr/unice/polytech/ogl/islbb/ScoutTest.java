package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Land;
import fr.unice.polytech.ogl.islbb.actions.Move;
import fr.unice.polytech.ogl.islbb.actions.Scout;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 09/03/2015.
 * Classe de test pour la classe Scout
 */
public class ScoutTest {


    @Test public void testScout() {

        assertEquals(Scout.scout("N"), "{\"action\":\"scout\",\"parameters\":{\"direction\":\"N\"}}" );

    }

}
