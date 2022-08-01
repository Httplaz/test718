package com.httplaz.diefromfire.entities.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;

public class MuramasaSlash extends Bullet
{

    public MuramasaSlash(Vector2 p, Vector2 v, World w, int power)
    {
        super(p, v, 20, w, 1.f+power/20.f, 0.9f, (TextureRegion) Resourses.getResourse("textures/projectiles/basic.png:48 0 16 32", TextureRegion.class), null);
        speed = 0.04f;
        damage = 4;
        float pc = (1.f+power/10.f);
        destructible = false;
        reflectsBullets = true;
        size = 1.f+power/10.f;
        speed*=(1.f+power/10.f);
        damage = (int)(damage*(1.f+power/10.f));
        collider = new Rect(-1f, -1f*pc, 0.5f*pc, 1f*pc);
        transcendent = true;
    }
}
