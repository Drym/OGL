package fr.unice.polytech.ogl.islbb;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thibault OBER on 09/03/2015.
 */
public class Data {

    ArrayList<String> cardinaux;
    HashMap<Integer,String>card;

    ArrayList<String> resources;
    HashMap<String, ArrayList<String>> hashMapResources;

    public Data() {
        card = new HashMap<>();
        card.put(1, "N");
        card.put(2, "N");
        card.put(3, "N");
        card.put(4, "N");
        cardinaux = new ArrayList<>();
        cardinaux.add("N");
        cardinaux.add("E");
        cardinaux.add("S");
        cardinaux.add("W");
    }
    public String getCardinaux(int i){
        return this.cardinaux.get(i);
    }

    /**
     *@author Lucas Sauvage
     * @param biome
     * @return correspondance entre Biome et ressources
     */
    public HashMap<String, ArrayList<String>> resourceFromBiome(String biome) {

        if(biome.equals("OCEAN")) {
            resources.add("FISH");
            hashMapResources.put("OCEAN", resources);

        }

        else if(biome.equals("LAKE")) {
            resources.add("FISH");
            hashMapResources.put("LAKE", resources);

        }

        else if(biome.equals("SHRUBLAND")) {
            resources.add("FUR");
            hashMapResources.put("SHRUBLAND", resources);

        }

        else if(biome.equals("TROPICAL_SEASONAL_FOREST")) {
            resources.add("WOOD");
            resources.add("SUGAR_CANE");
            resources.add("FRUITS");
            hashMapResources.put("TROPICAL_SEASONAL_FOREST", resources);

        }

        else if(biome.equals("TROPICAL_RAIN_FOREST")) {
            resources.add("WOOD");
            resources.add("SUGAR_CANE");
            resources.add("FRUITS");
            hashMapResources.put("TROPICAL_RAIN_FOREST", resources);

        }

        else if(biome.equals("BEACH")) {
            resources.add("FISH");
            hashMapResources.put("BEACH", resources);

        }

        else if(biome.equals("MANGROVE")) {
            resources.add("FLOWER");
            hashMapResources.put("MANGROVE", resources);

        }

        else return null;

        return hashMapResources;
    }
}
