package ire.combat.statuseffects;

public abstract class StatusEffect {

    protected String name;
    protected String abbreviation;
    protected String description;
    protected boolean display;
    protected boolean percentage;
    protected int stacks;
    protected int duration;
    protected int cureResilience;

    public StatusEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                        int stacks, int duration) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.display = display;
        this.percentage = percentage;
    }

    protected abstract boolean inflict();
    protected abstract void incrementEffect();

}
