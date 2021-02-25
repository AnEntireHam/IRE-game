package ire.combat.statuseffects;

public abstract class StatusEffect {

    protected String name;
    protected String abbreviation;
    protected String description;
    protected boolean display;
    protected boolean percentage;
    protected int stacks;
    protected int duration;
    protected int removalType;
    protected int resilience;

    public StatusEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                        int stacks, int duration, int removalType, int resilience) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.display = display;
        this.percentage = percentage;
        this.stacks = stacks;
        this.duration = duration;
        this.removalType = removalType;
        this.resilience = resilience;
    }

    protected abstract boolean inflict();
    protected abstract void incrementEffect();

}
