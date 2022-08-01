package com.httplaz.diefromfire.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.entities.mobs.Entity;

public class Item
{
    public TextureRegion texture;
    public int maxCount;
    public int count;
    public int id;
    public String name;


    public int cooldownTime = 0;
    public int cooldown = 0;
    public int soundDelta = 0;

    public Sound useSound;
    public float useVolume = 1;

    public int staminaCost = 0;
    public boolean passiveOnly = false;
    public boolean ability = false;

    public Item(TextureRegion t, int mC, int c, String n, Sound s)
    {
        texture = t;
        maxCount = mC;
        count = c;
        name = n;
        useSound = s;
    }
    public void setId(int i)
    {
        id = i;
    }

    public boolean use(Entity e, World w)
    {
        if(cooldown<=0)
        {
            cooldown = cooldownTime;
            if(soundDelta>3 && useSound!=null)
            {
                Resourses.playSound(useSound, 1*useVolume);
                soundDelta = 0;
            }
            return true;
        }
        return false;
    }

    public void noUse(Entity e, World w)
    {

    }

    public void onPickup(Entity e)
    {

    }
}
