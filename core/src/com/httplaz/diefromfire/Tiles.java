package com.httplaz.diefromfire;

import com.badlogic.gdx.utils.Null;

import java.util.HashMap;

public abstract class Tiles
{
    public static HashMap<String, Tile> content = new HashMap<>();
    public static HashMap<Integer, Tile> contentI = new HashMap<>();

    public static void addTile(int x, int y, int p, int v, String n)
    {
        Tile t = new Tile(x,y,p,v,n, content.size());
        content.put(n, t);
        contentI.put(t.id, t);
        //System.out.println(content.size());
    }

    public static Tile getTile(String name)
    {
        return content.get(name);
    }

    public static Tile getTile(int id)
    {
        if(!contentI.containsKey(id))
            System.out.println(id);
        return contentI.get(id);
    }
}
