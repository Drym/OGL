package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.reports.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import java.util.Map;

/**
 * Created by user on 08/04/2015.
 */
public class IslandMapTest  {
    Biome ocean = new Biome("OCEAN", 90);
    Biome ocean2 = new Biome("OCEAN", 50);
    Biome ocean3 = new Biome("OCEAN", 100);
    Biome shrubland = new Biome("SHRUBLAND", 50);
    Biome shrubland2 = new Biome("SHRUBLAND", 10);
    Biome mangrove = new Biome("MANGROVE", 40);
    Resource wood=new  Resource("WOOD");
    Resource fish=new Resource("FISH");
    Resource fur=new Resource("FUR");
    private Map<String, IslandTile> islandMap;
    private Data directions;
    private IslandMap map;
    String n="0-1";
    String e="1-0";
    String s="0--1";
    String w="-1-0";

    @Test
    public void testscout(){
        map=new IslandMap();
        List<Resource> list=new ArrayList<Resource>();
        list.add(wood);
        list.add(fish);
        list.add(fur);
        IslandTile t=new IslandTile(10,list,true);
        IslandTile t1=new IslandTile(12,list,true);
        IslandTile t2=new IslandTile(15,list,true);
        IslandTile t3=new IslandTile(18,list,true);
        map.addTile(0,1,t);
        map.addTile(1,0,t);
        map.addTile(0,-1,t);
        map.addTile(-1, 0, t);
        assertEquals(map.getInformation(0,1).getResources().size(),3);
        //System.out.println(map.getInformation(0,1).getResources().get(0).getType());
        assertTrue(map.isRegistered(0,-1));
        assertTrue(map.isAlreadyScouted(-1, 0));
    }
    public void testGlimpse(){
        map=new IslandMap();
        List<Biome>list=new ArrayList<Biome>();
        list.add(ocean3);
        IslandTile test=new IslandTile(list,1);
        map.addTile(0,1,test);
        assertTrue(map.isWater(0,1));
        List<Biome>list2=new ArrayList<Biome>();
        list2.add(shrubland);
        list2.add(ocean2);
        IslandTile test2=new IslandTile(list2,1);
        assertTrue(map.isRegistered(0,-1));
    }
}

