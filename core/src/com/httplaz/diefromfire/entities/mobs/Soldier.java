package com.httplaz.diefromfire.entities.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.entities.AI;
import com.httplaz.diefromfire.items.Lasergun;

public class Soldier extends Entity
{
    public Soldier(Vector2 p, World w)
    {
        super(p, (TextureRegion) Resourses.getResourse("textures/mobs/mobs.png:64 0 16 16", TextureRegion.class), 0.3f, w);
        speed = 0.1f;
        health = 300;
        giveInventory();
        cost = 6;
        //ai.attackDistance = 10;
    }

    @Override
    public void giveAi(AI a)
    {
        super.giveAi(a);
        //ai.attackDistance = 20;
    }
}
