package com.httplaz.diefromfire.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Prop
{
    public Sprite sprite;
    public Vector2 position;
    public float collisionRadius;

    public Prop(Vector2 p, TextureRegion t, float cR)
    {
        sprite = new Sprite(t);
        position = p;
        collisionRadius = cR;
    }

    public Prop(Vector2 p, Texture t, float cR)
    {
        sprite = new Sprite(t);
        position = p;
        collisionRadius = cR;
    }


    public void draw(SpriteBatch spriteBatch, Vector2 dPos, Vector2 scale)
    {
        spriteBatch.draw(sprite, dPos.x+position.x*scale.x, dPos.y+position.y*scale.y, scale.x, scale.y);
    }

    public void interact()
    {

    }
}
