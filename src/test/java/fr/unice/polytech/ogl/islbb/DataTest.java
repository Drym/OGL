package fr.unice.polytech.ogl.islbb;

import com.atlassian.clover.reporters.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 23/04/2015.
 * Classe test de Data
 */
public class DataTest {

    Data test = new Data();

    @Test
    public void testData() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("FISH");
        assertEquals(test.getBiomeResources("OCEAN"), list);

        JSONObject result = new JSONObject();
        JSONObject extras = new JSONObject();
        JSONArray allResources = new JSONArray();
        JSONObject currentResource = new JSONObject();

        JSONArray allPOIs = new JSONArray();
        JSONObject currentPOI = new JSONObject();

        result.put("status", "OK");
        result.put("cost", 39);

        currentResource.put("resource", "WOOD");
        currentResource.put("amount", "HIGH");
        currentResource.put("cond", "EASY");

        allResources.put(currentResource);
        currentResource = new JSONObject();

        currentResource.put("resource", "FUR");
        currentResource.put("amount", "LOW");
        currentResource.put("cond", "FAIR");

        allResources.put(currentResource);
        currentResource = new JSONObject();

        currentResource.put("resource", "FLOWER");
        currentResource.put("amount", "MEDIUM");
        currentResource.put("cond", "HARSH");

        allResources.put(currentResource);

        extras.put("resources", allResources);

        currentPOI.put("kind", "CREEK");
        currentPOI.put("id", "creek_identifier_2");

        allPOIs.put(currentPOI);

        extras.put("pois", allPOIs);

        result.put("extras", extras);

    }
}
