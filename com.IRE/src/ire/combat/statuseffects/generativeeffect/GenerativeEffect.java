package ire.combat.statuseffects.generativeeffect;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;

public abstract class GenerativeEffect extends StatusEffect {

    protected int strength;
    protected int effectLevel;
    protected float baseProbability;
    protected float levelProbability;

    protected float strengthCoefficient = 3;
    protected float statProbability = 0;

    //  It might make more sense to change the strengthCoefficient instead of the levelProbability.
    public GenerativeEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                            int stacks, int duration, int effectLevel, float baseProbability, float levelProbability) {
        super(name, abbreviation, description, display, percentage, stacks, duration);

        this.effectLevel = effectLevel;
        this.baseProbability = baseProbability;
        this.levelProbability = levelProbability;
    }

    @Override
    public void apply(Entity attacker, Entity defender) {

        double rand = Math.random();
        float effectProbability = (baseProbability + levelProbability * (effectLevel - 1));
        boolean success = false;

        float totalProbability = effectProbability + statProbability;
        //  Extracting damageProbability out as a variable might be jank, but maybe not.

        if (rand <= (totalProbability)) {

            success = true;

            for (StatusEffect se: defender.getStatusEffects()) {
                if (se.getName().equals(this.name)) {

                    se.incrementStacks(1);
                    se.incrementDuration(this.duration);

                    ((GenerativeEffect) se).incrementStrength(defender, strength);
                    break;
                }
            }

            if (stacks == 1) {
                defender.addStatusEffect(this);
            }
        }

        displayResult(defender.getName(), success);
        if (attacker.isDebug()) {
            System.out.println("Strength: " + strength);
        }
    }

    protected abstract void displayResult(String defender, boolean success);

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void incrementStrength(Entity target, int strength) {
        this.strength += strength;
    }
}
