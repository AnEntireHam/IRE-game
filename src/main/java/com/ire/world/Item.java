package com.ire.world;

public class Item {

    private final String name;
    private final String equippedDescription;
    private final String unequippedDescription;

    public Item(String name, String equippedDescription, String unequippedDescription) {
        this.name = name;
        this.equippedDescription = equippedDescription;
        this.unequippedDescription = unequippedDescription;
    }

    public String getName() {
        return name;
    }

    public String getUnequippedDescription() {
        return unequippedDescription;
    }

}

