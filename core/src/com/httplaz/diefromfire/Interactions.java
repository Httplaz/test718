package com.httplaz.diefromfire;

import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.entities.bullets.Bullet;

public abstract class Interactions
{
    public static Vector2 move(Entity e, World w)
    {
        Vector2 a = e.getPosition().add(w.getTileOffset().scl(-1));
        Vector2 dist = e.getVelocity().scl(e.speed);
        Vector2 res = move(e.collisionRadius, new Vector2(a), dist, w);
        Vector2 ans = new Vector2();
        if(res!=null)
        {
            if(move(e.collisionRadius, new Vector2(a), new Vector2(dist.x, 0), w)==null)
                ans = a.add(new Vector2(dist.x, 0)).add(w.getTileOffset());
            else if(move(e.collisionRadius, new Vector2(a), new Vector2(0, dist.y), w)==null)
                ans = a.add(new Vector2(0, dist.y)).add(w.getTileOffset());
            else ans =  a.add(w.getTileOffset());
        }
        else ans = a.add(dist).add(w.getTileOffset());

        if(e instanceof Bullet)
        {
            if(ans.dst(e.getPosition().add(dist))>0.1f)
            {
                if(((Bullet)e).inactiveTime>1)
                {
                    //((Bullet) e).onDeath();
                    e.Damage(100);
                    return ans;
                    //return e.getPosition().add(dist);
                }
                Vector2 ad = ans.cpy().add(e.getPosition().scl(-1));
                Vector2 dd = ad.add(dist.scl(-1));
                //System.out.println(dd);
                ((Bullet) e).Hit(dd);
            }
                //((Bullet) e).Hit(ans.add((e.getPosition().add(dist)).scl(-1)));
                //((Bullet)e).Hit(new Vector2(1.f,0.f));}
        }


        return ans;
    }

    public static Vector2 move(float cR, Vector2 a, Vector2 dist, World w)
    {
        Vector2 b = new Vector2(a).add(dist);
        Vector2 r1 = new Vector2(Math.min(a.x, b.x)-cR, Math.min(a.y, b.y)-cR);
        Vector2 r2 = new Vector2(Math.max(a.x, b.x)+cR, Math.max(a.y, b.y)+cR);

        if(r1.x<=0 || r1.y<=0 || r2.x>=w.getTileWidth() || r2.y>=w.getTileHeight())
            return a;

        for (float x=dist.x>0?r2.x : r1.x; dist.x>0?(x>=r1.x) : (x<=r2.x); x+=dist.x>0? -1:1)
            for (float y=dist.y>0?r2.y : r1.y; dist.y>0?(y>=r1.y) : (y<=r2.y); y+=dist.y>0? -1:1)
                if(Tiles.getTile(w.getTile((int)x, (int)y)).priority>=3)
                {
                    //System.out.println(x);
                    //System.out.println(y);
                    //System.out.println(a);
                    //System.out.println(x);
                    //System.out.println(x-w.getTileWidth()/2.f);
                    //return new Vector2(x-w.getTileWidth()/2.f, y-w.getTileHeight()/2.f);
                    return (new Vector2(x,y).add((new Vector2(a).add(b)).scl(-0.5f))).scl(-1);
                }
        return null;
    }

    public static boolean intersects(Vector2 pos, float cR, Vector2 tile)
    {
        return !( (pos.x+cR>tile.x && pos.x-cR<tile.x) || (pos.x+cR>tile.x+1 && pos.x-cR<tile.x) || (pos.y+cR>tile.y && pos.y-cR<tile.y) || (pos.y+cR>tile.y+1 && pos.y-cR<tile.y));
    }
}
