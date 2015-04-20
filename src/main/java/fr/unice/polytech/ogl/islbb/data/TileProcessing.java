package fr.unice.polytech.ogl.islbb.data;

import fr.unice.polytech.ogl.islbb.reports.Biome;
import fr.unice.polytech.ogl.islbb.reports.IslandTile;
import fr.unice.polytech.ogl.islbb.reports.POI;
import fr.unice.polytech.ogl.islbb.reports.Resource;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileProcessing {

    public static IslandTile exploreTile(JSONObject tileInformation) {

        // On créer la liste des ressources disponibles sur la case.
        // Avec le type, la quantité et l'exploitabilité.
        List<Resource> tileResources = new ArrayList<>();
        JSONArray resourcesJSONArray = tileInformation.getJSONObject("extras").getJSONArray("resources");

        for (int i = 0; i < resourcesJSONArray.length(); i++) {
            JSONObject resourceCondition = resourcesJSONArray.getJSONObject(i);
            tileResources.add(new Resource(resourceCondition.getString("resource"), resourceCondition.getString("amount"), resourceCondition.getString("cond")));
        }

        // On créer la liste des points d'intérêts disponibles sur la case.
        // Avec le type, et l'ID.
        List<POI> tilePOIs = new ArrayList<>();
        JSONArray POIsJSONArray = tileInformation.getJSONObject("extras").getJSONArray("pois");

        for (int i = 0; i < POIsJSONArray.length(); i++) {
            JSONObject POIInformation = POIsJSONArray.getJSONObject(i);
            tilePOIs.add(new POI(POIInformation.getString("kind"), POIInformation.getString("id")));
        }

        return new IslandTile(tileResources, tilePOIs);

    }

    public static IslandTile scoutTile(JSONObject tileInformation, int currentAltitude) {

        // On créer la liste des ressources disponibles sur la case.
        List<Resource> tileResources = new ArrayList<>();
        JSONArray resourcesJSONArray = tileInformation.getJSONObject("extras").getJSONArray("resources");

        for (int i = 0; i < resourcesJSONArray.length(); i++) {
            tileResources.add(new Resource(resourcesJSONArray.getString(i)));
        }

        boolean unreachableTile = tileInformation.getJSONObject("extras").has("unreachable");

        return new IslandTile(currentAltitude + tileInformation.getJSONObject("extras").getInt("altitude"), tileResources, unreachableTile);

    }

    public static List<IslandTile> glimpseTile(JSONObject tilesInformation) {

        List<IslandTile> results = new ArrayList<>();

        JSONArray biomesJSONArray = tilesInformation.getJSONObject("extras").getJSONArray("report");

        for (int i = 0; i < biomesJSONArray.length(); i++) {

            JSONArray tileBiomesJSONArray = biomesJSONArray.getJSONArray(i);
            Map<String, Integer> biomesMap = new HashMap<>();
            List<Biome> tileBiomes = new ArrayList<>();

            if (i < 2) {
                for (int j = 0; j < tileBiomesJSONArray.length(); j++) {
                    if (biomesMap.containsKey(tileBiomesJSONArray.getJSONArray(j).getString(0))) {
                        biomesMap.put(tileBiomesJSONArray.getJSONArray(j).getString(0), biomesMap.get(tileBiomesJSONArray.getJSONArray(j).getString(0)) + tileBiomesJSONArray.getJSONArray(j).getInt(1));
                    } else {
                        biomesMap.put(tileBiomesJSONArray.getJSONArray(j).getString(0), tileBiomesJSONArray.getJSONArray(j).getInt(1));
                    }
                }
            }
            else if (i == 2) {
                for (int j = 0; j < tileBiomesJSONArray.length(); j++) {
                    biomesMap.put(tileBiomesJSONArray.getString(j), -1);
                }
            }
            else {
                biomesMap.put(tileBiomesJSONArray.getString(0), -1);
            }


            for (Map.Entry<String, Integer> entry : biomesMap.entrySet()) {
                tileBiomes.add(new Biome(entry.getKey(), entry.getValue()));
            }

            results.add(new IslandTile(tileBiomes, i));

        }

        return results;

    }


}
