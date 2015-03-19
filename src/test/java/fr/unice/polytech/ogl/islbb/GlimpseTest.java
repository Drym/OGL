package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Glimpse;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 19/03/2015.
 *  Classe de test pour la classe Glimpse
 */
public class GlimpseTest {

    @Test
    public void testGlimpse() {

        assertEquals(Glimpse.glimpse("N", 2), "{\"action\":\"glimpse\",\"parameters\":{\"range\":2,\"direction\":\"N\"}}" );

    }
}
