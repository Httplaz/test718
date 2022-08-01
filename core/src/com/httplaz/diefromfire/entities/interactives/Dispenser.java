package com.httplaz.diefromfire.entities.interactives;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.mobs.*;
import com.httplaz.diefromfire.items.*;
import com.httplaz.diefromfire.items.abilities.*;
import com.httplaz.diefromfire.items.passive.*;

public class Dispenser extends Interactive
{
    int cost = 15;
    public Dispenser(Vector2 p, World w)
    {
        super(p, (TextureRegion) Resourses.getResourse("textures/interactives/basic.png:32 0 32 32", TextureRegion.class), 2, w, "purchase: E");
        message+=cost;
        cooldownLimit = 3;
        health = 30000000;
    }

    @Override
    public void update(World w)
    {
        super.update(w);
        message = "purchase: E " + cost + "\n you have: " + world.coins;
    }

    @Override
    public boolean Interact(Entity e)
    {
        cost = 0;
        if(!super.Interact(e))
            return false;
        if(world.coins<cost)
            return false;

        world.coins-=cost;
        cost+=WorldGenerator.random.nextInt(7);
        Item item = new NullItem();
        int i = WorldGenerator.random.nextInt(17);
        switch (i)
        {
            case 0:
            case 1:
                item = new PowerBonus();
                break;
            case 2:
                item = new SpeedBonus();
                break;
            case 3:
                item = new Anvil();
                break;
            case 4:
                item = new HealthBonus();
                break;
            case 5:
                item = new CureBonus();
                break;
            case 6:
                item = new NeuroVirus();
                break;
            case 7:
                item = new PlasmaBeam();
                break;
            case 8:
                item = new Dash();
                break;
            case 9:
                item = new Teleporter();
                break;
            case 10:
                item = new ForceBlow();
                break;
            case 11:
                item = new Pickaxe();
                break;
            case 12:
                item = new Gun();
                break;
            case 13:
                item = new Lasergun();
                break;
            case 14:
                item = new BlueLightsword();
                break;
            case 15:
                item = new RedLightsword();
                break;
            case 16:
                item = new GreenLightsword();
                break;
        }
        ItemPickup pickup = new ItemPickup(e.getPosition().add(new Vector2(5.f,0.f).rotateDeg(WorldGenerator.random.nextFloat()*360.f)), item, world);
        pickup.setFaction(2);
        pickup.speed = 0.1f;
        world.addEntity(pickup);
        return true;
    }

}
