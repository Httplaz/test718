package com.httplaz.diefromfire.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.entities.mobs.Entity;

public class Flamethrower extends Item
{
    public Flamethrower()
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion) Resourses.getResourse("textures/items/tools.png:16 0 16 16", TextureRegion.class), 1, 1, "Pickaxe", null);
        super.setId(77);
    }

    @Override
    public boolean use(Entity e, World w)
    {
        if(!super.use(e, w))
            return false;
        //w.breakTile((int)e.target.x, (int)e.target.y);
        //System.out.println("doing");
        //w.setTile((int)e.target.x, (int)e.target.y, 0);
        return true;
    }
}
