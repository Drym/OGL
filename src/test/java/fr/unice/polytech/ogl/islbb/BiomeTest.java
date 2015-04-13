package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.reports.Biome;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 13/04/2015.
 */
public class BiomeTest {

    private float nb = 16;

    @Test public void biomeExit() {

        Biome b = new Biome("BEACH", nb);

        assertEquals(b.getBiome(), "BEACH");
        //0 étant le "delta", donc ici delta nul
        assertEquals(b.getPercentage(), nb, 0);

        b.setBiome("MANGROVE");
        b.setPercentage(20);

        assertEquals(b.getPercentage(), 20, 0);
        assertEquals(b.getBiome(), "MANGROVE");
    }
}
