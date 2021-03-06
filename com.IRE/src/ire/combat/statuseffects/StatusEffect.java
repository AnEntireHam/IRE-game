package ire.combat.statuseffects;

import ire.entities.Entity;

public abstract class StatusEffect {

    protected String name;
    protected String abbreviation;
    protected String description;
    protected boolean display;
    protected boolean percentage;
    protected int stacks;
    protected int duration;

    public StatusEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                        int stacks, int duration) {

        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.display = display;
        this.percentage = percentage;
        this.stacks = stacks;
        this.duration = duration;
    }

    protected abstract void apply(Entity attacker, Entity defender);
    protected abstract void incrementEffect(Entity target, boolean tick);
    protected abstract void remove(Entity target);

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

    @Override
    public String toString() {
        return ("Name: " + name + "  Stacks: " + stacks + "  Duration: " + duration);
    }
}
