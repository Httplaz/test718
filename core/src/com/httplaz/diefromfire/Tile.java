package com.httplaz.diefromfire;

public class Tile
{
    public int pX;
    public int pY;
    public int priority;
    public String name;
    public int variability;
    public int id;
    public Tile(int x, int y, int p, int v, String n, int i)
    {
        pX = x;
        pY = y;
        priority = p;
        variability = v;
        name = n;
        id = i;
    }
}
