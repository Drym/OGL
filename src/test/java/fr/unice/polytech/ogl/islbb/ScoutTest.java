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
        Scout test = new Scout();
        Scout test2 = new Scout();
        assertEquals(test.scout("N"), test2.scout("N"));

    }

}
