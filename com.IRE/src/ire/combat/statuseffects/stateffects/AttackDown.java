package ire.combat.statuseffects.stateffects;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;
import ire.tools.Tools;

public class AttackDown extends StatEffect {

    public AttackDown(int strength) {
        super("Attack Down", "ATK", "Lowers the attack stat of the afflicted target.",
                true, true, 0, 5, strength);
    }

    //  Not sure if stacks and duration should be in constructor in the first place.
    //  Consider moving class to "typicalStatEffect" package?

    //  Calculates to see if a status effect or stack should be added.
    //  if application is successful, and there's a pre-existing copy, make pre-existing one stronger.
    @Override
    protected void apply(Entity attacker, Entity defender, int damage) {

        double rand = Math.random();
        float baseChance = (0.60f + ((strength - 1) * 0.075f));
        float baseResistance = (0.3333f * (attacker.getCurAtk() - defender.getCurAtk()));  // Potentially outsource.
        boolean original = true;

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
            }

        } else {
            System.out.println(defender.getName() + " didn't have their <stat> lowered!");
        }
        Tools.sleep(1000);
    }

    //  Reduces duration by 1 and executes effects if either are applicable.
    //  When applying a status effect, first loop through the target's status effects. If there's a SE with a matching
    //  abbreviation and display/percentage, consider that in the final display.
    //  If duration == 0, call remove.
    @Override
    protected void incrementEffect(Entity target, boolean tick) {
        System.out.println("Method under construction");
    }

    @Override
    protected void remove(Entity target) {
        target.removeStatusEffect(this);
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
