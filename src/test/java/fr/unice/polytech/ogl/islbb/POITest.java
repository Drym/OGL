package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.reports.POI;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 13/04/2015.
 * Classe test de POI
 */
public class POITest {

    @Test public void POIExit() {
        //Initalisation
        POI p = new POI("creek", "9166ed94-1fd6-4a31-9480-cac7f75d7922");

        //Tests
        assertEquals(p.getType(),"creek");
        assertEquals(p.getId(), "9166ed94-1fd6-4a31-9480-cac7f75d7922");

        //Set
        p.setId("id");
        p.setType("type");

        //Tests des set
        assertEquals(p.getType(), "type");
        assertEquals(p.getId(), "id");

    }
}
