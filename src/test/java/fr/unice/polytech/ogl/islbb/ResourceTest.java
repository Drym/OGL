package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.reports.Resource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 13/04/2015.
 * Classe test de Resource
 */
public class ResourceTest {

    @Test public void testResource() {

        //Initialisation
        Resource r = new Resource("WOOD", "LOW", "FAIR");

        //Tests
        assertEquals(r.getAmount(), "LOW");
        assertEquals(r.getCondition(), "FAIR");
        assertEquals(r.getType(), "WOOD");

        //Set
        r.setAmount("MEDIUM");
        r.setCondition("HARSH");
        r.setType("FISH");

        //Test des set
        assertEquals(r.getAmount(), "MEDIUM");
        assertEquals(r.getCondition(), "HARSH");
        assertEquals(r.getType(), "FISH");

    }


}
