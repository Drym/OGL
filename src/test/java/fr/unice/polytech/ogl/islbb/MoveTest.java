package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Move;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 09/03/2015.
 * Classe de test pour la classe Move
 */
public class MoveTest {


    @Test public void testLand() {
        Move test = new Move();
        Move test2 = new Move();
        assertEquals(test.move("N"), test2.move("N"));

    }

}
