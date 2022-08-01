package com.httplaz.diefromfire.items.passive;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.items.Item;

public class CureBonus extends Item
{
    public CureBonus()
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion) Resourses.getResourse("textures/items/passive.png:64 32 16 16", TextureRegion.class), 1, 1, "HP refill", null);
        super.setId(77);
        useVolume = 3.f;
        staminaCost = 2;
        passiveOnly = true;
    }

    @Override
    public void onPickup(Entity e)
    {
        e.health = e.maxHealth;
    }
}
