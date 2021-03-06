package ire.combat.statuseffects.stateffects;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;
import ire.tools.Tools;

public abstract class StatEffect extends StatusEffect {

    protected static final float STAT_COEFFICIENT = 0.003333f;
    protected static final float STACK_COEFFICIENT = 0.50f;
    protected static final float DECAY_COEFFICIENT = 0.13f;

    protected float baseMultiplier;
    protected float levelMultiplier;
    protected float statMultiplier;

    protected float baseProbability;
    protected int effectLevel;
    protected float levelProbability;

    public StatEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                      int stacks, int duration, int effectLevel, float baseProbability, float levelProbability,
                      float baseMultiplier, float levelMultiplier) {
        super(name, abbreviation, description, display, percentage, stacks, duration);

        this.effectLevel = effectLevel;
        this.baseProbability = baseProbability;
        this.levelProbability = levelProbability;
        this.baseMultiplier = baseMultiplier;
        this.levelMultiplier = levelMultiplier;
    }

    @Override
    public void apply(Entity attacker, Entity defender) {

        double rand = Math.random();
        float effectProbability = (baseProbability + levelProbability * (effectLevel - 1));
        float statProbability = 0.0f;

        if (baseMultiplier < 0) {
            statProbability = (STAT_COEFFICIENT * (attacker.getBaseMag() - defender.getBaseStat(abbreviation)));
        }

        float totalProbability = effectProbability + statProbability;
        String[] parts = name.split(" ");
        String statName = parts[0].toLowerCase();

        if (rand <= (totalProbability)) {

            float statMultiplier = (baseMultiplier + ((effectLevel - 1) * levelMultiplier));

            for (StatusEffect se: defender.getStatusEffects()) {
                if (se.getName().equals(this.name)) {

                    se.incrementStacks(1);
                    se.incrementDuration(this.duration);

                    ((StatEffect) se).incrementStatMultiplier(
                            (statMultiplier * STACK_COEFFICIENT)
                                    / ((se.getStacks() - 1) + ((this.effectLevel - 1) * DECAY_COEFFICIENT)));
                    break;
                }
            }

            if (stacks == 1) {
                this.statMultiplier = statMultiplier;
                defender.addStatusEffect(this);
            }

            //  Improve sentence fluency later.
            if (baseMultiplier < 0.0) {
                System.out.println(defender.getName() + " had their " + statName + " lowered!");
            } else {
                System.out.println(defender.getName() + " had their " + statName + " increased!");
            }

        } else {
            if (baseMultiplier < 0.0) {
                System.out.println(defender.getName() + " didn't have their " + statName + " lowered!");
            } else {
                System.out.println(defender.getName() + " didn't have their " + statName + " increased!");
            }
        }

        if (attacker.isDebug()) {
            System.out.println("Stat Multiplier: " + this.statMultiplier);
        }

        Tools.sleep(1000);  //  Eventually factor in global text speed
    }

    //  Reduces duration by 1 and executes effects if either are applicable.
    @Override
    protected void incrementEffect(Entity target, boolean tick) {

        this.incrementDuration(-1);
        if (this.duration <= 0) {
            remove(target);
        }
    }

    @Override
    protected void remove(Entity target) {

        target.removeStatusEffect(this);
        System.out.println(target.getName() + "'s status effect " + name.toLowerCase() + " expired.");
        Tools.sleep(1000);
    }

    public int getEffectLevel() {
        return effectLevel;
    }


    public float getStatMultiplier() {
        return statMultiplier;
    }

    public void setEffectLevel(int effectLevel) {

        if (effectLevel < 1) {
            System.err.println("Level must be greater than 0");
        }  else if (effectLevel > 3) {
            System.err.print("Level of " + this.name + " shouldn't exceed 3");
        } else {
            this.effectLevel = effectLevel;
        }
    }

    public void setStatMultiplier(float statMultiplier) {
        this.statMultiplier = statMultiplier;
    }

    public void incrementStatMultiplier(float increment) {
        this.statMultiplier += increment;
    }

    @Override
    public String toString() {
        return ("Name: " + name + "  Multiplier: " + statMultiplier
                + "  Stacks: " + stacks + "  Duration: " + duration + "  Level: " + effectLevel);
    }
}
