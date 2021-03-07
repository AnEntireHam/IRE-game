package ire.combat.statuseffects.generativeeffect;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;
import ire.tools.Tools;

public abstract class GenerativeEffect extends StatusEffect {

    protected float strengthCoefficient = 3;

    protected int strength;
    protected int effectLevel;
    protected float baseProbability;
    protected float levelProbability;

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
        float damageProbability = 0.0f;
        boolean success = false;

        //  It's probably silly to base this off of TOTAL health for big chunky boss enemies.
        if (strength < 0) {

            damageProbability = (strengthCoefficient * ((float) strength / (float) defender.getCurHlh()));
            if (damageProbability < 0.05f) {
                damageProbability = 0;
            }
        }
        float totalProbability = effectProbability + damageProbability;

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

    @Override
    public boolean incrementEffect(Entity target) {

        target.bEffects.takeDamage(strength, true);
        System.out.println(target.getName() + " bled for " + strength + " damage.");  // Very un-general
        this.incrementDuration(-1);
        if (this.duration <= 0) {
            remove(target);
            return true;
        }
        return false;
    }

    @Override
    public void remove(Entity target) {

        target.removeStatusEffect(this);
        System.out.println(target.getName() + " stopped bleeding.");  //  Also un-general
        Tools.sleep(1250);
    }

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
