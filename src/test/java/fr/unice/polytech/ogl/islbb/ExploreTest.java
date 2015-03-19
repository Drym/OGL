package fr.unice.polytech.ogl.islbb;
import fr.unice.polytech.ogl.islbb.actions.Explore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Ulysse RICCIO on 09/03/15.
 * Classe de test pour la classe Explore
 */
public class ExploreTest   {

    @Test public void ExploreTest(){
        assertEquals(Explore.explore(), "{\"action\":\"explore\"}");

    }
}

