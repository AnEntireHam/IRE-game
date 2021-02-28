package ire.combat.statuseffects;

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

    protected abstract boolean apply();
    protected abstract void incrementEffect();
    protected abstract void remove();

}
