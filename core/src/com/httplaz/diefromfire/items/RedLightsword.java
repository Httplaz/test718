package com.httplaz.diefromfire.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.bullets.Bullet;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.entities.bullets.MurasamaSlash;

public class RedLightsword extends Item
{

    public RedLightsword()
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion) Resourses.getResourse("textures/items/tools.png:64 0 16 16", TextureRegion.class), 1, 1, "redSword", (Sound)Resourses.getResourse("sounds/items/murasama.wav", Sound.class));
        super.setId(77);
        cooldownTime = 3;
    }
    @Override
    public boolean use(Entity e, World w)
    {
        if(!super.use(e, w))
            return false;
        //for (int i=0; i<30; i++)
        if(e.addRot<0)
            e.addRot = 90-WorldGenerator.random.nextInt(50);
        else
            e.addRot = -90+WorldGenerator.random.nextInt(50);
        Vector2 bias = e.getRotationVec2().scl(new Vector2(WorldGenerator.random.nextFloat()/4.f-0.125f,WorldGenerator.random.nextFloat()/4.f-0.125f));
        w.addEntity(new MurasamaSlash(e.getPosition().add(bias).add(e.getRotationVec2().scl(1)), e.getRotationVec2().add(bias), w));
        w.entities.get(w.entities.size()-1).setFaction(e.faction);
        ((Bullet)w.entities.get(w.entities.size()-1)).damage*=e.power;
        //w.breakTile((int)e.target.x, (int)e.target.y);
        //System.out.println("doing");
        //w.setTile((int)e.target.x, (int)e.target.y, 0);
        return true;
    }

}
