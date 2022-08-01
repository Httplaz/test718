package com.httplaz.diefromfire.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Resourses;

public class Button
{
    public float posX, posY, width, height;
    TextureRegion texture;
    TextureRegion pressedTexture;
    TextureRegion freeTexture;
    int activeTime;
    public int active;
    int id;
    String text;
    boolean enabled = true;

    public Button(float px, float py, float w, float h, int aT, int i, String s, TextureRegion t, TextureRegion pt, TextureRegion ft)
    {
        posX = px;
        posY = py;
        texture = t;
        if(texture==null)
            texture = (TextureRegion)(Resourses.getResourse("textures/blank.png:0 0 8 8", TextureRegion.class));
        activeTime = aT;
        active = 0;
        id = i;
        text = s;
        width = w;
        height = h;
        //pressedTexture = (Texture)(Resourses.getResourse("textures/gui/pressedButton.png", Texture.class));
        //freeTexture = (Texture)(Resourses.getResourse("textures/gui/freeButton.png", Texture.class));
        //pressedTexture = (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 32 32 32", TextureRegion.class));
        //freeTexture = (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 0 32 32", TextureRegion.class));
        pressedTexture = pt;
        freeTexture = ft;
    }

    public void draw(SpriteBatch sb, float screenSizeX, float screenSizeY)
    {
        //System.out.println(active);
        float aspectRatio = screenSizeX/screenSizeY;
        //sb.draw(texture, -1000, -1000, 2000, 2000);
        float offset = (active>0? 1.f:0.f);
        sb.draw(active>0? pressedTexture:freeTexture, posX*screenSizeX, screenSizeY-(posY+height*1.f)*screenSizeY, width*screenSizeX, height*screenSizeY);
        sb.draw(texture, (posX+width*0.1f+width*0.05f*offset)*(screenSizeX), screenSizeY-(posY+height*0.9f + height*0.05f*offset)*(screenSizeY), width*screenSizeX*0.9f, height*screenSizeY*0.9f);
    }

    public boolean intersects(float cPosX, float cPosY)
    {
        return !(cPosX<posX || cPosX > posX+width || cPosY<posY || cPosY > posY+height);
    }

    public void setTexture(TextureRegion txt)
    {
        texture = txt;
    }

    public void enable()
    {
        enabled = true;
    }

    public void disable()
    {
        enabled = false;
    }

}
