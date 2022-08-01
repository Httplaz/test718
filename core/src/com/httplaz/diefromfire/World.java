package com.httplaz.diefromfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.entities.mobs.Entity;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static com.badlogic.gdx.graphics.GL30.GL_TEXTURE_2D;

public class World {
    Chunk[][] loadedChunks;
    int width;
    int height;
    boolean needsToUpdate;
    Texture mapTexture;
    int glTarget;
    int glHandle;
    Texture mapTextureC;
    int glTargetC;
    int glHandleC;
    RandomXS128 random;
    public Vector2 chunkOffset;
    boolean autogen;

    int[] arr1;
    float[] arr1C;

    public int cooldown = 40;

    public int coins = 0;


    ByteBuffer b;
    ByteBuffer bC;
    SimplexNoise noise = new SimplexNoise();

    public ArrayList<Entity> entities;
    public ArrayList<Entity> decoratives;

    public World(int w, int h, boolean ag)
    {
        entities = new ArrayList<>();
        decoratives = new ArrayList<>();
        autogen = ag;
        chunkOffset = new Vector2(0, 0);
        random = new RandomXS128();
        width = w;
        height = h;
        loadedChunks = new Chunk[w][h];
        needsToUpdate = false;
        arr1 = new int[w*h*Chunk.size*Chunk.size*4];
        arr1C = new float[w*h*Chunk.size*Chunk.size*4];
    }

    public void generate() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < width; j++) {
                loadedChunks[i][j] = new Chunk();
                if (autogen)
                    WorldSaver.fillChunk(loadedChunks[i][j], i + (int) chunkOffset.x, j + (int) chunkOffset.y);
                else
                    for (int x = 0; x < Chunk.size; x++)
                        for (int y = 0; y < Chunk.size; y++)
                            loadedChunks[i][j].data[x][y] = 0;
            }

        glTarget = Gdx.graphics.getGL30().GL_TEXTURE_2D;
        glHandle = Gdx.graphics.getGL30().glGenTexture();
        Gdx.graphics.getGL30().glBindTexture(glTarget, glHandle);
        Gdx.graphics.getGL30().glTexParameteri(GL_TEXTURE_2D, Gdx.graphics.getGL30().GL_TEXTURE_WRAP_S, Gdx.graphics.getGL30().GL_CLAMP_TO_EDGE);
        Gdx.graphics.getGL30().glTexParameteri(GL_TEXTURE_2D, Gdx.graphics.getGL30().GL_TEXTURE_WRAP_T, Gdx.graphics.getGL30().GL_CLAMP_TO_EDGE);
        Gdx.graphics.getGL30().glTexParameteri(GL_TEXTURE_2D, Gdx.graphics.getGL30().GL_TEXTURE_MIN_FILTER, (Gdx.graphics.getGL30().GL_NEAREST));
        Gdx.graphics.getGL30().glTexParameteri(GL_TEXTURE_2D, Gdx.graphics.getGL30().GL_TEXTURE_MAG_FILTER, (Gdx.graphics.getGL30().GL_NEAREST));
        Gdx.graphics.getGL30().glBindTexture(GL_TEXTURE_2D, 0);
        b = ByteBuffer.allocateDirect(4 * arr1.length).order(ByteOrder.nativeOrder());

        glTargetC = Gdx.graphics.getGL30().GL_TEXTURE_2D;
        glHandleC = Gdx.graphics.getGL30().glGenTexture();
        Gdx.graphics.getGL30().glBindTexture(glTargetC, glHandleC);
        Gdx.graphics.getGL30().glTexParameteri(GL_TEXTURE_2D, Gdx.graphics.getGL30().GL_TEXTURE_WRAP_S, Gdx.graphics.getGL30().GL_CLAMP_TO_EDGE);
        Gdx.graphics.getGL30().glTexParameteri(GL_TEXTURE_2D, Gdx.graphics.getGL30().GL_TEXTURE_WRAP_T, Gdx.graphics.getGL30().GL_CLAMP_TO_EDGE);
        Gdx.graphics.getGL30().glTexParameteri(GL_TEXTURE_2D, Gdx.graphics.getGL30().GL_TEXTURE_MIN_FILTER, (Gdx.graphics.getGL30().GL_NEAREST));
        Gdx.graphics.getGL30().glTexParameteri(GL_TEXTURE_2D, Gdx.graphics.getGL30().GL_TEXTURE_MAG_FILTER, (Gdx.graphics.getGL30().GL_NEAREST));
        Gdx.graphics.getGL30().glBindTexture(GL_TEXTURE_2D, 0);
        bC = ByteBuffer.allocateDirect(4 * arr1C.length).order(ByteOrder.nativeOrder());
        updateTexture();
    }

    public void updateChunks(Camera camera, boolean tr) {
        Vector2 pos = camera.getPosition();
        Vector2 cOffset = new Vector2(0, 0);
        if (pos.x > width / 2 * Chunk.size + Chunk.size) {
            if (tr)
                camera.translate(new Vector2(-Chunk.size, 0));
            cOffset.x++;
        }
        if (pos.y > height * Chunk.size / 2 + Chunk.size) {
            if (tr)
                camera.translate(new Vector2(0, -Chunk.size));
            cOffset.y++;
        }
        if (pos.x < width / 2 * Chunk.size - Chunk.size) {
            if (tr)
                camera.translate(new Vector2(Chunk.size, 0));
            cOffset.x--;
        }
        if (pos.y < height * Chunk.size / 2 - Chunk.size) {
            if (tr)
                camera.translate(new Vector2(0, Chunk.size));
            cOffset.y--;
        }
        //cOffset.scl(0);
        if (cOffset.x != 0 || cOffset.y != 0) {
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++) {
                    if (loadedChunks[i][j].changed)
                    {
                        if (autogen)
                            WorldSaver.saveChunk(loadedChunks[i][j], i + (int) chunkOffset.x, j + (int) chunkOffset.y);
                        loadedChunks[i][j].changed = false;
                    }
                }
            Chunk[][] newLoadedChunks = new Chunk[width][height];
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++) {
                    int posInLCX = i + (int) cOffset.x;
                    int posInLCY = j + (int) cOffset.y;
                    if (posInLCX >= 0 && posInLCX < width && posInLCY >= 0 && posInLCY < height)
                        newLoadedChunks[i][j] = loadedChunks[posInLCX][posInLCY];
                    else {
                        newLoadedChunks[i][j] = new Chunk();
                        if (autogen)
                            WorldSaver.fillChunk(newLoadedChunks[i][j], posInLCX + (int) chunkOffset.x, posInLCY + (int) chunkOffset.y);
                        else
                            for (int x = 0; x < Chunk.size; x++)
                                for (int y = 0; y < Chunk.size; y++)
                                    newLoadedChunks[i][j].data[x][y] = 0;
                    }
                }
            cooldown = 40;
            loadedChunks = newLoadedChunks;
            chunkOffset.add(cOffset);
            updateTexture();
            //System.out.println(chunkOffset);
        }
    }

    public void save()
    {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
            {
                {
                    if (autogen)
                        WorldSaver.saveChunk(loadedChunks[i][j], i + (int) chunkOffset.x, j + (int) chunkOffset.y);

                }
            }
    }

    void update()
    {
        cooldown--;
        for (int i=0; i<width; i++)
            for (int j=0; j<height; j++)
                if(loadedChunks[i][j].needsToUpdate)
                {
                    loadedChunks[i][j].needsToUpdate = false;
                    needsToUpdate = true;
                }
        if (needsToUpdate)
        {
            updateTexture();
            needsToUpdate = false;
        }
    }

    public void addEntity(Entity e)
    {
        entities.add(e);
    }

    public void removeEntity(Entity e)
    {
        entities.remove(e);
    }

    public void addDecorative(Entity e)
    {
        if(decoratives.size()>=100)
            decoratives.remove(0);
        decoratives.add(e);
    }

    void updateTexture()
    {
        int badTiles = 0;
        for (int i = 0; i < width * Chunk.size; i++)
        {
            for (int j = 0; j < height * Chunk.size; j++)
            {
                int id = getTile(i, j)%256;
                Tile tile = Tiles.getTile(id);
                arr1[(j*width*Chunk.size+i)*4] = tile.pX + WorldGenerator.getVariation((int) (chunkOffset.x*Chunk.size+i), (int) (chunkOffset.y*Chunk.size+j), tile.variability)*1024 + tile.priority*1048576;
                //arr1[(j*width*Chunk.size+i)*4+1] = WorldGenerator.getVariation((int) (chunkOffset.x*Chunk.size+i), (int) (chunkOffset.y*Chunk.size+j), tile.variability);
                //arr1[(j*width*Chunk.size+i)*4+2] = tile.priority;
                long code = getColorCode(i,j);
                arr1C[(j*width*Chunk.size+i)*4+3] = (int)(code>>24)/255.f;
                code&=16777215;
                int b1 = (int)(code>>16);
                arr1C[(j*width*Chunk.size+i)*4+2] = b1/255.f;
                code&=65535;
                arr1C[(j*width*Chunk.size+i)*4+1] = (int)(code>>8)/255.f;
                code&=255;
                if(code<255)
                    badTiles++;
                arr1C[(j*width*Chunk.size+i)*4] = (int)(code)/255.f;
            }
        }
        //System.out.println(badTiles);
        uploadTexture();
    }

    public void updateTextureTileColor(int x, int y, float[] color)
    {
        arr1C[(y*width*Chunk.size+x)*4+3] = color[3];
        arr1C[(y*width*Chunk.size+x)*4+2] = color[2];
        arr1C[(y*width*Chunk.size+x)*4+1] = color[1];
        arr1C[(y*width*Chunk.size+x)*4+0] = color[0];
    }

    void updateTextureSub(int x0, int y0, int x1, int y1)
    {
        for (int i = x0; i < x1; i++)
        {
            for (int j = y0; j < y1; j++)
            {
                int id = getTile(i, j)&255;
                Tile tile = Tiles.getTile(id);
                arr1[(j*width*Chunk.size+i)*4] = tile.pX + WorldGenerator.getVariation((int) (chunkOffset.x*Chunk.size+i), (int) (chunkOffset.y*Chunk.size+j), tile.variability)*1024 + tile.priority*1048576;
                long code = getColorCode(i,j);
                arr1C[(j*width*Chunk.size+i)*4+3] = (code>>24)/255.f;
                code&=16777215;
                int b1 = (int)(code>>16);
                arr1C[(j*width*Chunk.size+i)*4+2] = b1/255.f;
                code&=65535;
                arr1C[(j*width*Chunk.size+i)*4+1] = (code>>8)/255.f;
                code&=255;
                arr1C[(j*width*Chunk.size+i)*4+0] = (code)/255.f;
            }
        }
    }

    public void bindTexture() {
        Gdx.gl.glActiveTexture(Gdx.graphics.getGL30().GL_TEXTURE0 + 2);
        Gdx.graphics.getGL30().glBindTexture(glTarget, glHandle);

    }

    public void uploadTexture()
    {
        Gdx.graphics.getGL30().glBindTexture(glTarget, glHandle);
        ((Buffer)b).clear();
        b.asIntBuffer().put(arr1);
        Gdx.graphics.getGL30().glTexImage2D(glTarget, 0, Gdx.graphics.getGL30().GL_RGBA32I, width*Chunk.size, height*Chunk.size, 0, Gdx.graphics.getGL30().GL_RGBA_INTEGER, Gdx.graphics.getGL30().GL_INT, b);

    }

    public void uploadColorTexture()
    {
        Gdx.graphics.getGL30().glBindTexture(glTargetC, glHandleC);
        ((Buffer)bC).clear();
        bC.asFloatBuffer().put(arr1C);
        Gdx.graphics.getGL30().glTexImage2D(glTargetC, 0, Gdx.graphics.getGL30().GL_RGBA32F, width*Chunk.size, height*Chunk.size, 0, Gdx.graphics.getGL30().GL_RGBA, Gdx.graphics.getGL30().GL_FLOAT, bC);
    }

    public void bindColorTexture()
    {
        Gdx.gl.glActiveTexture(Gdx.graphics.getGL30().GL_TEXTURE0 + 3);

        Gdx.graphics.getGL30().glBindTexture(glTargetC, glHandleC);

    }


    public Texture getTexture() {
        return mapTexture;
    }

    public void setTile(int rx, int ry, int tile)
    {
        if (rx / Chunk.size >= width || ry / Chunk.size >= height || rx < 0 || ry < 0)
            return;
        loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size] = tile + (16777216L-1L)*256L;// + loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size]%64;

        loadedChunks[rx / Chunk.size][ry / Chunk.size].changed = true;
        //WorldSaver.saveChunk(loadedChunks[rx/Chunk.size][ry/Chunk.size], rx+(int)chunkOffset.x, ry+(int)chunkOffset.y);
        needsToUpdate = true;
    }

    public int[] getColor(long colorCode)
    {
        int color1[] = new int[4];
        color1[3] = (int)(colorCode/16777216L);
        colorCode%=16777216;
        color1[2] = (int)(colorCode/65536L);
        colorCode%=65536;
        color1[1] = (int)(colorCode/256L);
        colorCode%=256;
        color1[0] = (int)colorCode;
        return color1;
    }

    float[] addC = new float[4];

    public void addColor(int rx, int ry, int[] color)
    {
        if (rx / Chunk.size >= width || ry / Chunk.size >= height || rx < 0 || ry < 0)
            return;
        //for (int i=0; i<4; i++)
            //color[i]/=255.f;
        long tile = loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size]%256;
        long colorCode = loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size]/256;
        int color1[] = new int[4];
        color1[3] = (int)(colorCode>>24);
        colorCode&=16777215;
        color1[2] = (int)(colorCode>>16);
        colorCode&=65535;
        color1[1] = (int)(colorCode>>8);
        colorCode&=255;
        color1[0] = (int)colorCode;
        //System.out.println(color1[1] + " " + color[1]);
        for (int i=0; i<3; i++)
        {
            color1[i] = 255-( Math.min(255, 255 - color1[i] + 255 - color[i]));
        }
        //color1[0] = 250;
        color1[3] = Math.min(255, color[3]+color1[3]);

        //System.out.println(Arrays.toString(color1));
        //System.out.println(color1[0]);
        //System.out.println(color1[1]);
        //System.out.println(color1[2]);
        //System.out.println(color1[3]);
        long colorCodeNew = (color1[0] + ((long)color1[1]<<8L) + ((long)color1[2] << 16L) + ((long)color1[3] << 24L));
        loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size] = tile + colorCodeNew*256;
        //System.out.println(loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size]);

        loadedChunks[rx / Chunk.size][ry / Chunk.size].changed = true;
        //WorldSaver.saveChunk(loadedChunks[rx/Chunk.size][ry/Chunk.size], rx+(int)chunkOffset.x, ry+(int)chunkOffset.y);\
        addC[0] = color1[0]/255.f;
        addC[1] = color1[1]/255.f;
        addC[2] = color1[2]/255.f;
        addC[3] = color1[3]/255.f;
        updateTextureTileColor(rx, ry, addC);
        //needsToUpdate = true;
    }

    public void setColor(int rx, int ry, int[] color, boolean update)
    {
        if (rx / Chunk.size >= width || ry / Chunk.size >= height || rx < 0 || ry < 0)
            return;
        //for (int i=0; i<4; i++)
        //color[i]/=255.f;
        long tile = loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size]%256;
        long colorCodeNew = (color[0] + ((long)color[1]<<8L) + ((long)color[2] << 16L) + ((long)color[3] << 24L));
        loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size] = tile + colorCodeNew*256;
        //System.out.println(loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size]);

        //loadedChunks[rx / Chunk.size][ry / Chunk.size].changed = true;
        //WorldSaver.saveChunk(loadedChunks[rx/Chunk.size][ry/Chunk.size], rx+(int)chunkOffset.x, ry+(int)chunkOffset.y);
        if(update)
            updateTextureSub(rx, ry, rx+1, ry+1);
        //needsToUpdate = true;
    }

    public boolean breakTile(int rx, int ry)
    {
        if (rx / Chunk.size >= width || ry / Chunk.size >= height || rx < 0 || ry < 0)
            return false;
        Tile t = Tiles.getTile(getTile(rx,ry));
        if(t.priority<3)
            return false;
        t = Tiles.getTile("floor"+t.name.substring(0,1).toUpperCase(Locale.ROOT)+t.name.substring(1));
        if(t==null)
            t = Tiles.getTile("floorDarkStone");
        loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size] = t.id;

        loadedChunks[rx / Chunk.size][ry / Chunk.size].changed = true;
        //WorldSaver.saveChunk(loadedChunks[rx/Chunk.size][ry/Chunk.size], rx+(int)chunkOffset.x, ry+(int)chunkOffset.y);
        needsToUpdate = true;
        return true;
    }

    public int getTile(int rx, int ry)
    {
        return (int)(loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size]%256L);
    }

    public long getColorCode(int rx, int ry)
    {
        //System.out.println(loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size]);
        return loadedChunks[rx / Chunk.size][ry / Chunk.size].data[rx % Chunk.size][ry % Chunk.size]/256;
    }

    public int getWidth() {
        return width;
    }

    public int getTileWidth()
    {
        return width*Chunk.size;
    }

    public int getTileHeight()
    {
        return height*Chunk.size;
    }

    public Vector2 getTileOffset() {
        return new Vector2(chunkOffset).scl(Chunk.size);
    }

    public int getHeight() {
        return height;
    }

    public float[] encodeTile(int tile)
    {
        float[] res = new float[4];
        int a = 8;
        int b = 512;
        for (int i = 0; i < 4; i++)
        {
            res[i] = (tile % 512) / 8.f;
            tile = tile>>4;
        }
        return res;
    }
}