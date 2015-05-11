package fr.unice.polytech.ogl.islbb;


import fr.unice.polytech.ogl.islbb.actions.Transform;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ulysse on 11/05/15.
 */
public class TransformTest {

    @Test
    public void testTransform() {

        //Test si l'action retourne la bonne chose
        assertEquals(Transform.transform("WOOD", 10), "{\"action\":\"transform\",\"parameters\":{\"WOOD\":\"10\"}}");
        assertEquals(Transform.transform("SUGAR_CANE", 100 , "FRUIT" , 10 ), "{\"action\":\"transform\",\"parameters\":{\"SUGAR_CANE\":\"100\",\"FRUIT\":\"10\"}}");
        assertEquals(Transform.transform("SUGAR_CANE", 100 , "FRUIT" , 10 , "exemple debug" ), "{\"debug\":\"exemple debug\",\"action\":\"transform\",\"parameters\":{\"SUGAR_CANE\":\"100\",\"FRUIT\":\"10\"}}");

    }
}
