package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Exit;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 09/03/2015.
 * Classe de test pour la classe Exit
 */
public class ExitTest  {

    @Test public void testExit() {

       assertEquals(Exit.exit(), "{\"action\":\"stop\"}" );

    }
}
