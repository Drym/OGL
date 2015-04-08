package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.reports.Biome;
import fr.unice.polytech.ogl.islbb.reports.IslandTile;
import fr.unice.polytech.ogl.islbb.reports.Objective;
import fr.unice.polytech.ogl.islbb.reports.Resource;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 08/04/2015.
 */
public class IslandTileTest {
    Biome ocean = new Biome("OCEAN", 90);
    Biome shrubland = new Biome("SHRUBLAND", 30);
    Biome mangrove = new Biome("MANGROVE", 40);
    Resource wood=new  Resource("WOOD");
    Resource fish=new Resource("FISH");
    Resource fur=new Resource("FUR");
    Initialization init;


    @Test
    public void testGlimpse(){
        List<Biome>list=new ArrayList<Biome>();
        list.add(ocean);
        list.add(shrubland);
        IslandTile test=new IslandTile(list,2);
        assertEquals(90,test.getOceanPart());
        assertTrue(test.isWater());
        assertEquals(test.containBiome("OCEAN").getBiome(), "OCEAN");
        assertEquals(test.getOceanPart(),90);
    }



    @Test
    public void testScout(){
        init =new Initialization("{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}");
        List<Objective>objectives=Objective.buildObjectives(init.getResources(), init.getAmounts());
        List<Resource> list=new ArrayList<Resource>();
        list.add(wood);
        list.add(fish);
        list.add(fur);
        IslandTile test=new IslandTile(10,list,true);
        assertEquals(test.hasResources(objectives).get(0).getType(), "WOOD");
        assertEquals(test.hasResources(objectives).get(1).getType(),"FISH");
        assertEquals(test.hasResources(objectives).size(),2);
        test.removeResource("FISH");
        assertEquals(test.getResources().size(),2);
        test.removeResource("FUR");
        assertEquals(test.getResources().size(),1);
    }
}