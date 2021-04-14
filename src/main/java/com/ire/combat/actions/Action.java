package com.ire.combat.actions;

import com.ire.entities.Entity;

import java.io.Serializable;

public abstract class Action implements Serializable {

    protected String name;
    protected String description;

    public Action(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void execute(Entity attacker, Entity defender);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Action{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
