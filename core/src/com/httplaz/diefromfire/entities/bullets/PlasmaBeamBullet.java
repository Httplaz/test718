package com.httplaz.diefromfire.entities.bullets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.Tiles;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.items.abilities.PlasmaBeam;

public class PlasmaBeamBullet extends Bullet
{
    public float length = 1;
    public int segments = 0;
    public PlasmaBeamBullet next;
    public PlasmaBeamBullet(Vector2 p, Vector2 v,  World w, int l)
    {
        super(p, v, 20, w, 0.95f, 0.99f, (TextureRegion) Resourses.getResourse("textures/projectiles/basic.png:32 48 16 16", TextureRegion.class), null);
        speed = 0.3f;
        segments = l;
        reflectsBullets = false;
        transcendent = true;
        ricoshets = false;
        destructible = false;
        //transcendent = true;
        damage = 300;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, Vector2 dPos, Vector2 scale)
    {
        rotation = getRotationFloat();
        float dscale = scale.x-scale.y;
        Vector2 pos = getWorldPosition();
        spriteBatch.draw(sprite.getTexture(), dPos.x+(pos.x-0.5f)*scale.x, dPos.y+(pos.y-0.5f)*scale.y, scale.x*0.5f, scale.y*0.5f, (scale.y)/16.f*sprite.getRegionWidth(), (scale.y)/16.f*sprite.getRegionHeight(), size,size, rotation, sprite.getRegionX(), sprite.getRegionY(), sprite.getRegionWidth(), sprite.getRegionHeight(), false, false);
        framesAlive++;
    }

    @Override
    public void Damage(int d)
    {
        segments = 0;
    }

    @Override
    public void update(World w)
    {
        Vector2 a = getPosition();
        super.update(w);
        //if(!w.entities.contains(this))
        //w.removeEntity(next);
        float cR = collisionRadius;

        Vector2 a1 = getWorldPosition();
        Vector2 r1 = new Vector2(a1.x-cR, a1.y-cR);
        Vector2 r2 = new Vector2(a1.x+cR, a1.y+cR);

        if(r1.x<=0 || r1.y<=0 || r2.x>=w.getTileWidth() || r2.y>=w.getTileHeight())
            return;

        for (float x=r1.x; x<=r2.x; x++)
            for (float y=r1.y; y<=r2.y; y++)
                if(Tiles.getTile(w.getTile((int)x, (int)y)).name!="water")
                {
                    //world.breakTile((int) x, (int) y);
                    world.setTile((int) x, (int) y, 11);
                }
        Vector2 b = getPosition();
        if(a.x!=b.x || a.y!=b.y)
        {
            Vector2 dist = b.cpy().sub(a);
            position.set(a);
            if(segments>0)
            {
                world.entities.add(new PlasmaBeamBullet(b, velocity, w, segments-1));
                next = (PlasmaBeamBullet) world.entities.get(world.entities.size()-1);
                next.target.set(target.cpy().add(dist));
                next.setFaction(faction);
                next.update(w);
            }
            segments = 0;
        }
    }

    @Override
    public void Die()
    {
        super.Die();
        if(next!=null)
            next.Die();
    }

}
