package com.ire.combat.statuseffects.generativeeffect;

import com.ire.combat.statuseffects.StatusEffect;
import com.ire.entities.Entity;
import com.ire.tools.Tools;

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
        boolean original = true;

        float totalProbability = effectProbability + statProbability;

        if (rand <= (totalProbability)) {

            success = true;

            for (StatusEffect se: defender.getStatusEffects()) {
                if (se.getName().equals(this.name)) {

                    original = false;

                    se.incrementStacks(1);
                    se.incrementDuration(this.duration);
                    ((GenerativeEffect) se).incrementStrength(strength);

                    break;
                }
            }

            if (original) {
                defender.addStatusEffect(this);
            }
        }

        displayResult(defender.getName(), success, original);
        if (attacker.isDebug()) {
            System.out.println("Strength " + strength);
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

    @Override
    public String generateDisplay() {

        // TODO: temporary format. probably include a shorter/longer version, settable by global options.
        return (name + ": " + Math.abs(strength) + ", " + duration + " t, " + stacks + " s.  ");
    }

    public abstract void combineEffects(Entity target, int total);

    protected abstract void displayResult(String defender, boolean success, boolean original);

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void incrementStrength(int strength) {

        this.strength += strength;
        /*if (this.strength < 1) {
            remove(target);
            //  Possibly include text indicated that GenerativeEffect has stopped due to < 1 strength.
        }*/
    }
}
