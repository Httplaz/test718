package com.httplaz.diefromfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jogamp.common.util.Bitstream;

import java.io.*;

public abstract class WorldSaver
{
    public static void fillChunk(final Chunk chunk, int cx, int cy)
    {
        //String locRoot = Gdx.files.getLocalStoragePath();
        //System.out.println(locRoot);
        //System.out.println("NIGGERS");
        final String name = "chunks/Chunk" + Integer.toString(cx) + " " + Integer.toString(cy);
        boolean exists = Gdx.files.local(name).exists();
        if(!exists)
        //if(true)
        {
            WorldGenerator.fillChunk(chunk, cx, cy);
            saveChunk(chunk, cx, cy);
        }
        else
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    FileHandle file = Gdx.files.local(name);
                    byte[] data = file.readBytes();

                    ByteArrayInputStream bis = new ByteArrayInputStream(data);
                    Chunk copy = new Chunk();
                    try
                    {
                        ObjectInputStream ois = new ObjectInputStream(bis);
                        copy = (Chunk)ois.readObject();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e){

                        e.printStackTrace();
                    }
                    final long[][] dt = copy.data;
                    Gdx.app.postRunnable(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            chunk.data = dt;
                            chunk.needsToUpdate = true;
                        }
                    });
                }
            }).start();
            /*FileHandle file = Gdx.files.local(name);
            byte[] data = file.readBytes();

            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            try
            {
                ObjectInputStream ois = new ObjectInputStream(bis);
                Chunk copy = (Chunk)ois.readObject();
                chunk.data = copy.data;
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }*/
        }
    }

    public static void saveChunk(Chunk toSave, final int cx, final int cy)
    {
        //if(false)
            //return;
        final Chunk chunk = new Chunk(toSave);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String name = "chunks/Chunk" + Integer.toString(cx) + " " + Integer.toString(cy);
                FileHandle file = Gdx.files.local(name);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(chunk);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte [] data = bos.toByteArray();
                file.writeBytes(data, false);
                // do something important here, asynchronously to the rendering thread
                /*Gdx.app.postRunnable(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                        results.add(result);
                    }
                });*/
            }
        }).start();
    }
}
