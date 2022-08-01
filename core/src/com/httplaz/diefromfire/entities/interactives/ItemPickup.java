package com.httplaz.diefromfire.entities.interactives;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.items.Item;

public class ItemPickup extends Interactive
{

    public Item item;
    public ItemPickup(Vector2 p, Item i, World w)
    {
        super(p, i.texture, 0.7f, w, "E: " + i.name);
        //speed = 0;
        sound = (Sound) Resourses.getResourse("sounds/pick.mp3", Sound.class);
        item = i;
        health = 30000;
    }

    @Override
    public boolean Interact(Entity e)
    {
        if(!super.Interact(e))
            return false;
        if(item.ability)
            e.ability = item;
        else if(item.passiveOnly)
            item.onPickup(e);
        else
            e.inventory.addItem(item);
        world.removeEntity(this);
        return true;
    }
}
