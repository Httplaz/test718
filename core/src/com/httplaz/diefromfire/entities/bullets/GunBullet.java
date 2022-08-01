package com.httplaz.diefromfire.entities.bullets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;

public class GunBullet extends Bullet
{
    public GunBullet(Vector2 p, Vector2 v,  World w)
    {
        super(p, v, 30, w, 0.3f, 0.95f, (TextureRegion) Resourses.getResourse("textures/projectiles/basic.png:0 0 16 16", TextureRegion.class), null);
        //speed = 0.03f;
        damage = 26;
    }
}
