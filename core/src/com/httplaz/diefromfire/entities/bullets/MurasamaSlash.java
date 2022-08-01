package com.httplaz.diefromfire.entities.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;

public class MurasamaSlash extends Bullet
{

    public MurasamaSlash(Vector2 p, Vector2 v, World w)
    {
        super(p, v, 20, w, 1.f, 0.9f, (TextureRegion) Resourses.getResourse("textures/projectiles/basic.png:16 0 16 32", TextureRegion.class), null);
        speed = 0.04f;
        damage = 20;
        destructible = false;
        reflectsBullets = true;
        transcendent = true;
        collider = new Rect(-1f, -1f, 0.4f, 1f);
    }
}
