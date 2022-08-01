package com.httplaz.diefromfire.entities.interactives;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.AI;
import com.httplaz.diefromfire.entities.mobs.*;
import com.httplaz.diefromfire.items.*;

public class WaveTrigger extends Interactive
{
    int waveCount = 1;
    public WaveTrigger(Vector2 p, World w)
    {
        super(p, (TextureRegion) Resourses.getResourse("textures/interactives/basic.png:0 0 32 32", TextureRegion.class), 2, w, "start wave: E");
        health = 30000000;
    }


    @Override
    public boolean Interact(Entity e)
    {
        if(!super.Interact(e))
            return false;
        System.out.println(waveCount);
        for (int i=0; i<waveCount*4; i++)
        {
            Entity e1;
            if(WorldGenerator.random.nextInt(waveCount+80)>80)
                e1 = (new BigZombie(new Vector2(WorldGenerator.random.nextInt(6)*20-50+e.getPosition().x, WorldGenerator.random.nextInt(6)*20-50+e.getPosition().y), world));
            else if(WorldGenerator.random.nextInt(waveCount+120)>120)
                e1 = (new Soldier(new Vector2(WorldGenerator.random.nextInt(6)*20-50+e.getPosition().x, WorldGenerator.random.nextInt(6)*20-50+e.getPosition().y), world));
            else if(waveCount%3==0)
                e1 = (new Thinng(new Vector2(WorldGenerator.random.nextInt(6)*20-50+e.getPosition().x, WorldGenerator.random.nextInt(6)*20-50+e.getPosition().y), world));
            else
                e1 = (new Zombie(new Vector2(WorldGenerator.random.nextInt(6)*20-50+e.getPosition().x, WorldGenerator.random.nextInt(6)*20-50+e.getPosition().y), world));
            e1.giveAi(new AI(e1));
            e1.setFaction(1);
            Item item = new Claw();
            if(e1 instanceof Soldier)
            {
                int w = WorldGenerator.random.nextInt(3);
                switch (w)
                {
                    case 0:
                        item = new Lasergun();
                        e1.ai.attackDistance = 20;
                        break;
                    case 1:
                        item = new Gun();
                        e1.ai.attackDistance = 20;
                        break;
                    case 2:
                        item = new RedLightsword();
                        break;
                }
            }
            if(e1 instanceof BigZombie)
            {
                item = new BlueLightsword();
                if(WorldGenerator.random.nextInt(10)==1)
                    item = new GreenLightsword();
                e1.ai.attackDistance = 10;
            }
            e1.inventory.addItem(item);
            e1.health*=Math.max(1, Math.pow(1.2f, 1.f+WorldGenerator.random.nextInt(waveCount)*0.1f));
            e1.power*=Math.max(1, Math.pow(1.2f, 1.f+WorldGenerator.random.nextInt(waveCount)*0.1f));
            e1.cost+=WorldGenerator.random.nextInt(waveCount);
            world.addEntity(e1);

        }
        waveCount++;
        return true;
    }

}
