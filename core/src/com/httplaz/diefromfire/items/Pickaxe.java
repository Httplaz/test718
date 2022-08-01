package com.httplaz.diefromfire.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.mobs.Entity;

public class Pickaxe extends Item
{
    public Sound breakSound;
    public Pickaxe()
    {
        //Tile tile = Tiles.getTile(id);
        super((TextureRegion) Resourses.getResourse("textures/items/tools.png:0 0 16 16", TextureRegion.class), 1, 1, "Pickaxe", null);
        super.setId(77);
        cooldownTime = 10;
        breakSound = (Sound) Resourses.getResourse("sounds/break.mp3", Sound.class);
    }

    @Override
    public boolean use(Entity e, World w)
    {
        //System.out.println("www");
        if(!super.use(e, w))
            return false;
        if(e.addRot<0)
            e.addRot = 90- WorldGenerator.random.nextInt(50);
        else
            e.addRot = -90+WorldGenerator.random.nextInt(50);
        Vector2 target = e.target.cpy().sub(w.getTileOffset());
        if (w.breakTile((int)target.x, (int)target.y))
            Resourses.playSound(breakSound);
        //System.out.println("doing");
        //w.setTile((int)e.target.x, (int)e.target.y, 0);
        return true;
    }
}
