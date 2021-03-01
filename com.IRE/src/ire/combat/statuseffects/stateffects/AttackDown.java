package ire.combat.statuseffects.stateffects;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;
import ire.tools.Tools;

public class AttackDown extends StatEffect {

    public AttackDown(int strength) {
        super("Attack Down", "ATK", "Lowers the attack stat of the afflicted target.",
                true, true, 1, 5, strength);
    }

    //  Not sure if stacks and duration should be in constructor in the first place.
    //  Consider moving class to "typicalStatEffect" package?

    @Override
    public void apply(Entity attacker, Entity defender) {

        double rand = Math.random();
        float baseChance = (0.60f + ((strength - 1) * 0.075f));
        float baseResistance = (0.003333f * (attacker.getCurMag() - defender.getCurAtk()));  // Potentially outsource.
        boolean original = true;

        System.out.println("rand: " + rand);
        System.out.println("baseChance: " + baseChance);
        System.out.println("baseResistance: " + baseResistance);
        System.out.println("combined chance: " + (baseChance + baseResistance));
        if (rand < (baseChance + baseResistance)) {

            this.statMultiplier = (1 - (0.70f + ((strength - 1) * 0.075f)));

            for (StatusEffect se: defender.getStatusEffects()) {
                if (se.getName().equals(this.getName())) {

                    se.incrementStacks(1);
                    se.incrementDuration(this.duration);
                    ((StatEffect) se).incrementStatMultiplier(this.statMultiplier / se.getStacks());
                    original = false;
                    break;
                }
            }

            if (original) {

                System.out.println(defender.getName() + " had their <stat> lowered!");
                defender.addStatusEffect(this);
                System.out.println("Multiplier: " + statMultiplier);
            }

        } else {
            System.out.println(defender.getName() + " didn't have their <stat> lowered!");
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
