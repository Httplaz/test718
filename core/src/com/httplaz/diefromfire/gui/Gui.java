package com.httplaz.diefromfire.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.httplaz.diefromfire.MainScreen;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.Tiles;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.items.*;

import java.util.ArrayList;

public class Gui
{
    public SpriteBatch batch;
    BitmapFont font;
    Viewport viewport;

    public ArrayList<Button> buttons;

    public ArrayList<Button> buttonsP;
    Camera camera;

    public MainScreen screen;
    int hotbar[] = new int[4];
    int selectedHotbarSlot = -1;
    public boolean touchingVolume = false;
    public boolean touchingWater = false;

    public float ar = 1.f;

    Entity player;

    public Gui(Entity e)
    {
        player = e;
        camera = new OrthographicCamera(800, 480);
        camera.translate(400, 240, 0);
        viewport = new FitViewport(800, 480, camera);
        batch = new SpriteBatch();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        font = new BitmapFont(Gdx.files.internal("default.fnt"));
        buttons = new ArrayList<>();
        buttonsP = new ArrayList<>();
        float acpectRatio = 800.f/480.f;
        ar = acpectRatio;

        buttonsP.add(new Button(0.2f, 0.2f, 0.27f, 0.09f*acpectRatio, 6000, 0, "EXIT", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 32 48 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 32 48 16", TextureRegion.class))));
        buttonsP.add(new Button(0.2f, 0.4f, 0.27f, 0.09f*acpectRatio, 6000, 0, "EXIT", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 64 48 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 48 48 16", TextureRegion.class))));
        buttonsP.add(new Button(0.2f, 0.6f, 0.27f, 0.09f*acpectRatio, 6000, 0, "EXIT", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 32 48 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 32 48 16", TextureRegion.class))));
        buttonsP.add(new Button(0.2f, 0.6f, 0.27f, 0.09f*acpectRatio, 6000, 0, "EXIT", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 48 48 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 48 48 16", TextureRegion.class))));
        buttonsP.add(new Button(0.6f, 0.6f, 0.27f, 0.09f*acpectRatio, 6000, 0, "EXIT", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 64 48 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 64 48 16", TextureRegion.class))));
        buttonsP.add(new Button(0.6f, 0.6f, 0.27f, 0.09f*acpectRatio, 6000, 0, "EXIT", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 48 48 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 48 48 16", TextureRegion.class))));

        buttons.add(new Button(0.0f, 0.0f, 0.09f, 0.09f*acpectRatio, 6000, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:16 16 16 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:16 0 16 16", TextureRegion.class))));
        for(int i=1; i<5; i++)
            buttons.add(new Button(0.0f, 0.0f + 0.09f*acpectRatio*i, 0.09f, 0.09f*acpectRatio, 6000, 0, "", (TextureRegion)(Resourses.getResourse("textures/blank.png:0 0 8 8", TextureRegion.class)) , (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 16 16 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 0 16 16", TextureRegion.class))));
        buttons.add(new Button(0.91f, 0.0f, 0.09f, 0.09f*acpectRatio, 30, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:96 32 32 32", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:96 0 32 32", TextureRegion.class))));
        buttons.add(new Button(0.82f, 0.0f, 0.09f, 0.09f*acpectRatio, 30, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:96 32 32 32", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:96 0 32 32", TextureRegion.class))));
        buttons.add(new Button(0.91f, 0.09f*acpectRatio, 0.09f, 0.09f*acpectRatio, 6000, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:96 32 32 32", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:96 0 32 32", TextureRegion.class))));
        for (int j=0; j<6; j++)
            for(int i=0; i<5; i++)
                buttons.add(new Button(0.0f + 0.09f*(j+1), 0.0f + 0.09f*acpectRatio*(i), 0.09f, 0.09f*acpectRatio, 6000, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 16 16 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 0 16 16", TextureRegion.class))));
        buttons.add(new Button(0.18f, 0.0f, 0.34f, 0.05f*acpectRatio, 6000, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:32 16 16 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:32 16 16 16", TextureRegion.class))));
        buttons.add(new Button(0.18f, 0.0f, 0.34f, 0.05f*acpectRatio, 6000, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:32 0 16 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:32 0 16 16", TextureRegion.class))));

        buttons.add(new Button(0.18f, 0.08f, 0.34f, 0.05f*acpectRatio, 6000, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 16 16 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 16 16 16", TextureRegion.class))));
        buttons.add(new Button(0.18f, 0.08f, 0.34f, 0.05f*acpectRatio, 6000, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 0 16 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:48 0 16 16", TextureRegion.class))));
        //0:show inventory
        //1-4:select tile
        //5:proceed
        //6:abort
        //7:build rectangle
        //8-38:inventory
        for (int i=0; i<4; i++)
            hotbar[i] = i;
        for (int i=0; i<30; i++)
        {
            buttons.get(8+i).disable();
        }
        buttons.add(new Button(0.09f, 0.0f, 0.09f, 0.09f*acpectRatio, 6000, 0, "", null, (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 16 16 16", TextureRegion.class)), (TextureRegion) (Resourses.getResourse("textures/gui/buttons.png:0 0 16 16", TextureRegion.class))));

        prepareInventory();
    }

    public void draw()
    {
        batch.begin();
        font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond() + "\n entities: " + screen.map.entities.size(),0,0.5f*480);
        for (int i=0; i<buttons.size(); i++)
            if(buttons.get(i).enabled)
                buttons.get(i).draw(batch, 800, 480);
        batch.end();
    }

    public void pauseDraw()
    {
        batch.begin();
        for (int i=0; i<buttonsP.size(); i++)
            if(buttonsP.get(i).enabled)
                buttonsP.get(i).draw(batch, 800, 480);
        batch.end();
        buttonsP.get(3).width = Resourses.volume*0.27f;
        buttonsP.get(3).height = Resourses.pitch*0.09f*ar;
        buttonsP.get(5).width = Resourses.water*0.27f;
    }

    public void pauseUpdate(float cursorX, float cursorY, boolean touch)
    {
        if(touch)
        {
            if (buttonsP.get(1).intersects(cursorX, cursorY))
                buttonsP.get(1).active = buttonsP.get(1).active==0? buttonsP.get(1).activeTime : 0;
            if (buttonsP.get(0).intersects(cursorX, cursorY))
                screen.exit();
        }
        if (buttonsP.get(2).intersects(cursorX, cursorY) && touch)
            touchingVolume = true;

        if (buttonsP.get(4).intersects(cursorX, cursorY) && touch)
            touchingWater = true;
        else
            touchingWater = false;
        //System.out.println(touchingVolume);

        if(touchingVolume)
        {
            Vector2 f = new Vector2((cursorX-buttonsP.get(2).posX)/buttonsP.get(2).width, (cursorY-buttonsP.get(2).posY)/buttonsP.get(2).height);
            Resourses.volume = f.x;
            Resourses.pitch = f.y;
        }

        if(touchingWater)
        {
            Vector2 f = new Vector2((cursorX-buttonsP.get(4).posX)/buttonsP.get(4).width, (cursorY-buttonsP.get(4).posY)/buttonsP.get(4).height);
            Resourses.water = f.x;
        }
    }

    public boolean update(float cursorX, float cursorY, boolean touch)
    {
        buttons.get(42).setTexture(player.ability.texture);
        for (int i=0; i<30; i++)
        {
            buttons.get(i+8).setTexture(player.inventory.getItem(i).texture);
        }
        //buttons.get()
        for (int i=0; i<4; i++)
        {
            buttons.get(i+1).setTexture(player.inventory.getItem(hotbar[i]).texture);
        }

        if(buttons.get(6).active==1)
        {
            screen.fixateScheme(false);
            //screen.clearSchematic();
        }
        if(buttons.get(5).active==1)
            screen.fixateScheme(true);
        if(buttons.get(7).active>0)
            screen.rectangle = true;
        else
            screen.rectangle = false;
        //System.out.println(cursorX);
        //System.out.println(cursorY);
        boolean collision = false;
        if(buttons.get(0).active>0)
        {
            for (int i=8; i<38; i++)
                buttons.get(i).enable();
            for(int i=0; i<4; i++)
                buttons.get(buttons.size()-i-1).disable();
        }
        else
        {
            for (int i=8; i<38; i++)
                buttons.get(i).disable();
            for(int i=0; i<4; i++)
                buttons.get(buttons.size()-i-1).enable();
        }

        for (int i=0; i<buttons.size(); i++)
        {
            if(buttons.get(i).intersects(cursorX, cursorY) && buttons.get(i).enabled)
            {
                collision = true;
                //System.out.println("NIGGERS");
                if(touch)
                {
                    if(i<1 || i>4)
                        buttons.get(i).active = buttons.get(i).active<=0 ? buttons.get(i).activeTime : 0;
                    else
                        buttons.get(i).active = buttons.get(i).activeTime;
                    if(i>=1 && i<=4 && buttons.get(i).active>0)
                    {
                        for (int j=1; j<=4; j++)
                            if(j!=i)
                                buttons.get(j).active = 0;
                        if(buttons.get(i).active>0)
                        {
                            selectedHotbarSlot = i-1;
                            player.inventory.selectedItem = hotbar[selectedHotbarSlot];
                        }
                        else
                        {
                            selectedHotbarSlot = -1;
                            screen.buildingTile = 0;
                        }
                    }

                    if(i>=8 && i<=37 && buttons.get(i).active>0)
                    {
                        for (int j=8; j<=37; j++)
                            if(j!=i)
                                buttons.get(j).active = 0;
                        if(buttons.get(i).active>0)
                        {
                            if(selectedHotbarSlot>-1)
                            {
                                hotbar[selectedHotbarSlot] = i-8;
                                buttons.get(selectedHotbarSlot+1).setTexture(buttons.get(i).texture);
                                player.inventory.selectedItem = hotbar[selectedHotbarSlot];
                            }
                            //selectedHotbarSlot = i-1;
                            //screen.buildingTile = hotbar[selectedHotbarSlot];
                        }
                        else
                        {
                            //selectedHotbarSlot = -1;
                            screen.buildingTile = 0;
                        }
                    }

                }
                //else
                    //buttons.get(i
            }
                buttons.get(i).active += (buttons.get(i).active>0 && buttons.get(i).activeTime<5000)? -1:0;
        }
            //else

        return collision;
    }

    public void prepareInventory()
    {
        //player.inventory.addItem(new Murasama());
        //player.inventory.addItem(new Gun());
        //player.inventory.addItem(new Pickaxe());
        //player.inventory.addItem(new Flamethrower());
        //int slot = 8;
        for(int i=1; i<Tiles.content.size(); i++)
        {
            //Item item = new TileItem(i);
            //item.setId(i);
            //player.inventory.addItem(item);

        }
        /*player.inventory.addItem(new Pickaxe());
        buttons.get(slot).setTexture(player.inventory.getItem(player.inventory.itemsAvailable-1).texture);
        player.inventory.addItem(new Flamethrower());
        buttons.get(slot+1).setTexture(player.inventory.getItem(player.inventory.itemsAvailable-1).texture);
        player.inventory.addItem(new Gun());
        buttons.get(slot+2).setTexture(player.inventory.getItem(player.inventory.itemsAvailable-1).texture);
        player.inventory.addItem(new Murasama());
        buttons.get(slot+3).setTexture(player.inventory.getItem(player.inventory.itemsAvailable-1).texture);
        hotbar[0] = 11;
        hotbar[1] = 10;
        hotbar[2] = 12;
        hotbar[3] = 13;
        buttons.get(1).setTexture(player.inventory.getItem(hotbar[0]).texture);
        buttons.get(2).setTexture(player.inventory.getItem(hotbar[1]).texture);
        buttons.get(3).setTexture(player.inventory.getItem(hotbar[2]).texture);
        buttons.get(4).setTexture(player.inventory.getItem(hotbar[3]).texture);
        */
    }

    public void click(int buttonID)
    {

    }
}
