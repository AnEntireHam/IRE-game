package com.ire.combat.statuseffects;

import com.ire.entities.Entity;

import java.util.Arrays;

public abstract class StatusEffect {


    // Fields

    protected String name;
    protected String abbreviation;
    protected String description;
    protected boolean display;
    protected boolean percentage;
    protected int stacks;
    protected int duration;
    protected RemoveCondition[] removeConditions;

    private float takeDamageCoefficient = 4;
    private float takeDamageBase = -0.6f;


    // Constructor

    public StatusEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                        int stacks, int duration, RemoveCondition[] removeConditions) {

        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.display = display;
        this.percentage = percentage;
        this.stacks = stacks;
        this.duration = duration;
        this.removeConditions = removeConditions;
    }


    // Methods

    public abstract void apply(Entity attacker, Entity defender);
    public abstract boolean incrementEffect(Entity target);
    public abstract void remove(Entity target);
    public abstract String generateDisplay();

    public boolean checkRemove(RemoveCondition condition) {

        for (RemoveCondition r : removeConditions) {
            if (r.equals(condition)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTakeDamage(int damage, int maxHlh) {
        return Math.random() <= (((float) damage / maxHlh) * takeDamageCoefficient) + takeDamageBase;
    }


    // Accessors and Mutators

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public boolean isDisplay() {
        return display;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public int getStacks() {
        return stacks;
    }

    public int getDuration() {
        return duration;
    }

    public float getTakeDamageCoefficient() {
        return takeDamageCoefficient;
    }

    public float getTakeDamageBase() {
        return takeDamageBase;
    }

    public void setStacks(int stacks) {
        this.stacks = stacks;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void incrementStacks(int increment) {
        this.stacks += increment;
    }

    public void incrementDuration(int increment) {
        this.duration += increment;
    }

    public void setTakeDamageCoefficient(float takeDamageCoefficient) {
        this.takeDamageCoefficient = takeDamageCoefficient;
    }

    public void setTakeDamageBase(float takeDamageBase) {
        this.takeDamageBase = takeDamageBase;
    }

    @Override
    public String toString() {
        return "StatusEffect{" +
                "name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", description='" + description + '\'' +
                ", stacks=" + stacks +
                ", duration=" + duration +
                ", removeConditions=" + Arrays.toString(removeConditions) +
                '}';
    }
}
