package ire.combat.statuseffects.generativeeffect;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;
import ire.tools.Tools;

public abstract class GenerativeEffect extends StatusEffect {

    protected int strength;
    protected int effectLevel;
    protected float baseProbability;
    protected float levelProbability;

    protected float strengthCoefficient = 3;
    protected float statProbability = 0;
    protected String expirationMessage;

    //  It might make more sense to change the strengthCoefficient instead of the levelProbability.
    public GenerativeEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                            int stacks, int duration, int effectLevel, float baseProbability, float levelProbability,
                            String expirationMessage) {
        super(name, abbreviation, description, display, percentage, stacks, duration);

        this.effectLevel = effectLevel;
        this.baseProbability = baseProbability;
        this.levelProbability = levelProbability;
        this.expirationMessage = expirationMessage;
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

    @Override
    public boolean incrementEffect(Entity target) {

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
        System.out.println(target.getName() + expirationMessage);
        Tools.sleep(1250);
    }

    public abstract void combineEffects(Entity target, int total);
    protected abstract void displayResult(String defender, boolean success);

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {

        if (strength > 0) {
            this.strength = strength;
        } else {
            System.err.println("A GenerativeEffect's strength must be greater than 0");
            this.strength = 1;
        }
    }

    public void incrementStrength(Entity target, int strength) {

        this.strength += strength;
        if (this.strength < 1) {
            remove(target);
            //  Possibly include text indicated that GenerativeEffect has stopped due to < 1 strength.
        }
    }
}
