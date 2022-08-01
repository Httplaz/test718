package com.httplaz.diefromfire.entities.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;

public class Thinng extends Entity
{
    public Thinng(Vector2 p, World w)
    {
        super(p, (TextureRegion) Resourses.getResourse("textures/mobs/mobs.png:16 0 16 16", TextureRegion.class), 0.4f, w);
        speed = 0.12f;
        health = 50;
        cost = 2;
        giveInventory();
    }
}
