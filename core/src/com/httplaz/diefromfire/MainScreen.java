package com.httplaz.diefromfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.httplaz.diefromfire.entities.*;
import com.httplaz.diefromfire.entities.bullets.Bullet;
import com.httplaz.diefromfire.entities.interactives.Dispenser;
import com.httplaz.diefromfire.entities.interactives.Interactive;
import com.httplaz.diefromfire.entities.interactives.ItemPickup;
import com.httplaz.diefromfire.entities.interactives.WaveTrigger;
import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.entities.mobs.Flower;
import com.httplaz.diefromfire.entities.mobs.Zombie;
import com.httplaz.diefromfire.gui.Gui;
import com.httplaz.diefromfire.items.*;
import com.httplaz.diefromfire.items.abilities.*;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class MainScreen implements Screen {
    SpriteBatch worldBatch;
    SpriteBatch batch;
    SpriteBatch sample;
    Texture img;
    Camera camera;
    Viewport viewport;
    ShaderProgram shader;
    ShaderProgram colorShader;
    ShaderProgram schematicShader;
    RandomXS128 random;

    Vector2 cursorPosition;
    float scale = 32;
    float aspectRatio = 1;

    public World map;

    public World schematicMap;


    public boolean lmb = false;
    public boolean rmb = false;

    public Vector2 velocity;
    com.httplaz.diefromfire.Camera cam;


    BitmapFont font;

    InputHandler inputHandler;
    Gui gui;

    final int WINDOWX = 800;
    final int WINDOWY = 480;

    boolean fillRect = false;
    public int buildingTile = -1;
    public Item item;

    public boolean interacting = false;

    public Vector2 startPoint = new Vector2(-1, -1);
    public Vector2 stopPoint = new Vector2(-1, -1);
    public Boolean rectangle = false;

    public ArrayDeque<int[]> buildingTasks = new ArrayDeque<>();
    public boolean building = false;

    public ArrayDeque<int[]> temporaryBuildingTasks = new ArrayDeque<>();


    public ArrayList<Prop> props = new ArrayList<>();
    //public ArrayList<Entity> entities = new ArrayList<>();
    //public ArrayList<AI> ai = new ArrayList<>();

    Texture texture;

    public Entity player;


    public boolean itemUse = false;

    public int time = 0;

    SpriteBatch v1;

    int globalX = 0;
    int globalY = 0;

    Pixmap p = new Pixmap(256, 256, Pixmap.Format.RGBA8888);
    boolean pause = false;

    @Override
    public void show() {

        WorldGenerator.randomise();
        v1 = new SpriteBatch();
        //emitter.start();
        //props.add(new Prop(new Vector2(128.25f,124.8f), (TextureRegion)Resourses.getResourse("textures/props/houseProps.png:16 0 16 16", TextureRegion.class), 0.6f));
        Tiles.addTile(0, 0, 0, 1, "air");
        Tiles.addTile(1, 0, 0, 4, "water");
        Tiles.addTile(2, 0, 1, 4, "grass");
        Tiles.addTile(3, 0, 3, 4, "stone");
        Tiles.addTile(4, 0, 3, 4, "darkStone");

        Tiles.addTile(7, 0, 3, 1, "planks");
        Tiles.addTile(8, 0, 1, 1, "floorPlanks");
        Tiles.addTile(9, 0, 3, 1, "redOre");
        Tiles.addTile(10, 0, 3, 1, "blueOre");
        Tiles.addTile(5, 0, 1, 1, "floorStone");
        Tiles.addTile(6, 0, 1, 1, "floorDarkStone");
        Tiles.addTile(11, 0, 1, 1, "burnedGround");


        cursorPosition = new Vector2(0, 0);
        inputHandler = new InputHandler(this);
        velocity = new Vector2(0, 0f);
        random = new RandomXS128();
        font = new BitmapFont(Gdx.files.internal("default.fnt"));
        map = new World(8, 8, true);
        schematicMap = new World(8, 8, false);
        cam = new com.httplaz.diefromfire.Camera(new Vector2(map.width * Chunk.size / 2, map.height * Chunk.size / 2));
        cam.setInterpolation(0.09f);
        map.generate();
        schematicMap.generate();
        worldBatch = new SpriteBatch();
        batch = new SpriteBatch();
        sample = new SpriteBatch();
        img = new Texture("textures/tiles/tileset.png");
        camera = new OrthographicCamera(WINDOWX, WINDOWY);
        viewport = new FitViewport(WINDOWX, WINDOWY, camera);
        texture = new Texture("entity.png");
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("vert.glsl"), (Gdx.files.internal("frag.glsl")));
        if (!shader.isCompiled()) {
            System.out.println(shader.getLog());
            //System.exit(0);
        }
        schematicShader = new ShaderProgram(Gdx.files.internal("vert.glsl"), (Gdx.files.internal("scfrag.glsl")));
        if (!schematicShader.isCompiled()) {
            System.out.println(schematicShader.getLog());
            //System.exit(0);
        }

        colorShader = new ShaderProgram(Gdx.files.internal("colorsVert.glsl"), (Gdx.files.internal("colorsFrag.glsl")));
        if (!colorShader.isCompiled()) {
            System.out.println(colorShader.getLog());
            //System.exit(0);
        }


        //worldBatch.setShader(shader);
        Gdx.input.setInputProcessor(inputHandler);
        aspectRatio = (float) viewport.getScreenWidth() / (float) viewport.getScreenHeight();


        player = new Flower(new Vector2(128.25f, 124.8f), map);
        map.addEntity(new WaveTrigger(new Vector2(130, 130), map));
        map.entities.get(map.entities.size() - 1).setFaction(4);
        /*map.addEntity(new ItemPickup(new Vector2(124, 128), new Murasama(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(124, 126), new Gun(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(126, 126), new Pickaxe(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(122, 126), new Claw(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(120, 126), new Muramasa(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(118, 126), new GreenLightsword(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(116, 126), new Lasergun(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(124, 127), new ForceBlow(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(124, 124), new Dash(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(125, 124), new Healer(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(126, 124), new PlasmaBeam(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);
        map.addEntity(new ItemPickup(new Vector2(126, 125), new Teleporter(), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);*/
        map.addEntity(new Dispenser(new Vector2(120, 120), map));
        map.entities.get(map.entities.size() - 1).setFaction(2);

        for (int i = 1; i < Tiles.content.size(); i++) {
            Item item = new TileItem(i);
            item.setId(i);
            map.addEntity(new ItemPickup(new Vector2(124 - 2 * i, 128), item, map));
            map.entities.get(map.entities.size() - 1).setFaction(2);

        }
        map.addEntity(player);
        for (int i = 0; i < 0; i++) {
            map.addEntity(new Zombie(new Vector2(WorldGenerator.random.nextInt(map.getTileWidth()), WorldGenerator.random.nextInt(map.getTileHeight()) / 4), map));
            map.entities.get(map.entities.size() - 1).giveAi(new AI(map.entities.get(map.entities.size() - 1)));
            map.entities.get(map.entities.size() - 1).setTarget(player.getWorldPosition());
            map.entities.get(map.entities.size() - 1).setFaction(1);
            //ai.add(new AI(entities.get(i)));
        }
        gui = new Gui(player);
        gui.screen = this;
        for (int i=-20; i<=20; i++)
            for (int j=-20; j<=20; j++)
                map.breakTile((int)player.getWorldPosition().x+i, (int)player.getWorldPosition().y+j);
    }

    public void update() {

    }

    private FrameBuffer fboColor = null, fboColorS = null, fbo = null, fboS = null;
    private TextureRegion fboRegion = null;
    private SpriteBatch testBatch = new SpriteBatch();
    SpriteBatch gay = new SpriteBatch();


    public void doColors()
    {
        if(fbo == null)
        {
            fbo = new FrameBuffer(Pixmap.Format.RGBA8888, 256, 256, false);
            fboS = new FrameBuffer(Pixmap.Format.RGB565, 256, 256, false);
            fboColor = new FrameBuffer(Pixmap.Format.RGB565, 256, 256, false);
            fboColorS = new FrameBuffer(Pixmap.Format.RGB565, 256, 256, false);
        }
        fbo.begin();
        ScreenUtils.clear(Color.CLEAR);
        testBatch.setShader(colorShader);
        testBatch.setBlendFunction(GL30.GL_ONE, GL30.GL_ONE_MINUS_SRC_ALPHA);
        map.bindColorTexture();
        testBatch.begin();
        map.bindColorTexture();
        //fbo.getColorBufferTexture().bind();
        colorShader.setUniformi("colorTexture", 3);
        map.bindTexture();
        colorShader.setUniformi("u_texture", 2);
        ((Texture)(Resourses.getResourse("textures/tiles/tileset.png", Texture.class))).bind(0);
        colorShader.setUniformi("size", map.getTileWidth());
        colorShader.setUniformi("alphaMode", 0);
        testBatch.draw(img, 0, 0, 800, 480);
        testBatch.setShader(null); //default ShaderProgram
        testBatch.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA); //default blend mode

        testBatch.end();
        byte[] d = ScreenUtils.getFrameBufferPixels(0,0,256,256,true);
        fbo.end();


        ByteBuffer b = p.getPixels();
        //b.clear();
        ((Buffer)b).clear();
        b.put(d);
        ((Buffer)b).position(0);

        Color c = new Color();
        float[] replace = new float[4];
        if(time%(61-(int)(60.f*Resourses.water))==0)
            for (int i=0; i<256; i++)
                for (int j=0; j<256; j++)
                {
                    if(Tiles.getTile(map.getTile(i, j)).name=="water")
                    {
                        Color.rgba8888ToColor(c, p.getPixel(i, j));
                        map.setColor(i, j, new int[]{(int) (c.r * 255), (int) (c.g * 255), (int) (c.b*255), (int) (c.a*255)}, false);
                        replace[0] = c.r;
                        replace[1] = c.g;
                        replace[2] = c.b;
                        replace[3] = c.a;
                        map.updateTextureTileColor(i,j, replace);
                    }
                }

        //((Buffer)b).clear();
    }

    public void draw()
    {
        map.uploadTexture();
        map.uploadColorTexture();
        //.out.println(map.getColorCode(120,120)/16777216);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        time++;


        ScreenUtils.clear(1,1,1,1);
        drawMap(1, 0, 0);
        drawSchematicMap();

        sample.setProjectionMatrix(camera.combined);
        sample.begin();
        float p1 = (camera.viewportWidth/camera.viewportHeight)/(800.f/480.f);
        float pixelsPerTile = (float)(float)viewport.getScreenHeight()/scale;
        for (int i=0; i<map.decoratives.size(); i++)
            map.decoratives.get(i).draw(sample, new Vector2 (pixelsPerTile*(-cam.getPosition().x)*p1, pixelsPerTile*(-cam.getPosition().y)), new Vector2(pixelsPerTile*p1, pixelsPerTile));
        sample.end();
        drawMap(0, 0, 1);
        sample.begin();
        for (int i=0; i<map.entities.size(); i++)
            map.entities.get(i).draw(sample, new Vector2 (pixelsPerTile*(-cam.getPosition().x)*p1, pixelsPerTile*(-cam.getPosition().y)), new Vector2(pixelsPerTile*p1, pixelsPerTile));
        sample.end();
        drawMap(0,1, 0);
        if(!pause)
            gui.draw();
        else
            gui.pauseDraw();
    }

    @Override
    public void render(float delta)
    {
        //System.out.println(viewport.getScreenWidth());
        //System.out.println(viewport.getScreenWidth());
        //System.out.println(player.getPosition());
        //player.speed = 0.1f;
        if(!pause) {
            if (item instanceof TileItem)
                buildingTile = ((TileItem) item).id;
            aspectRatio = (float) viewport.getScreenWidth() / (float) viewport.getScreenHeight();
            cam.translateTarget(new Vector2(velocity).scl(scale / 5.f));
            if (itemUse)
                player.inventory.useItem();
                //player.ability.use(player, map);
            else
                player.inventory.noUseItem();
            if (map.cooldown <= 0)
                doColors();
            for (int i = 0; i < map.entities.size(); i++) {
                Entity e = map.entities.get(i);
                if (e.framesAlive > -1 && e.getWorldPosition().x > 0 && e.getWorldPosition().y > 0 && e.getWorldPosition().x < map.getTileWidth() && e.getWorldPosition().y < map.getTileHeight())
                    e.update(map);
            }
            for (int i = 0; i < map.decoratives.size(); i++) {
                Entity e = map.decoratives.get(i);
                if (e.getWorldPosition().x > 0 && e.getWorldPosition().y > 0 && e.getWorldPosition().x < map.getTileWidth() && e.getWorldPosition().y < map.getTileHeight())
                    e.update(map);
            }
            for (int i = 0; i < map.entities.size(); i++) {
                for (int j = 0; j < map.entities.size() && i < map.entities.size(); j++) {
                    Entity a = map.entities.get(i);
                    Entity b = map.entities.get(j);
                    boolean overlap;
                    if (a.collider == null) {
                        if (b.collider == null)
                            overlap = (a.getPosition().dst2(b.getPosition()) <= a.collisionRadius * a.collisionRadius + b.collisionRadius * b.collisionRadius);
                        else {
                            float rotation = b.getRotationFloat();
                            Vector2 dist = a.getPosition().sub(b.getPosition()).rotateDeg(-rotation);
                            overlap = b.collider.overlaps(a.collisionRadius, b.getPosition(), b.getPosition().add(dist));
                        }
                        //overlap = b.collider.overlaps(a.collisionRadius, b.getPosition(), a.getPosition());
                    } else {
                        if (b.collider == null) {
                            float rotation = a.getRotationFloat();
                            Vector2 dist = b.getPosition().sub(a.getPosition()).rotateDeg(-rotation);
                            overlap = a.collider.overlaps(b.collisionRadius, a.getPosition(), a.getPosition().add(dist));
                        } else
                            overlap = a.collider.overlaps(b.collider, a.getPosition(), b.getPosition());
                    }
                    if (overlap && a.faction != b.faction) {
                        if (a instanceof Bullet && !(b instanceof Bullet))
                            ((Bullet) a).Hit(b);
                        else if (a instanceof Bullet)
                            if (((Bullet) a).destructible && b.reflectsBullets)
                                ((Bullet) a).Hit(b);
                        //System.out.println(interacting);
                        if (a == player && b instanceof Interactive && interacting)
                            ((Interactive) b).Interact(a);
                        if (a == player && b instanceof Interactive)
                            ((Interactive) b).showMessage = true;
                    }
                }
            }
            //player.update(map);
            cam.setTarget(player.getPosition().add(map.getTileOffset().scl(-1)));
            cam.update();
            map.update();
            schematicMap.update();
            map.updateChunks(cam, false);
            schematicMap.updateChunks(cam, true);
            Vector3 t = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            t = camera.unproject(t);
            //screen.gui.update((float)screenX/(float)Gdx.graphics.getWidth(), (float)screenY/(float)Gdx.graphics.getHeight(), true);
            float k = Gdx.graphics.getHeight() / 480.f;
            float p = Gdx.graphics.getWidth() / 800.f / k;
            building = !gui.update(t.x / (float) camera.viewportWidth * p + 0.5f, 0.5f - t.y / (float) camera.viewportHeight, false);

            //building = !gui.update(cursorPosition.x,cursorPosition.y,false);
            //System.out.println(building);
            float pixelsPerTile = (float) (float) viewport.getScreenHeight() / scale;
            Vector2 cursorTilePos = new Vector2(cursorPosition).scl(1.f / pixelsPerTile);
            Vector2 tilePos = new Vector2(cam.getPosition().x + cursorTilePos.x, cam.getPosition().y + cursorTilePos.y);
            player.setTarget(tilePos.add(map.getTileOffset()));

            player.setRotation(cursorPosition.angleDeg());

            if (player.inventory.selectedItem >= 0)
                if (player.inventory.getItem(player.inventory.selectedItem) instanceof TileItem) {
                    startPoint = new Vector2(tilePos);
                    stopPoint = new Vector2(tilePos);
                }
            //player.setRotation((60.f));

            //if(lmb && building)
            //map.setTile((int) (tilePos.x), (int) tilePos.y, 9);
            if (!rectangle && lmb && buildingTile >= 0 && building && tilePos.x >= 0 && tilePos.y >= 0 && tilePos.x < map.width * Chunk.size && tilePos.y < map.height * Chunk.size) {
                //map.setTile((int) (tilePos.x), (int) tilePos.y, 5);
                if (schematicMap.getTile((int) (tilePos.x), (int) tilePos.y) != buildingTile) {
                    schematicMap.setTile((int) (tilePos.x), (int) tilePos.y, buildingTile);

                    //if(!rectangle)
                    temporaryBuildingTasks.addLast(new int[]{(int) (tilePos.x + map.chunkOffset.x * Chunk.size), (int) (tilePos.y + map.chunkOffset.y * Chunk.size), buildingTile});
                }
                batch.setProjectionMatrix(camera.combined);
            }

            if (!buildingTasks.isEmpty()) {
                int[] task = buildingTasks.pollFirst();
                map.setTile(task[0] - (int) map.chunkOffset.x * Chunk.size, task[1] - (int) map.chunkOffset.y * Chunk.size, task[2]);
                schematicMap.setTile(task[0] - (int) map.chunkOffset.x * Chunk.size, task[1] - (int) map.chunkOffset.y * Chunk.size, 0);
            }

            //if(lmb && rectangle && building)
            //continueRectangle();
            //drawSchematicMap();

            //System.out.println(buildingTile>=0 && lmb && building);
            //System.out.println(tilePos);
            //v1.begin();
            //effect.draw(v1);
            //v1.end();
            //System.out.println(Resourses.resourses.size());
        }
        draw();
        for (int i=0; i<map.entities.size(); i++)
        {
            Entity e = map.entities.get(i);
            if(map.entities.get(i).faction==1)
            e.setTarget(e.target.lerp(player.getPosition(), 0.1f));
        }

        if(WorldGenerator.random.nextInt(1000)==2000)
        {
            map.addEntity(new Zombie(new Vector2(WorldGenerator.random.nextInt(6)*20-50+player.getPosition().x, WorldGenerator.random.nextInt(6)*20-50+player.getPosition().y), map));
            map.entities.get(map.entities.size()-1).giveAi(new AI(map.entities.get(map.entities.size()-1)));
            map.entities.get(map.entities.size()-1).setTarget(player.getPosition());
            map.entities.get(map.entities.size()-1).setFaction(1);
        }

        //map.addColor((int)player.getWorldPosition().x, (int)player.getWorldPosition().y, new float[]{255, 255, 255, 255});
        //map.updateTexture();
        gui.buttons.get(38).width = 0.34f*player.health/(float)player.maxHealth;
        gui.buttons.get(40).width = 0.34f*player.stamina/(float)player.maxStamina;
        if(!player.usingAbility && rmb && !pause)
            player.useAbility();

    }

    public void exit()
    {
        //map.save();
        if(gui.buttonsP.get(1).active==0)
            Gdx.files.local("chunks/").deleteDirectory();
        else
            map.save();
        Gdx.app.exit();
    }

    public void startRectangle()
    {
        rectangle = true;
        startPoint = getTilePos().add(schematicMap.getTileOffset());
        stopPoint = getTilePos().add(schematicMap.getTileOffset());
        //System.out.println("NOOO");
    }

    public void continueRectangle()
    {
        stopPoint = getTilePos().add(schematicMap.getTileOffset());
    }

    public void fixateScheme(boolean keep)
    {
        if(rectangle)
            finishRectangle(keep);
        else
        {
            //System.out.println(keep);
            if(!keep)
            {
                while(!temporaryBuildingTasks.isEmpty())
                {
                    int[] task = temporaryBuildingTasks.pollFirst();
                    //map.setTile(task[0]-(int)map.chunkOffset.x*Chunk.size, task[1]-(int)map.chunkOffset.y*Chunk.size, task[2]);
                    schematicMap.setTile(task[0]-(int)map.chunkOffset.x*Chunk.size, task[1]-(int)map.chunkOffset.y*Chunk.size, 0);
                }
            }
            else
            {
                //System.out.println(temporaryBuildingTasks.size());
                while(!temporaryBuildingTasks.isEmpty())
                    buildingTasks.addLast(temporaryBuildingTasks.pollFirst());
                //System.out.println(temporaryBuildingTasks.size());
            }
        }
    }

    public void finishRectangle(boolean keep)
    {
        //System.out.println(keep);
        //System.out.println(startPoint);
        //System.out.println(stopPoint);
        startPoint.add(schematicMap.getTileOffset().scl(-1));
        stopPoint.add(schematicMap.getTileOffset().scl(-1));
        if(keep)
            for (int i=(int)Math.min(startPoint.x, stopPoint.x); i<=Math.max(stopPoint.x, startPoint.x); i++)
                for (int j=(int)Math.min(startPoint.y, stopPoint.y); j<=Math.max(stopPoint.y, startPoint.y); j++)
                {
                    schematicMap.setTile(i, j, buildingTile);
                    buildingTasks.addLast(new int[]{i+(int)map.chunkOffset.x*Chunk.size, j+(int)map.chunkOffset.y*Chunk.size, buildingTile});
                }
        startPoint = new Vector2(-1,-1);
        stopPoint = new Vector2(-1,-1);
        rectangle = false;
        //clearSchematic();
    }

    public Vector2 getTilePos()
    {
        float pixelsPerTile = (float)(float)viewport.getScreenHeight()/scale;
        Vector2 cursorTilePos = new Vector2(cursorPosition).scl(1.f/pixelsPerTile);
        return new Vector2(cam.getPosition().x+cursorTilePos.x, cam.getPosition().y+cursorTilePos.y);
    }

    public void clearSchematic()
    {
        schematicMap.generate();
    }

    public void buildSchematic()
    {

    }


    void drawMap(int low, int high, int wall)
    {
        worldBatch.setShader(shader);
        worldBatch.begin();
        //map.getTexture().bind(2);
        map.bindColorTexture();
        shader.setUniformi("colorTexture", 3);
        map.bindTexture();
        shader.setUniformi("u_texture", 2);
        ((Texture)(Resourses.getResourse("textures/tiles/tileset.png", Texture.class))).bind(0);
        shader.setUniformf("aspectRatio", aspectRatio);
        shader.setUniformf("mapSize", map.width*Chunk.size);
        shader.setUniform2fv("camPos", new float[]{cam.getPosition().x, cam.getPosition().y},0,2);
        shader.setUniformi("low", low);
        shader.setUniformi("high", high);
        shader.setUniformi("wall", wall);
        shader.setUniformf("time", time);
        Vector2 relativeCursorPosition = new Vector2(cursorPosition);
        relativeCursorPosition.scl(new Vector2(1.f/(float)viewport.getScreenWidth(), 1.f/(float)viewport.getScreenHeight()));
        relativeCursorPosition.add(new Vector2(0.5f, 0.5f));
        shader.setUniform2fv("cursorPos", new float[]{relativeCursorPosition.x, relativeCursorPosition.y},0,2);
        shader.setUniformf("scale", scale);
        worldBatch.draw(img, 0, 0, 800, 480);
        worldBatch.end();
    }

    void drawSchematicMap()
    {
        //System.out.println(startPoint);
        //System.out.println(stopPoint);
        worldBatch.setShader(schematicShader);
        worldBatch.begin();
        //schematicMap.getTexture().bind(2);
        schematicMap.bindTexture();
        schematicShader.setUniformi("u_texture", 2);
        schematicShader.setUniformi("low", 1);
        schematicShader.setUniformi("high", 1);
        schematicShader.setUniformi("wall", 1);
        ((Texture)(Resourses.getResourse("textures/tiles/tileset.png", Texture.class))).bind(0);
        schematicShader.setUniformi("v_texture", 0);
        schematicShader.setUniformf("aspectRatio", aspectRatio);
        schematicShader.setUniformf("mapSize", map.width*Chunk.size);
        schematicShader.setUniform2fv("camPos", new float[]{cam.getPosition().x, cam.getPosition().y},0,2);

        Vector2 relativeCursorPosition = new Vector2(cursorPosition);
        relativeCursorPosition.scl(new Vector2(1.f/(float)viewport.getScreenWidth(), 1.f/(float)viewport.getScreenHeight()));
        relativeCursorPosition.add(new Vector2(0.5f, 0.5f));
        schematicShader.setUniform2fv("cursorPos", new float[]{relativeCursorPosition.x, relativeCursorPosition.y},0,2);
        schematicShader.setUniformf("scale", scale);
        startPoint.add(schematicMap.getTileOffset().scl(-1));
        stopPoint.add(schematicMap.getTileOffset().scl(-1));
        schematicShader.setUniform2fv("startPoint", new float[]{Math.min(startPoint.x, stopPoint.x), Math.min(startPoint.y, stopPoint.y)}, 0, 2);
        schematicShader.setUniform2fv("stopPoint", new float[]{Math.max(startPoint.x, stopPoint.x), Math.max(startPoint.y, stopPoint.y)}, 0, 2);
        startPoint.add(schematicMap.getTileOffset().scl(1));
        stopPoint.add(schematicMap.getTileOffset().scl(1));
        if(player.inventory.selectedItem>=0)
            if(player.inventory.getItem(player.inventory.selectedItem) instanceof TileItem)
                schematicShader.setUniformi("buildingTile", Tiles.getTile(player.inventory.getItem(player.inventory.selectedItem).id).pX);

        worldBatch.draw(img, 0, 0, 800, 480);
        worldBatch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        //fbo = new FrameBuffer(Pixmap.Format.RGB565, (int)(Gdx.graphics.getWidth()), (int)(Gdx.graphics.getHeight()), false);
        //fboRegion = new TextureRegion(fbo.getColorBufferTexture());
        //fboRegion.flip(false, true);
        //System.out.println(Gdx.graphics.getWidth());
        //camera.res
    }

    @Override
    public void pause()
    {
        pause = true;

    }

    @Override
    public void resume()
    {
        pause = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
    }

    public void move(Vector2 delta)
    {
    }



    public void scale(float delta)
    {
        float ds = delta*scale/15.f;
        scale+=ds;
        //System.out.println(scale);
        cam.setInterpolation(0.03f+Math.min(1.f, 6.f/(scale*scale)));
    }

    public void setCursorPosition(Vector2 cp)
    {
        Vector3 ucp = camera.unproject(new Vector3(cp.x, cp.y, 0));
        cursorPosition = new Vector2(ucp.x-camera.position.x, ucp.y-camera.position.y);
    }

    public void addVelocity(Vector2 v)
    {
        player.addVelocity(v);
        velocity.add(v);
    }
}
