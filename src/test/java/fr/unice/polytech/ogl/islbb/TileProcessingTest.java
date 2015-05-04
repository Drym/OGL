package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.data.TileProcessing;
import fr.unice.polytech.ogl.islbb.reports.*;

import static org.junit.Assert.assertEquals;

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
    ArrayList<Objective> listObjective = new ArrayList<>();
    ArrayList<Objective> listObjective2 = new ArrayList<>();
    ArrayList<Resource> listResources = new ArrayList<>();
    List<Biome> listBiomes= new ArrayList<>();

    /**
     * Test la méthode scoutTile
     */
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

        testResult.setAltitude(10);
        assertEquals(testResult.getAltitude(), 10);
        testResult.setAlreadyScouted(false);
        assertEquals(testResult.isAlreadyScouted(), false);

        testResult.setAlreadyExplored(true);
        assertEquals(testResult.isAlreadyExplored(), true);
        testResult.setAlreadyGlimpsed(true);
        assertEquals(testResult.isAlreadyGlimpsed(), true);

        assertEquals(testResult.isUnreachable(), false);

        testResult.scoutTile(10, listResources, true);
        assertEquals(testResult.getAltitude(), 10);
        assertEquals(testResult.isUnreachable(), true);
        assertEquals(testResult.isAlreadyScouted(), true);
    }

    /**
     * Test la méthode exploretTile
     */
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

        assertEquals(testResult.hasResource("WOOD").getType(), "WOOD");
        assertEquals(testResult.hasResource(null), null);
        assertEquals(testResult.hasOnlyResource("WOOD"), true);
        assertEquals(testResult.hasOnlyResource(null), false);

        Objective wood = new Objective("WOOD", 300);
        Objective fish = new Objective("FISH", 300);
        listObjective.add(wood);
        listObjective2.add(fish);
        assertEquals(testResult.hasOnlyResources(listObjective), true);
        assertEquals(testResult.hasOnlyResources(listObjective2), false);
        assertEquals(testResult.hasResources(listObjective).get(0).getType(), "WOOD");

        testResult.setAlreadyExplored(false);
        assertEquals(testResult.hasResources(listObjective), null);

        testResult.exploreTile(listResources, null);
    }


    /**
     * Test la méthode glimpseTile, cas distance : 1
     */
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

        assertEquals(testResultList.get(0).isAlreadyGlimpsed(), true);
        assertEquals(testResultList.get(0).containBiome(null), null);

        testResultList.get(0).setAlreadyGlimpsed(false);
        Biome biome = new Biome("biome");
        listBiomes.add(biome);
        testResultList.get(0).glimpseTile(listBiomes, 1);
        assertEquals(testResultList.get(0).isAlreadyGlimpsed(), true);

    }


    /**
     * Test la méthode glimpseTile, cas distance : 2
     */
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

    /**
     * Test la méthode glimpseTile, cas distance : 3
     */
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

    /**
     * Test la méthode glimpseTile, cas distance : 4
     */
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
