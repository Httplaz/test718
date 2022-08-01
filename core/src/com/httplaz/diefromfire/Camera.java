package com.httplaz.diefromfire;

import com.badlogic.gdx.math.Vector2;

public class Camera
{
    Vector2 position;
    Vector2 target;
    float interpolation = 0.1f;

    public Camera(Vector2 p)
    {
        position = new Vector2(p);
        target = new Vector2(p);
    }

    public void update()
    {
            Vector2 dist = position.lerp(target, interpolation);
    }

    public void setTarget(Vector2 t)
    {
        target = new Vector2(t);
    }

    public Vector2 getPosition()
    {
        return new Vector2(position);
    }

    public void translateTarget(Vector2 v)
    {
        target.add(v);
    }

    public void translate(Vector2 v)
    {
        position.add(v);
        target.add(v);
    }

    public void setInterpolation(float n)
    {
        interpolation = n;
    }
}
