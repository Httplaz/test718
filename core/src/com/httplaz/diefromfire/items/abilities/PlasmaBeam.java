package com.httplaz.diefromfire.items.abilities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.bullets.LaserBullet;
import com.httplaz.diefromfire.entities.bullets.PlasmaBeamBullet;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.entities.bullets.GunBullet;
import com.httplaz.diefromfire.items.Item;

public class PlasmaBeam extends Item
{

    public PlasmaBeam()
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion) Resourses.getResourse("textures/items/abilities.png:48 0 16 16", TextureRegion.class), 1, 1, "plasma beam", (Sound)Resourses.getResourse("sounds/items/plasmaBeam.mp3", Sound.class));
        super.setId(77);
        cooldownTime = 20;
        staminaCost = 90;
        ability = true;
    }
    @Override
    public boolean use(Entity e, World w)
    {
        e.addRot = 0;
        if(!super.use(e, w))
            return false;
        //for (int i=0; i<30; i++)
        Vector2 bias = e.getRotationVec2().scl(new Vector2(WorldGenerator.random.nextFloat()/4.f-0.125f,WorldGenerator.random.nextFloat()/4.f-0.125f));
        Entity b = new PlasmaBeamBullet(e.getPosition().add(e.getRotationVec2().scl(1)), e.getRotationVec2(), w, 120);
        b.setFaction(e.faction);
        w.addEntity(b);        //w.breakTile((int)e.target.x, (int)e.target.y);
        b.update(w);
        //System.out.println("doing");
        //w.setTile((int)e.target.x, (int)e.target.y, 0);
        return true;
    }

}
