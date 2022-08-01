package com.httplaz.diefromfire.entities.interactives;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.entities.mobs.Entity;

public class Interactive extends Entity
{

    public String message;
    public boolean showMessage = false;
    BitmapFont font;
    public int cooldown = 0;
    public int cooldownLimit = 100;
    public Sound sound;
    public int lifetime = 90;
    public Interactive(Vector2 p, TextureRegion t, float cR, World w, String m)
    {
        super(p, t, cR, w);
        maxHealth = 99999999;
        health = 999999999;
        font = new BitmapFont(Gdx.files.internal("default.fnt"));
        message = m;
        speed = 0;
    }

    public boolean Interact(Entity e)
    {
        if(cooldown>0)
            return false;
        cooldown = cooldownLimit;
        if(sound!=null)
            Resourses.playSound(sound);
        return true;
    }

    @Override
    public void update(World w)
    {
        super.update(w);
        cooldown--;
        //lifetime--;
        //Vector2 a = getWorldPosition();
        //for (int i = (int)a.x; i<(int)a.x+2; i++)
            //for(int j=(int)a.y; j<(int)a.y+2; j++)
                //System.out.println(i + " " + j);
                //world.addColor(i, j, new int[]{255, 0, 0, 1});
        //world.addColor((int)getWorldPosition().x, (int)getWorldPosition().y, new int[]{24, 0, 0, 1});
    }

    @Override
    public void draw(SpriteBatch spriteBatch, Vector2 dPos, Vector2 scale)
    {
        super.draw(spriteBatch, dPos, scale);
        if(showMessage)
        {
            Vector2 pos = getWorldPosition();
            font.getData().setScale(2);
            font.draw(spriteBatch, message, dPos.x+(pos.x-0.5f)*scale.x, dPos.y+(pos.y-0.5f)*scale.y);
        }
        showMessage = false;
    }

}
