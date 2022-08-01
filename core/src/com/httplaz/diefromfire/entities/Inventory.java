package com.httplaz.diefromfire.entities;

import com.httplaz.diefromfire.entities.mobs.Entity;
import com.httplaz.diefromfire.items.Item;
import com.httplaz.diefromfire.items.NullItem;

public class Inventory
{
    public Item items[];

    public int selectedItem = 0;
    public Entity entity;

    public Inventory(Entity e)
    {
        entity = e;
        items = new Item[31];
        for (int i=0; i<31; i++)
            items[i] = new NullItem();
    }


    public void addItem(Item i)
    {
        for (int j=0; j<30; j++)
            if(items[j] instanceof NullItem)
            {
                items[j] = i;
                break;
            }
    }

    public void ThrowItem()
    {

    }

    public void useItem()
    {
        items[selectedItem].use(entity, entity.world);
    }

    public void noUseItem()
    {
        items[selectedItem].noUse(entity, entity.world);
    }

    public Item getItem(int i)
    {
        return items[i];
    }
}
