package com.httplaz.diefromfire.entities.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;

public class ClawSlash extends Bullet
{

    public ClawSlash(Vector2 p, Vector2 v, World w)
    {
        this(p, v, w, 1);
    }
    public ClawSlash(Vector2 p, Vector2 v, World w, float power)
    {
        super(p, v, 20, w, 0.5f, 0.9f, (TextureRegion) Resourses.getResourse("textures/projectiles/basic.png:32 0 16 16", TextureRegion.class), null);
        speed = 0.04f;
        damage = 20;
        destructible = false;
        reflectsBullets = false;
    }
}
