package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.reports.Resource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 13/04/2015.
 */
public class ResourceTest {

    @Test public void testResource() {

        Resource r = new Resource("WOOD", "LOW", "FAIR");

        assertEquals(r.getAmount(), "LOW");
        assertEquals(r.getCondition(), "FAIR");
        assertEquals(r.getType(), "WOOD");

        r.setAmount("MEDIUM");
        r.setCondition("HARSH");
        r.setType("FISH");

        assertEquals(r.getAmount(), "MEDIUM");
        assertEquals(r.getCondition(), "HARSH");
        assertEquals(r.getType(), "FISH");

    }


}
