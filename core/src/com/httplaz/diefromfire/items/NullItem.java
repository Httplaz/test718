package com.httplaz.diefromfire.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.entities.mobs.Entity;

public class NullItem extends Item
{
    public NullItem()
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion) Resourses.getResourse("textures/items/tools.png:0 0 1 1", TextureRegion.class), 1, 1, "Null", null);
        super.setId(77);
    }

    @Override
    public boolean use(Entity e, World w)
    {
        return false;
    }
}