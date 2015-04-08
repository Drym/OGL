package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Move;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 09/03/2015.
 * Classe de test pour la classe Move
 */
public class MoveTest {


    @Test public void testMove() {

        //Test si l'action retourne la bonne chose
        assertEquals(Move.move("N"), "{\"action\":\"move_to\",\"parameters\":{\"direction\":\"N\"}}" );
        assertEquals(Move.move(0, "exemple debug"), "{\"debug\":\"exemple debug\",\"action\":\"move_to\",\"parameters\":{\"direction\":\"N\"}}" );
    }

}
