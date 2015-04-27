package fr.unice.polytech.ogl.islbb;

import com.atlassian.clover.reporters.json.JSONObject;
import fr.unice.polytech.ogl.islbb.reports.Resource;
import fr.unice.polytech.ogl.islbb.data.TileProcessing;
import fr.unice.polytech.ogl.islbb.reports.IslandTile;
import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Lucas on 26/04/2015.
 * Classe test de TileProcessing
 */
public class TileProcessingTest {

    IslandTile testResult;
    List<IslandTile> testResultList;

    @Test
    public void testTileProcessingScout() {

        /* Initialisation */
        ArrayList<String> list = new ArrayList<>();
        list.add("FLOWER");
        list.add("FISH");

        org.json.JSONObject obj = new org.json.JSONObject();
        LinkedHashMap extras = new LinkedHashMap();

        obj.put("status", "OK");
        obj.put("cost", "576");

        extras.put("resources", list);
        extras.put("altitude", 23);
        obj.put("extras", extras);

        testResult = TileProcessing.scoutTile(obj, 1);

        /* Test */

        assertEquals(testResult.getAltitude(), 24);
        assertEquals(testResult.getResources().get(0).getType(), "FLOWER");
        assertEquals(testResult.getResources().get(1).getType(), "FISH");

        assertEquals(testResult.isAlreadyScouted(), true);


    }

    @Test
    public void testTileProcessingExplore() {

         /* Initialisation */

        org.json.JSONObject result = new  org.json.JSONObject();
        org.json.JSONObject extras = new  org.json.JSONObject();
        org.json.JSONArray allResources = new  org.json.JSONArray();
        org.json.JSONObject currentResource = new  org.json.JSONObject();

        org.json.JSONArray allPOIs = new  org.json.JSONArray();
        org.json.JSONObject currentPOI = new  org.json.JSONObject();

        result.put("status", "OK");
        result.put("cost", 39);

        currentResource.put("resource", "WOOD");
        currentResource.put("amount", "HIGH");
        currentResource.put("cond", "EASY");

        allResources.put(currentResource);

        extras.put("resources", allResources);

        currentPOI.put("kind", "CREEK");
        currentPOI.put("id", "creek_identifier_2");

        allPOIs.put(currentPOI);

        extras.put("pois", allPOIs);
        result.put("extras", extras);

        testResult = TileProcessing.exploreTile(result);

        /* Test */

        assertEquals(testResult.getResources().get(0).getType(), "WOOD");
        assertEquals(testResult.getResources().get(0).getAmount(), "HIGH");
        assertEquals(testResult.getResources().get(0).getCondition(), "EASY");
        assertEquals(testResult.isAlreadyExplored(), true);

    }


    @Test public void testTileProcessingGlimpse1() {

         /* Initialisation */

        org.json.JSONObject result = new  org.json.JSONObject();
        org.json.JSONObject extras = new  org.json.JSONObject();
        org.json.JSONArray allResources = new  org.json.JSONArray();
        org.json.JSONArray currentResource = new  org.json.JSONArray();
        org.json.JSONArray currentResource2 = new  org.json.JSONArray();
        org.json.JSONArray currentResource3 = new  org.json.JSONArray();
        org.json.JSONArray allOfAllResources = new  org.json.JSONArray();

        result.put("status", "OK");
        result.put("cost", 39);

        currentResource.put("MANGROVE");
        currentResource.put(20.00);

        currentResource2.put("BEACH");
        currentResource2.put(60.00);

        currentResource3.put("MANGROVE");
        currentResource3.put(20.00);

        allResources.put(currentResource);
        allResources.put(currentResource2);
        allResources.put(currentResource3);

        allOfAllResources.put(allResources);

        extras.put("asked_range", 1);
        extras.put("report", allOfAllResources);

        result.put("extras", extras);

        testResultList = TileProcessing.glimpseTile(result);

        /* Test */

        assertEquals(testResultList.get(0).getBiomes().get(0).getBiome(), "MANGROVE");
        assertEquals(testResultList.get(0).getBiomes().get(0).getPercentage(), 40.00, 1);
        assertEquals(testResultList.get(0).getBiomes().get(1).getBiome(), "BEACH");
        assertEquals(testResultList.get(0).getBiomes().get(1).getPercentage(), 60.00, 1);


    }

    @Test public void testTileProcessingGlimpse2() {

         /* Initialisation */

        org.json.JSONObject result = new  org.json.JSONObject();
        org.json.JSONObject extras = new  org.json.JSONObject();
        org.json.JSONArray allResources = new  org.json.JSONArray();
        org.json.JSONArray currentResource = new  org.json.JSONArray();
        org.json.JSONArray currentResource2 = new  org.json.JSONArray();
        org.json.JSONArray allOfAllResources = new  org.json.JSONArray();

        result.put("status", "OK");
        result.put("cost", 39);

        currentResource.put("MANGROVE");
        currentResource.put(80.00);

        currentResource2.put("BEACH");
        currentResource2.put(20.00);

        allResources.put(currentResource);
        allResources.put(currentResource2);

        allOfAllResources.put(allResources);
        allOfAllResources.put(allResources);

        extras.put("asked_range", 2);
        extras.put("report", allOfAllResources);

        result.put("extras", extras);

        testResultList = TileProcessing.glimpseTile(result);

        /* Test */

        assertEquals(testResultList.get(0).getBiomes().get(0).getBiome(), "MANGROVE");
        assertEquals(testResultList.get(0).getBiomes().get(0).getPercentage(), 80.00, 1);
        assertEquals(testResultList.get(0).getBiomes().get(1).getBiome(), "BEACH");
        assertEquals(testResultList.get(0).getBiomes().get(1).getPercentage(), 20.00, 1);
        assertEquals(testResultList.get(1).getBiomes().get(0).getBiome(), "MANGROVE");
        assertEquals(testResultList.get(1).getBiomes().get(0).getPercentage(), 80.00, 1);
        assertEquals(testResultList.get(1).getBiomes().get(1).getBiome(), "BEACH");
        assertEquals(testResultList.get(1).getBiomes().get(1).getPercentage(), 20.00, 1);

    }

    @Test public void testTileProcessingGlimpse3() {

         /* Initialisation */

        org.json.JSONObject result = new  org.json.JSONObject();
        org.json.JSONObject extras = new  org.json.JSONObject();
        org.json.JSONArray allResources = new  org.json.JSONArray();
        org.json.JSONArray currentResource = new  org.json.JSONArray();
        org.json.JSONArray currentResource2 = new  org.json.JSONArray();
        org.json.JSONArray currentResource3 = new  org.json.JSONArray();
        org.json.JSONArray allOfAllResources = new  org.json.JSONArray();

        result.put("status", "OK");
        result.put("cost", 39);

        currentResource.put("MANGROVE");
        currentResource.put(80.00);

        currentResource2.put("BEACH");
        currentResource2.put(20.00);

        currentResource3.put("OTHER");
        currentResource3.put("TROPICAL_RAIN_FOREST");

        allResources.put(currentResource);
        allResources.put(currentResource2);

        allOfAllResources.put(allResources);
        allOfAllResources.put(allResources);
        allOfAllResources.put(currentResource3);

        extras.put("asked_range", 3);
        extras.put("report", allOfAllResources);

        result.put("extras", extras);

        testResultList = TileProcessing.glimpseTile(result);

        /* Test */

        assertEquals(testResultList.get(0).getBiomes().get(0).getBiome(), "MANGROVE");
        assertEquals(testResultList.get(0).getBiomes().get(0).getPercentage(), 80.00, 1);
        assertEquals(testResultList.get(0).getBiomes().get(1).getBiome(), "BEACH");
        assertEquals(testResultList.get(0).getBiomes().get(1).getPercentage(), 20.00, 1);

        assertEquals(testResultList.get(1).getBiomes().get(0).getBiome(), "MANGROVE");
        assertEquals(testResultList.get(1).getBiomes().get(0).getPercentage(), 80.00, 1);
        assertEquals(testResultList.get(1).getBiomes().get(1).getBiome(), "BEACH");
        assertEquals(testResultList.get(1).getBiomes().get(1).getPercentage(), 20.00, 1);

        assertEquals(testResultList.get(2).getBiomes().get(0).getBiome(), "OTHER");
        assertEquals(testResultList.get(2).getBiomes().get(1).getBiome(), "TROPICAL_RAIN_FOREST");

    }

    @Test public void testTileProcessingGlimpse4() {

         /* Initialisation */

        org.json.JSONObject result = new  org.json.JSONObject();
        org.json.JSONObject extras = new  org.json.JSONObject();
        org.json.JSONArray allResources = new  org.json.JSONArray();
        org.json.JSONArray currentResource = new  org.json.JSONArray();
        org.json.JSONArray currentResource2 = new  org.json.JSONArray();
        org.json.JSONArray currentResource3 = new  org.json.JSONArray();
        org.json.JSONArray currentResource4 = new  org.json.JSONArray();
        org.json.JSONArray allOfAllResources = new  org.json.JSONArray();

        result.put("status", "OK");
        result.put("cost", 39);

        currentResource.put("MANGROVE");
        currentResource.put(80.00);

        currentResource2.put("BEACH");
        currentResource2.put(20.00);

        currentResource3.put("TROPICAL_RAIN_FOREST");
        currentResource3.put("OTHER");

        currentResource4.put("TROPICAL_RAIN_FOREST");


        allResources.put(currentResource);
        allResources.put(currentResource2);

        allOfAllResources.put(allResources);
        allOfAllResources.put(allResources);
        allOfAllResources.put(currentResource3);
        allOfAllResources.put(currentResource4);

        extras.put("asked_range", 4);
        extras.put("report", allOfAllResources);

        result.put("extras", extras);

        testResultList = TileProcessing.glimpseTile(result);

        /* Test */

        assertEquals(testResultList.get(0).getBiomes().get(0).getBiome(), "MANGROVE");
        assertEquals(testResultList.get(0).getBiomes().get(0).getPercentage(), 80.00, 1);
        assertEquals(testResultList.get(0).getBiomes().get(1).getBiome(), "BEACH");
        assertEquals(testResultList.get(0).getBiomes().get(1).getPercentage(), 20.00, 1);

        assertEquals(testResultList.get(1).getBiomes().get(0).getBiome(), "MANGROVE");
        assertEquals(testResultList.get(1).getBiomes().get(0).getPercentage(), 80.00, 1);
        assertEquals(testResultList.get(1).getBiomes().get(1).getBiome(), "BEACH");
        assertEquals(testResultList.get(1).getBiomes().get(1).getPercentage(), 20.00, 1);

        assertEquals(testResultList.get(2).getBiomes().get(0).getBiome(), "OTHER");
        assertEquals(testResultList.get(2).getBiomes().get(1).getBiome(), "TROPICAL_RAIN_FOREST");

        assertEquals(testResultList.get(3).getBiomes().get(0).getBiome(), "TROPICAL_RAIN_FOREST");

    }


}
