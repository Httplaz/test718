package com.httplaz.diefromfire;

import java.io.Serializable;


public class Chunk implements Serializable
{
    public static final int size = 32;
    public long data[][];
    public boolean changed = false;
    public boolean needsToUpdate = false;
    public Chunk()
    {
        data = new long[size][size];
    }
    public Chunk(Chunk copy)
    {
        data = new long[size][size];
        for (int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                data[i][j] = copy.data[i][j];
    }
}
