package com.httplaz.diefromfire.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.Tiles;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.entities.mobs.Entity;

public class TileItem extends Item
{

    public TileItem(int id)
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion)Resourses.getResourse("textures/tiles/tileset.png:"+Integer.toString(Tiles.getTile(id).pX*16) + " 0 16 16", TextureRegion.class), 64, 64, Tiles.getTile(id).name,  null);
    }

    @Override
    public boolean use(Entity e, World w)
    {
        if(!super.use(e, w))
            return false;
        //System.out.println("vfdfefef");
        //System.out.println(id);
        //System.out.println(e.target);
        w.setTile((int)(e.target.x-w.getTileOffset().x), (int)(e.target.y-w.getTileOffset().y), id);
        return true;
    }
}
