package fr.unice.polytech.ogl.islbb;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thibault OBER on 09/03/2015.
 */
public class Data {

    ArrayList<String> cardinaux;
    Map<String, List<String>> biomesResources;

    public Data() {

        this.cardinaux = new ArrayList<>();
        this.cardinaux.add("N");
        this.cardinaux.add("E");
        this.cardinaux.add("S");
        this.cardinaux.add("W");

        this.biomesResources = new HashMap<>();

        List<String> resources = new ArrayList<>();
        resources.add("FISH");
        this.biomesResources.put("OCEAN", resources);
        this.biomesResources.put("LAKE", resources);

        resources = new ArrayList<>();
        resources.add("FISH");
        resources.add("QUARTZ");
        this.biomesResources.put("BEACH", resources);

        resources = new ArrayList<>();
        resources.add("WOOD");
        resources.add("SUGAR_CANE");
        resources.add("FRUITS");
        this.biomesResources.put("TROPICAL_SEASONAL_FOREST", resources);
        this.biomesResources.put("TROPICAL_RAIN_FOREST", resources);

        resources = new ArrayList<>();
        resources.add("FUR");
        this.biomesResources.put("SHRUBLAND", resources);

        resources = new ArrayList<>();
        resources.add("FLOWER");
        this.biomesResources.put("MANGROVE", resources);


    }
    public String getCardinaux(int i){
        return this.cardinaux.get(i);
    }

    /**
     *@author Lucas Sauvage
     * @param biome
     * @return correspondance entre Biome et ressources
     */
    public List<String> getBiomeResources(String biome) {

        return this.biomesResources.get(biome);

    }

    /**
     * Construit une liste de Biome (String) par rapport à la liste de Resource (String) données.
     * La liste retournée est alors la liste des Biomes intéressants selon les ressources recherchées.
     */

    public List<String> buildWantedBiomesList(List<String> researchedResources) {

        List<String> result = new ArrayList<>();

        for (String currentResource : researchedResources) {

            for (Map.Entry<String, List<String>> currentBiome : this.biomesResources.entrySet()) {

                if (this.getBiomeResources(currentBiome.getKey()).contains(currentResource))
                    result.add(currentBiome.getKey());

            }

        }

        return result;
    }
}
