package fr.unice.polytech.ogl.islbb;
import fr.unice.polytech.ogl.islbb.Exit;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 09/03/2015.
 * Classe de test pour la classe exit
 */
public class ExitTest  {

    @Test public void testExit() {
        Exit test = new Exit();
        Exit test2 = new Exit();
        assertEquals(test, test2);

    }
}
