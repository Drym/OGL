package fr.unice.polytech.ogl.islbb;

import junit.framework.TestCase;

/**
 * Created by Thibault OBER on 14/03/2015.
 */
public class ComportementTest extends TestCase {
    public void testComportement() {
        Comportement test = new Comportement();
        String r1 = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":-23}}";
        String r2 = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":-23}}";
        String r3 = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"WATER\"],\"altitude\":-23}}";
        String r4 = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"WOOD\"],\"altitude\":-23}}";
        //String r5="{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"WOOD\"],\"altitude\":-23}}";
        test.getScout(r2,"N");
        test.getScout(r2, "S");
        test.getScout(r3, "E");
        test.getScout(r4, "W");
        assertEquals(test.takeDirection(),"W");
        assertTrue(test.hasWood(test.takeDirection()));
    }
}
