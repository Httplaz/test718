package com.httplaz.diefromfire;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.*;

public abstract class Resourses
{
    static HashMap<String, Object> resourses = new HashMap<>();
    static ArrayDeque<Object> soundsPlayed = new ArrayDeque<>();
    public static float volume = 0.5f;
    public static float pitch = 0.5f;
    public static float water = 0.95f;
    public static Object getResourse(String name, Class type)
    {
        if(resourses.containsKey(name))
            return resourses.get(name);
        else
        {
            if(type == Texture.class)
            {
                Texture t = new Texture(name);
                resourses.put(name, t);
                return t;
            }
            if(type == TextureRegion.class)
            {
                int inds[] = new int[5];
                inds[0] = name.indexOf(':');
                if(inds[0]==-1)
                {
                    Texture atlas = (Texture)getResourse(name, Texture.class);
                    TextureRegion region = new TextureRegion(atlas);
                    resourses.put(name, region);
                    return region;
                }
                for (int i=1; i<4; i++)
                    inds[i] = name.indexOf(' ', inds[i-1]+1);
                inds[4] = name.length();
                int pos[] = new int[4];
                for (int i=0; i<4; i++)
                    pos[i] = Integer.parseInt(name.substring(inds[i]+1, inds[i+1]));
                Texture atlas = (Texture)getResourse(name.substring(0, inds[0]), Texture.class);
                TextureRegion region = new TextureRegion(atlas, pos[0],pos[1],pos[2], pos[3]);
                resourses.put(name, region);
                return region;
            }
            if(type==Sound.class)
            {
                Sound s = Gdx.audio.newSound(Gdx.files.internal(name));
                resourses.put(name, s);
                return s;
            }
            return null;

        }
    }

    public static void playSound(Sound s, float v)
    {
        if(soundsPlayed.size()>10)
        {
            Sound firstSound = (Sound)soundsPlayed.pollFirst();
            long id = (long)soundsPlayed.pollFirst();
            firstSound.stop(id);
        }
        soundsPlayed.addLast(s);
        soundsPlayed.addLast(s.play(Math.max(0.f, (volume-0.2f))*v));
        s.setPitch((long) soundsPlayed.peekLast(), (WorldGenerator.random.nextFloat()+0.6f)*pitch*2.f);
    }

    public static void playSound(Sound s)
    {
        playSound(s, 1);
    }

}
