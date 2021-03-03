package ire.combat.statuseffects.stateffects;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;
import ire.tools.Tools;

public class AttackDown extends StatEffect {

    public AttackDown(int strength) {
        super("Attack Down", "ATK", "Lowers the attack stat of the afflicted target.",
                true, true, 1, 5, 0.60f, strength, 0.075f);
    }

    //  Not sure if stacks and duration should be in constructor in the first place.
    //  Consider moving class to "typicalStatEffect" package?

    @Override
    public void apply(Entity attacker, Entity defender) {

        double rand = Math.random();
        float effectProbability = -(baseProbability + strengthCoefficient * (strength - 1));
        float statProbability = (statCoefficient * (attacker.getBaseMag() - defender.getBaseAtk()));
        float totalProbability = effectProbability + statProbability;

        if (attacker.isDebug()) {
            System.out.println("rand: " + rand);
            System.out.println("effectProbability: " + effectProbability);
            System.out.println("statProbability: " + statProbability);
            System.out.println("combined chance: " + (effectProbability + statProbability));
        }

        if (rand < (totalProbability)) {

            for (StatusEffect se: defender.getStatusEffects()) {
                if (se.getName().equals(this.getName())) {

                    se.incrementStacks(1);
                    se.incrementDuration(this.duration);
                    //  This needs to be edited so that "this.statMultiplier" is the severity formula.
                    ((StatEffect) se).incrementStatMultiplier(this.statMultiplier / se.getStacks());
                    break;
                }
            }

            if (stacks == 1) {

                this.statMultiplier = (-(0.70f + ((strength - 1) * 0.075f)));
                System.out.println(defender.getName() + " had their <stat> lowered!");
                defender.addStatusEffect(this);
                System.out.println("Multiplier: " + statMultiplier);
            }

        } else {
            System.out.println(defender.getName() + " didn't have their <stat> lowered!");
        }

        if (attacker.isDebug()) {
            System.out.println(this.statMultiplier);
        }
        Tools.sleep(1000);
    }

    @Override
    protected void incrementEffect(Entity target, boolean tick) {

        this.incrementDuration(-1);
        if (this.duration < 0) {
            remove(target);
        }
    }

    @Override
    protected void remove(Entity target) {

        target.removeStatusEffect(this);
        System.out.println(target.getName() + "'s status effect " + this.getName() + " expired.");
        Tools.sleep(1000);
    }

    @Override
    public void setStrength(int strength) {

        if (strength < 1) {
            System.err.println("Strength must be greater than 0");
        }  else if (strength > 3) {
            System.err.print("Strength of " + this.name + " shouldn't exceed 3");
        } else {
            this.strength = strength;
        }
    }

}
