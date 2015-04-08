package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Glimpse;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 19/03/2015.
 *  Classe de test pour la classe Glimpse
 */
public class GlimpseTest {

    @Test
    public void testGlimpse() {

        //Test si l'action retourne la bonne chose
        assertEquals(Glimpse.glimpse("N", 2), "{\"action\":\"glimpse\",\"parameters\":{\"range\":2,\"direction\":\"N\"}}" );
        assertEquals(Glimpse.glimpse(0, 2, "exemple debug"), "{\"debug\":\"exemple debug\",\"action\":\"glimpse\",\"parameters\":{\"range\":2,\"direction\":\"N\"}}" );
/*        String result = "{ \"status\": \"OK\", \"cost\": 12,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [[\"MANGROVE\", 80.0], [\"BEACH\", 20.0]],\n" +
                "      [[\"MANGROVE\", 40.0], [\"TROPICAL_RAIN_FOREST\", 40.0], [\"TROPICAL_SEASONAL_FOREST\", 20.0]],\n" +
                "      [\"TROPICAL_RAIN_FOREST\", \"TROPICAL_SEASONAL_FOREST\"],\n" +
                "      [\"TROPICAL_RAIN_FOREST\"]\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        System.out.println(new JSONObject(result).getJSONObject("extras").getJSONArray("report").getJSONArray(0));*/

    }
}
