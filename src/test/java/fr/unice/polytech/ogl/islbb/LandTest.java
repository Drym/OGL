package fr.unice.polytech.ogl.islbb;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 09/03/2015.
 * Classe de test pour la classe Land
 */
public class LandTest {


    @Test public void testLand() {
        Land test = new Land();
        Land test2 = new Land();
        assertEquals(test.land("IDazed3",1), test2.land("IDazed3",1));

    }

}
