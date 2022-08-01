package com.httplaz.diefromfire.items.abilities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.*;
import com.httplaz.diefromfire.entities.interactives.Interactive;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.items.Item;

public class Teleporter extends Item
{
    public Teleporter()
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion) Resourses.getResourse("textures/items/abilities.png:64 0 16 16", TextureRegion.class), 1, 1, "teleporter", (Sound)Resourses.getResourse("sounds/items/plasmaBeam.mp3", Sound.class));
        super.setId(77);
        //useVolume = 3.f;
        staminaCost = 99;
        ability = true;
    }

    @Override
    public boolean use(Entity e, World w)
    {
        e.addRot = 0;
        if(!super.use(e, w))
            return false;
        Vector2 a = e.target.cpy().sub(w.getTileOffset());
        float cR = e.collisionRadius;
        Vector2 r1 = new Vector2(a.x-cR, a.y-cR);
        Vector2 r2 = new Vector2(a.x+cR, a.y+cR);

        if(r1.x<=0 || r1.y<=0 || r2.x>=w.getTileWidth() || r2.y>=w.getTileHeight())
            return false;

        for (float x=r1.x; x<=r2.x; x++)
            for (float y=r1.y; y<=r2.y; y++)
                if(Tiles.getTile(w.getTile((int)x, (int)y)).priority>=3)
                {
                    //world.breakTile((int) x, (int) y);
                    return false;
                }
        e.position.set(e.target);
        return true;
    }
}
