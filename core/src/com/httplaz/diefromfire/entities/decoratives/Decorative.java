package com.httplaz.diefromfire.entities.decoratives;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Tiles;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.mobs.Entity;

public class Decorative extends Entity
{
    public int lifetime = 90;
    public int color[];
    public Decorative(Vector2 p, TextureRegion t, World w)
    {
        super(p, t, 0, w);
        int a = 0;//WorldGenerator.random.nextInt(3);
        switch (a)
        {
            case 0:
                color = new int[]{255, 0, 0, 255};
                break;
            case 1:
                color = new int[]{0, 255, 0, 255};
                break;
            case 2:
                color = new int[]{0, 0, 255, 255};
                break;

        }
    }

    @Override
    public void update(World w)
    {
        if(lifetime<=0)
        {
            Die();
            return;
        }
        lifetime--;
        //System.out.println(getPosition().toString());
        Vector2 a = getWorldPosition();
        for (int i = (int)(a.x-0.7f); i<a.x+0.7f; i++)
            for(int j=(int)(a.y-0.7f); j<a.y+0.7f; j++)
                if(Tiles.getTile(w.getTile(i, j)).priority<=2)
                {
                    if(Tiles.getTile(w.getTile(i, j)).name=="water")
                        world.setColor(i, j, color, true);
                    else
                    {
                        color[3]=1;
                        world.addColor(i, j, color);
                        color[3] = 255;
                    }
                }
    }

    @Override
    public void Die()
    {
        world.decoratives.remove(this);
    }
}
