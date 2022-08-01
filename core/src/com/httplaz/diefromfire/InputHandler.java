package com.httplaz.diefromfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements InputProcessor
{
    MainScreen screen;
    Boolean lmb = false;


    public InputHandler(MainScreen screen)
    {
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if(keycode == Input.Keys.W)
            screen.addVelocity(new Vector2(0,1f));
        if(keycode == Input.Keys.A)
            screen.addVelocity(new Vector2(-1f,0));
        if(keycode == Input.Keys.S)
            screen.addVelocity(new Vector2(0,-1f));
        if(keycode == Input.Keys.D)
            screen.addVelocity(new Vector2(1f,0));
        if(keycode == Input.Keys.E)
            screen.interacting = true;
        if(keycode == Input.Keys.SHIFT_LEFT)
            screen.player.usingAbility = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        if(keycode == Input.Keys.W)
            screen.addVelocity(new Vector2(0,-1f));
        if(keycode == Input.Keys.A)
            screen.addVelocity(new Vector2(1f,0));
        if(keycode == Input.Keys.S)
            screen.addVelocity(new Vector2(0,1f));
        if(keycode == Input.Keys.D)
            screen.addVelocity(new Vector2(-1f,0));
        if(keycode == Input.Keys.ESCAPE)
            screen.pause = !screen.pause;
        if(keycode == Input.Keys.E)
            screen.interacting = false;
        if(keycode == Input.Keys.SHIFT_LEFT)
            screen.player.usingAbility = false;
        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if(button==Input.Buttons.LEFT)
        {
            //System.out.println(screen.building);
            //System.out.println("touch");
            //System.out.println(screen.building);
            if(screen.building)
                //screen.player.inventory.useItem();
                screen.itemUse = true;
            screen.lmb = true;
            if(screen.building && screen.rectangle)
                screen.startRectangle();
        }
        if(button==Input.Buttons.RIGHT)
            screen.rmb = true;

        Vector3 t = new Vector3(screenX, screenY, 0);
        t = screen.camera.unproject(t);
        //screen.gui.update((float)screenX/(float)Gdx.graphics.getWidth(), (float)screenY/(float)Gdx.graphics.getHeight(), true);
        float k = Gdx.graphics.getHeight()/480.f;
        float p = Gdx.graphics.getWidth()/800.f/k;
        screen.gui.pauseUpdate(t.x/(float)screen.camera.viewportWidth*p+0.5f, 0.5f-t.y/(float)screen.camera.viewportHeight, screen.pause);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        if(button==Input.Buttons.LEFT)
        {
            screen.itemUse = false;
            screen.lmb = false;
            Vector3 t = new Vector3(screenX, screenY, 0);
            t = screen.camera.unproject(t);
            //screen.gui.update((float)screenX/(float)Gdx.graphics.getWidth(), (float)screenY/(float)Gdx.graphics.getHeight(), true);
            float k = Gdx.graphics.getHeight()/480.f;
            float p = Gdx.graphics.getWidth()/800.f/k;
            screen.building = screen.gui.update(t.x/(float)screen.camera.viewportWidth*p+0.5f, 0.5f-t.y/(float)screen.camera.viewportHeight, !screen.pause);
            screen.gui.touchingVolume = false;
            screen.gui.touchingWater = false;
        }
        if(button==Input.Buttons.RIGHT)
            screen.rmb = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        screen.setCursorPosition(new Vector2(screenX, screenY));
        Vector3 t = new Vector3(screenX, screenY, 0);
        t = screen.camera.unproject(t);
        //screen.gui.update((float)screenX/(float)Gdx.graphics.getWidth(), (float)screenY/(float)Gdx.graphics.getHeight(), true);
        float k = Gdx.graphics.getHeight()/480.f;
        float p = Gdx.graphics.getWidth()/800.f/k;
        screen.gui.pauseUpdate(t.x/(float)screen.camera.viewportWidth*p+0.5f, 0.5f-t.y/(float)screen.camera.viewportHeight, screen.pause);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        screen.setCursorPosition(new Vector2(screenX, screenY));
        //System.out.println(screenX);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        screen.scale(amountY);
        return true;
    }
}
