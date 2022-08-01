package com.httplaz.diefromfire.entities.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;

public class BigZombie extends Entity
{
    public BigZombie(Vector2 p, World w)
    {
        super(p, (TextureRegion) Resourses.getResourse("textures/mobs/mobs.png:32 0 32 32", TextureRegion.class), 0.8f, w);
        speed = 0.05f;
        health = 600;
        cost = 7;
        giveInventory();
    }
}
