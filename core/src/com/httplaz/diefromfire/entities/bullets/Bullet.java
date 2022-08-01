package com.httplaz.diefromfire.entities.bullets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.entities.mobs.Entity;

public class Bullet extends Entity
{
    public int lifetime;
    public int inactiveTime = 3;
    public float speedDecrease;
    public int damage = 0;
    public boolean destructible = true;
    public boolean ricoshets = true;
    public Sound hitSound;

    public Bullet(Vector2 p, Vector2 v, int lt, World w, float cR, float sd, TextureRegion t, Sound hs)
    {
        super(p, t, cR, w);
        lifetime = lt;
        speed = v.len();
        velocity = v.nor();
        speedDecrease = sd;
        setTarget(getWorldPosition().add(velocity.cpy().scl(40000)));
        hitSound = hs;
        size =1;
    }

    @Override
    public void update(World w)
    {
        super.update(w);
        //if(velocity.x==0 && velocity.y==0)
        {
            //Die();
            //return;
        }

        lifetime--;
        inactiveTime--;
        if(lifetime==0)
        {
            Die();
            return;
        }
        speed*=speedDecrease;
    }

    public void Hit(Object o)
    {
        if(o instanceof Vector2 && !transcendent)
        {
            if(ricoshets)
                velocity.add(((Vector2) o).nor().scl(2));
            else if(destructible)
                Damage(900);
            //velocity.rot
            //velocity.nor();
            //System.out.println(velocity);
        }

        if(o instanceof Entity)
        {
            if(!((Entity) o).reflectsBullets)
            {
                if(((Entity) o).inFrames<=0)
                    ((Entity) o).Damage((int)(damage));
                ((Entity)o).kick(velocity.cpy().scl(0.1f*speed/((Entity) o).speed));
                if(hitSound!=null)
                    Resourses.playSound(hitSound);
            }
            if(destructible && !((Entity) o).reflectsBullets)
                Damage(900);
            if(((Entity) o).reflectsBullets && destructible && !transcendent)
            {
                //System.out.println("NIGGERS");
                //lifetime = 0;
                Hit(getPosition().sub(((Entity) o).getPosition()).nor());
                faction = ((Entity) o).faction;
            }
        }
    }

    public void onDeath()
    {
        Damage(99999);
    }
}
