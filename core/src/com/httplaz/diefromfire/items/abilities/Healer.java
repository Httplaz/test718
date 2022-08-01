package com.httplaz.diefromfire.items.abilities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.items.Item;

public class Healer extends Item
{
    public Healer()
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion) Resourses.getResourse("textures/items/abilities.png:32 0 16 16", TextureRegion.class), 1, 1, "healer", (Sound)Resourses.getResourse("sounds/items/forceBlow.mp3", Sound.class));
        super.setId(77);
        useVolume = 4.f;
        staminaCost = 80;
        ability = true;
    }

    @Override
    public boolean use(Entity e, World w)
    {
        e.addRot = 0;
        if(!super.use(e, w))
            return false;
        e.health = Math.max(e.health+e.maxHealth/4, e.maxHealth);
        return true;
    }
}
