package fr.unice.polytech.ogl.islbb;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 23/04/2015.
 */
public class DataTest {

    Data test = new Data();

    @Test
    public void testData() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("FISH");

        assertEquals(test.getBiomeResources("OCEAN"), list);

    }

}
