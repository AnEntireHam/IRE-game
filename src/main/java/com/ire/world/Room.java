package com.ire.world;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private String description = "";
    private Map<String, Room> exits = new HashMap<String, Room>();
    private Inventory itemsInRoom = new Inventory();

    public Room(String... description) {
        for (String d : description) {
            this.description += d;
        }
    }

    public String getLongDescription() {

        String returnValue = this.description;
        for (Item item: itemsInRoom.getInventory()) {
            returnValue += "\n" + item.getUnequippedDescription();
        }

        return returnValue;
    }

    public void addItem(Item item) {
        itemsInRoom.addItem(item);
    }

    public Item getItem(String name) {
        return itemsInRoom.getItem(name);
    }

    public Item removeItem(String name) {
        return itemsInRoom.removeItem(name);
    }

    public void addPath(String direction, Room roomTo) {
        exits.put(direction, roomTo);
    }

    public Room roomAtPath(String direction) {
        return exits.get(direction);
    }
}

