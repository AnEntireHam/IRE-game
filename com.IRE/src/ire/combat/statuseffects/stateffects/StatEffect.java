package ire.combat.statuseffects.stateffects;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;

public class StatEffect extends StatusEffect {

    protected final float statCoefficient = 0.003333f;
    protected float baseProbability;
    protected int strength;
    protected float strengthCoefficient;
    protected float statMultiplier;

    public StatEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                      int stacks, int duration, float baseProbability, int strength, float strengthCoefficient) {
        super(name, abbreviation, description, display, percentage, stacks, duration);

        this.baseProbability = baseProbability;
        this.strength = strength;
    }

    @Override
    public void apply(Entity attacker, Entity defender) {
    }

    //  Reduces duration by 1 and executes effects if either are applicable.
    @Override
    protected void incrementEffect(Entity target, boolean tick) {
        System.out.println("Method under construction");
    }

    //  For status effects with specific or unique conditions, add separate methods.
    @Override
    protected void remove(Entity target) {
        System.out.println("Method under construction");
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        if (strength < 1) {
            System.err.println("Strength must be greater than 0");
        }  //  Handle max strength values in each spell?
    }

    public float getStatMultiplier() {
        return statMultiplier;
    }

    public void setStatMultiplier(float statMultiplier) {
        this.statMultiplier = statMultiplier;
    }

    public void incrementStatMultiplier(float increment) {
        this.statMultiplier += increment;
    }
}
