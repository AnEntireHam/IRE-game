package ire.combat.statuseffects.generativeeffect;

import ire.entities.Entity;
import ire.tools.Tools;

public class ManaDrain extends GenerativeEffect {

    float statCoefficient = 0.003333f;

    public ManaDrain(int effectLevel) {
        super("Mana Drain", "M DRN", "Target loses mana at end of each turn.",
                true, false, 1, 5, effectLevel, 0.6f, 0.075f);
    }

    @Override
    public void apply(Entity attacker, Entity defender) {

        statProbability = (statCoefficient * (attacker.getBaseMag() - defender.getBaseMag()));
        super.apply(attacker, defender);
    }

    @Override
    protected void displayResult(String defender, boolean success) {
        if (success) {
            System.out.println(defender + " started losing mana!");
        } else {
            System.out.println(defender + " didn't start losing mana.");
        }
        Tools.sleep(1000);
    }

    @Override
    public boolean incrementEffect(Entity target) {

        if (target.getMan() - strength > 0) {
            if (target.isAlive()) {
                System.out.println(target.getName() + " lost " + strength + " mana.");
            }
            target.incrementMan(-strength);
        } else {
            if (target.isAlive()) {
                System.out.println(target.getName() + " lost " + target.getMan() + " mana.");
            }
            target.setMan(0);
        }
        Tools.sleep(1000);

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
        System.out.println(target.getName() + " stopped losing mana.");
        Tools.sleep(1250);
    }

    public void setStrength(int strength) {

        if (strength > 0) {
            this.strength = strength;
        } else {
            System.err.println("Mana Drain's strength must be greater than 0");
        }
    }

    public void incrementStrength(Entity target, int strength) {

        this.strength += strength;
        if (this.strength < 1) {
            remove(target);
            //  Possibly include text indicated that bleed has stopped due to < 1 strength.
        }
    }
}
