package ire.world;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private Map<String, Item> inventory = new HashMap<String, Item>();

    public Collection<Item> getInventory() {
        return inventory.values();
    }

    public void addItem(Item item) {
        inventory.put(item.getName(), item);
    }

    public Item getItem(String itemName) {
        return inventory.get(itemName);
    }

    public Item removeItem(String itemName) {
        return inventory.remove(itemName);
    }

}

