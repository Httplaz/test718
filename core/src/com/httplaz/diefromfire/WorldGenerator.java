package com.httplaz.diefromfire;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;

public abstract class WorldGenerator
{
    public static RandomXS128 random = new RandomXS128();
    static Vector2 offset = new Vector2();
    public static void randomise()
    {
        offset = new Vector2((random.nextFloat()-0.5f)*1000000.f, (random.nextFloat()-0.5f)*1000000.f);
    }
    public static void fillChunk(Chunk chunk, int cx, int cy)
    {
        long data[][] = new long[Chunk.size+2][Chunk.size+2];
        for (int i=-1; i<Chunk.size+1; i++)
            for (int j=-1; j<Chunk.size+1; j++)
            {
                float n = (float) SimplexNoise.noise((double) (i + cx * Chunk.size + offset.x) / 100.d, (double) (j + cy * Chunk.size + offset.y) / 100.d);
                Tile tile;
                {
                    if (n > 0.7)
                    {
                        if(random.nextInt(100)<=5)
                            tile = Tiles.getTile("redOre");
                        else if(random.nextInt(100)<=5)
                            tile = Tiles.getTile("blueOre");
                        else
                            tile = Tiles.getTile("darkStone");
                    }
                    else if (n > 0.6)
                        tile = Tiles.getTile("stone");
                    else if(n>-0.4)
                        tile = Tiles.getTile("grass");
                    else
                        tile = Tiles.getTile("water");
                }
                data[i+1][j+1] = tile.id + (16777216L-1L)*256L;

                //if(tile.name == "water")
                    //data[i+1][j+1] = tile.id + 256L*(48L+168L*256L+255L*256L*256L+255L*256L*256L*256L);
                //data[i+i][j+i] = tile.id + (long)(0.192156862745098f*255L*256) + (long)(0.6627450980392157f*65280L*256) + (long)(0.9333333333333333f*16711680L*256) + (long)(1.0f*4278190080L*256);

                //18-26
            }
        for (int i=0; i<Chunk.size; i++)
            for (int j=0; j<Chunk.size; j++)
            {
                chunk.data[i][j] = data[i+1][j+1];
            }
    }

    public static int getVariation(int x, int y, int v)
    {
        float n = (float) SimplexNoise.noise((double) (x+offset.x) / 10.d, (double) (y+offset.y) / 10.d);
        return ((int)((n+1.f)*50)%v);
    }

}
