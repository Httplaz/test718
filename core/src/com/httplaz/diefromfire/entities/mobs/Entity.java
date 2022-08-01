package com.httplaz.diefromfire.entities.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.httplaz.diefromfire.Interactions;
import com.httplaz.diefromfire.Resourses;
import com.httplaz.diefromfire.World;
import com.httplaz.diefromfire.WorldGenerator;
import com.httplaz.diefromfire.entities.AI;
import com.httplaz.diefromfire.entities.Inventory;
import com.httplaz.diefromfire.entities.bullets.Bullet;
import com.httplaz.diefromfire.entities.decoratives.Decorative;
import com.httplaz.diefromfire.entities.interactives.Interactive;
import com.httplaz.diefromfire.items.Item;
import com.httplaz.diefromfire.items.NullItem;
import com.httplaz.diefromfire.items.abilities.ForceBlow;

public class Entity
{
    public TextureRegion sprite;
    public TextureRegion tool;
    public Vector2 position;
    public Vector2 velocity;
    public float collisionRadius;
    public Rect collider;
    public boolean circle = true;


    public float speed = 0.1f;
    public float rotation = 0.0f;
    public float addRot = 0.0f;
    public float updateTime = 0.0f;
    public int updatesAlive = 0;

    public int framesAlive;

    public World world;

    public Inventory inventory = null;

    public Vector2 target;
    public Vector2 addVelocity;

    public boolean hideTool = true;

    public int health = 100;
    public int maxHealth = 100;
    public int maxStamina = 100;

    public AI ai;

    public boolean reflectsBullets = false;
    public int faction = 0;

    public float size = 1.f;

    public boolean transcendent = false;

    public int inFrames = 0;

    public Sound deathSound;

    public Sprite s;

    public Item ability;
    public int stamina = 100;
    public int staminaRecovery = 1;
    public int healthRecovery = 0;

    public boolean usingAbility = false;

    public float power = 1.0f;
    public float resurrectionChance = 0;

    public int cost = 1;

    public Entity(Vector2 p, TextureRegion t, float cR, World w)
    {
        ability = new NullItem();
        addVelocity = Vector2.Zero.cpy();
        target = new Vector2();
        sprite = t;
        s = new Sprite(t);
        position = p;
        collisionRadius = cR;
        velocity = Vector2.Zero.cpy();
        setTarget(p);
        tool = (TextureRegion) Resourses.getResourse("textures/blank.png:0 0 16 16", TextureRegion.class);
        world = w;
        int r = WorldGenerator.random.nextInt(3);
        deathSound =  (Sound)Resourses.getResourse("sounds/mobs/death" + (r==0? "":r) + ".mp3", Sound.class);
    }

    public void giveInventory()
    {
        inventory = new Inventory(this);
    }

    public void giveAi(AI a)
    {
        ai = a;
    }

    public void setTarget(Vector2 v)
    {
        target = v.cpy();
    }

    public void attack()
    {

    }

    public void useAbility()
    {
        if(stamina>0)
        {
            if(ability.use(this, world))
            {
                stamina-=ability.staminaCost;
                //Resourses.playSound(ability.useSound);
            }
        }
    }

    public void setFaction(int f)
    {
        faction = f;
    }

    public float getRotationFloat()
    {
        return getRotationVec2().angleDeg();
    }

    public Vector2 getRotationVec2()
    {
        return (new Vector2(target).add(getPosition().scl(-1))).nor();
    }

    public void draw(SpriteBatch spriteBatch, Vector2 dPos, Vector2 scale)
    {
        rotation = getRotationFloat();
        hideTool = true;
        if(inventory!=null)
            if(inventory.selectedItem>=0)
            {
                tool.setRegion(inventory.getItem(inventory.selectedItem).texture);
                hideTool = false;
            }
        float dscale = scale.x-scale.y;
        Vector2 pos = getWorldPosition();
        //System.out.println(pos);
        float vev = 0.5f;
        if(sprite.getRegionHeight()/sprite.getRegionWidth()==2.f)
            vev = 1;
        float sizeSq = Math.min(sprite.getRegionWidth()/16.f, sprite.getRegionHeight()/16.f);
        //spriteBatch.draw(sprite, dPos.x+(pos.x-0.5f)*scale.x, dPos.y+(pos.y-0.5f)*scale.y, scale.x, scale.y );
        s.setPosition(dPos.x+(pos.x-0.5f*sizeSq)*scale.x, dPos.y+(pos.y-vev*sizeSq)*scale.y);
        s.setOrigin(scale.x*0.5f*sizeSq, scale.y*vev*sizeSq);
        s.setSize((scale.y)/16.f*sprite.getRegionWidth(), (scale.y)/16.f*sprite.getRegionHeight());
        s.setScale(size);
        s.setRotation(rotation);
        if(this instanceof Decorative)
            s.setAlpha(((Decorative) this).lifetime/90.f);
        //s.set
        //spriteBatch.draw(sprite.getTexture(), dPos.x+(pos.x-0.5f*sizeSq)*scale.x, dPos.y+(pos.y-vev*sizeSq)*scale.y, scale.x*0.5f*sizeSq, scale.y*vev*sizeSq, (scale.y)/16.f*sprite.getRegionWidth(), (scale.y)/16.f*sprite.getRegionHeight(), size,size, rotation, sprite.getRegionX(), sprite.getRegionY(), sprite.getRegionWidth(), sprite.getRegionHeight(), false, false);
        s.draw(spriteBatch);
        //spriteBatch.draw(tool, dPos.x+(pos.x-0.5f+0.5f)*scale.x, dPos.y+(pos.y-0.0f)*scale.y, scale.x/8.f, scale.y/8.f, (scale.y+dscale*(float)Math.abs(Math.cos(Math.toRadians(rotation+addRot))))/1.f, (scale.y+dscale*(float)Math.abs(Math.sin(Math.toRadians(rotation+addRot))))/1.f, 1.f,1.f, rotation+addRot);
        if(!hideTool)
            spriteBatch.draw(tool.getTexture(), dPos.x+(pos.x)*scale.x, dPos.y+(pos.y)*scale.y, scale.x/8.f, scale.y/8.f, (scale.y+dscale*(float)Math.abs(Math.cos(Math.toRadians(rotation+addRot))))/1.f, (scale.y+dscale*(float)Math.abs(Math.sin(Math.toRadians(rotation+addRot))))/1.f, 1.f,1.f, rotation+addRot, tool.getRegionX(), tool.getRegionY(), tool.getRegionWidth(), tool.getRegionHeight(), false, false);

        framesAlive++;
    }

    public void addVelocity(Vector2 dt)
    {
        velocity.add(dt);
    }

    public Vector2 getPosition()
    {
        return new Vector2(position);
    }


    public Vector2 getWorldPosition()
    {
        return getPosition().add(world.getTileOffset().scl(-1));
    }

    public Vector2 getVelocity()
    {
        return new Vector2(velocity);
    }

    public void update(World w)
    {
        stamina = Math.min(stamina+staminaRecovery, maxStamina);
        health = Math.min(health+healthRecovery, maxHealth);
        if(updatesAlive<=0)
        {
            updatesAlive++;
            return;
        }
        if(health<=0)
            Die();

        if(ai!=null)
            ai.update();

        ability.cooldown--;
        ability.soundDelta++;
        if(inventory!=null)
            if(inventory.selectedItem>=0)
            {
                inventory.getItem(inventory.selectedItem).cooldown--;
                inventory.getItem(inventory.selectedItem).soundDelta++;
            }

        velocity.add(addVelocity.cpy().scl(-0.1f));
        addVelocity.scl(0.9f);
        if(addVelocity.len()<0.1f)
        {
            velocity.add(addVelocity.scl(-1));
            addVelocity = Vector2.Zero.cpy();
        }
        //System.out.println(velocity);

        if(velocity.len()>0)
        {
            if(transcendent)
                position.add(velocity.cpy().scl(speed));
            else
                position.set(Interactions.move(this, w));
        }
        updatesAlive++;
        inFrames--;
        if(usingAbility)
            useAbility();
    }

    public void Damage(int dmg)
    {
        //System.out.println(inFrames);
        health-=dmg;
        inFrames=10;
    }

    public void setRotation(float r)
    {
        rotation = r;
        //sprite.setRotation(r);
    }

    public void kick(Vector2 d)
    {
        addVelocity.add(d);
        velocity.add(d);
    }


    public void Die()
    {
        if(WorldGenerator.random.nextFloat()<resurrectionChance)
        //if(true && !(this instanceof Bullet) )
        {
            Entity e = null;
            for (int i=world.entities.size()-1; i>=0; i--)
                if(world.entities.get(i)!=this && !(world.entities.get(i) instanceof Bullet))
                {
                    resurrectionChance*=0.2;
                    e = world.entities.get(i);
                    sprite = e.sprite;
                    s = new Sprite(sprite);
                    position = e.position;
                    //velocity = e.velocity.cpy();
                    inventory = new Inventory(this);
                    if(e.inventory!=null)
                    for (int j=0; j<e.inventory.items.length; j++)
                        inventory.items[j] = e.inventory.getItem(j);
                    health = e.health;
                    ability = new NullItem();
                    e.Die();
                    new ForceBlow().use(this, world);
                    break;
                }
            return;
        }
        velocity.set(Vector2.Zero);
        world.removeEntity(this);
        if(!(this instanceof Bullet) && !(this instanceof Interactive))
        {
            Entity e = new Decorative(getPosition(), (TextureRegion)(Resourses.getResourse("textures/paints/death.png:0 0 32 32", TextureRegion.class)), world);
            e.setTarget(new Vector2(1,0).rotateDeg(WorldGenerator.random.nextFloat()*360.f).add(e.getPosition()));
            world.addDecorative(e);
            //world.addColor((int)(getWorldPosition().x), (int)(getWorldPosition().y), new int[]{255, 0, 0, 1} );
            Resourses.playSound(deathSound);
            world.coins+=cost;
        }
    }

    public class Rect
    {
        Vector2 lower;
        Vector2 higher;
        public Rect(Vector2 l, Vector2 h)
        {
            lower = l;
            higher = h;
        }

        public Rect(float lx, float ly, float hx, float hy)
        {
            lower = new Vector2(lx, ly);
            higher = new Vector2(hx, hy);
        }

        public Boolean overlaps(Rect r, Vector2 p1, Vector2 p2)
        {
            return !( lower.y+p1.y > r.higher.y+p2.y || higher.y+p1.y < r.lower.y+p2.y || higher.x+p1.x <r.lower.x+p2.x || lower.x+p1.x > r.higher.x+p2.x );
        }

        public boolean overlaps(float radius, Vector2 p1, Vector2 p2)
        {
            float circleDistanceX = Math.abs(p2.x - p1.x);
            float circleDistanceY = Math.abs(p2.y - p1.y);
            float rectWidth = higher.x-lower.x;
            float rectHeight = higher.y-lower.y;
            if (circleDistanceX > (rectWidth/2 + radius)) { return false; }
            if (circleDistanceY > (rectHeight/2 + radius)) { return false; }

            if (circleDistanceX <= (rectWidth/2)) { return true; }
            if (circleDistanceY <= (rectHeight/2)) { return true; }

            float cornerDistance_sq = (circleDistanceX - rectWidth/2)*(circleDistanceX - rectWidth/2) + (circleDistanceY - rectHeight/2)*(circleDistanceY - rectHeight/2);
            //System.out.println("We came here");
            return (cornerDistance_sq <= (radius*radius));
        }
    }
}
