package com.ire.combat.statuseffects;

import com.ire.entities.Entity;

import java.io.Serializable;
import java.util.Arrays;

public abstract class StatusEffect implements RemoveMethod, Serializable {


    // Fields
    //
    protected String name;
    protected String abbreviation;
    protected String description;
    protected int stacks;
    protected int duration;
    protected RemoveCondition[] removeConditions;
    protected String[] removeText;

    protected final static RemoveCondition[] STANDARD_BUFF_CONDITIONS = new RemoveCondition[]{
            RemoveCondition.EXPIRATION, RemoveCondition.DEATH};
    protected final static RemoveCondition[] STANDARD_DEBUFF_CONDITIONS = new RemoveCondition[]{
            RemoveCondition.EXPIRATION, RemoveCondition.END_BATTLE, RemoveCondition.LEVEL_UP};

    private float takeDamageCoefficient = 4;
    private float takeDamageBase = -0.6f;


    // Constructor

    public StatusEffect(String name, String abbreviation, String description,
                        int stacks, int duration, RemoveCondition[] removeConditions) {

        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.stacks = stacks;
        this.duration = duration;
        this.removeConditions = removeConditions;
    }


    // Methods

    public abstract void apply(Entity attacker, Entity defender);
    protected abstract void printRemoveMessage(RemoveCondition condition, Entity target);
    public abstract String generateDisplay();

    public boolean incrementEffect(Entity target) {

        this.incrementDuration(-1);
        if (this.duration <= 0 && checkRemove(RemoveCondition.EXPIRATION, target)) {
            target.removeStatusEffect(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkRemove(RemoveCondition condition, Entity target) {

        for (RemoveCondition r : removeConditions) {
            if (r.equals(condition)) {
                printRemoveMessage(condition, target);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkTakeDamage(RemoveCondition condition, Entity target, int damage, int maxHlh) {

        if (checkRemove(condition, target)) {
            return Math.random() <= (((float) damage / maxHlh) * takeDamageCoefficient) + takeDamageBase;
        }
        return false;
    }


    // Accessors and Mutators

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
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
