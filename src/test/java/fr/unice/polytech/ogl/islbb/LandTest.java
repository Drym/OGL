package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Land;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 09/03/2015.
 * Classe de test pour la classe Land
 */
public class LandTest {


    @Test public void testLand() {

        //Test si l'action retourne la bonne chose
        assertEquals(Land.land("IDazed3", 1), "{\"action\":\"land\",\"parameters\":{\"creek\":\"IDazed3\",\"people\":1}}" );
        assertEquals(Land.land("IDazed3", 1, "exemple debug"), "{\"debug\":\"exemple debug\",\"action\":\"land\",\"parameters\":{\"creek\":\"IDazed3\",\"people\":1}}" );
    }

}
