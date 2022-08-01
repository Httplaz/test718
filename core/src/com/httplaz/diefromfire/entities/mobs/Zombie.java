package com.httplaz.diefromfire.entities.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;

public class Zombie extends Entity
{
    public Zombie(Vector2 p, World w)
    {
        super(p, (TextureRegion) Resourses.getResourse("textures/mobs/mobs.png:0 0 16 16", TextureRegion.class), 0.4f, w);
        speed = 0.08f;
        giveInventory();
    }
}