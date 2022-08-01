package com.httplaz.diefromfire.entities;

import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.items.Claw;

public class AI
{
    public Entity e;
    public State state;

    public Vector2 absTarget;
    public float attackDistance = 2.0f;

    public int usage = 90;

    public AI(Entity entity)
    {
        e = entity;
        absTarget = new Vector2(e.getPosition());
        state = State.Wandering;
        e.inventory.selectedItem = 0;
    }

    public void update()
    {
        e.velocity.set(e.getRotationVec2().add(e.addVelocity));
        if(e.getPosition().dst(e.target)<attackDistance)
        {
            usage--;
            if(usage<=0)
            {
                e.inventory.noUseItem();
                usage = WorldGenerator.random.nextInt(600);
            }
            else
                e.inventory.useItem();
        }
    }
}

enum State
{
    Wandering,
    Targeted
}

