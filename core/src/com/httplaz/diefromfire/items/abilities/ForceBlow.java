package com.httplaz.diefromfire.items.abilities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.items.Item;

public class ForceBlow extends Item
{
    public ForceBlow()
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion) Resourses.getResourse("textures/items/abilities.png:0 0 16 16", TextureRegion.class), 1, 1, "Force blow", (Sound)Resourses.getResourse("sounds/items/forceBlow.mp3", Sound.class));
        super.setId(77);
        useVolume = 4.f;
        staminaCost = 90;
        ability = true;
    }

    @Override
    public boolean use(Entity e, World w)
    {
        e.addRot = 0;
        if(!super.use(e, w))
            return false;
        for (int i=0; i<w.entities.size(); i++)
        {
            Entity e1 = w.entities.get(i);
            if(e!=e1)
            {
                Vector2 power = e1.getPosition().cpy().sub(e.getPosition());
                power.scl(20.f/power.len());
                e1.kick(power);
            }
        }
        return true;
    }
}
