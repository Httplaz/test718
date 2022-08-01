package com.httplaz.diefromfire.entities.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;

public class GreenLightswordSlash extends Bullet
{

    public GreenLightswordSlash(Vector2 p, Vector2 v, World w, int power)
    {
        super(p, v, (int)(20*(1.f+power/10.f)), w, 1.f+power/20.f, (float)Math.pow(0.97, 10.f/power), (TextureRegion) Resourses.getResourse("textures/projectiles/basic.png:64 0 16 32", TextureRegion.class), null);
        speed = 0.04f;
        damage = 20;
        float pc = (1.f+power/10.f);
        destructible = false;
        reflectsBullets = true;
        size = 1.f+power/40.f;
        speed*=(1.f+power/4.f);
        damage = (int)(damage*(1.f+power/10.f));
        collider = new Rect(0.4f*pc/4.f, -1f*pc/4, 0.5f*pc/4, 1f*pc/4);
        transcendent = true;
    }
}
