package com.httplaz.diefromfire.entities.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.items.*;
import com.httplaz.diefromfire.items.abilities.*;

public class Flower extends Entity
{
    public Flower(Vector2 p, World w)
    {
        super(p, (TextureRegion) Resourses.getResourse("entity.png", TextureRegion.class), 0.6f, w);
        speed = 0.1f;
        giveInventory();
        size = 1;
        health = 800;
        maxHealth = 800;
        Item item = new NullItem();
        int w1 = WorldGenerator.random.nextInt(5);
        switch (w1)
        {
            case 0:
                item = new Lasergun();
                break;
            case 1:
                item = new Gun();
                break;
            case 2:
                item = new RedLightsword();
                break;
            case 3:
                item = new GreenLightsword();
                break;
            case 4:
                item = new Claw();
                break;
        }
        //item = new Pickaxe();
        inventory.addItem(item);

        w1 = WorldGenerator.random.nextInt(5);
        switch (w1)
        {
            case 0:
                item = new Dash();
                break;
            case 1:
                item = new ForceBlow();
                break;
            case 2:
                item = new Teleporter();
                break;
            case 3:
                item = new PlasmaBeam();
                break;
            case 4:
                item = new Healer();
                break;
        }
        ability = item;
        //resurrectionChance = 5;
    }
}
