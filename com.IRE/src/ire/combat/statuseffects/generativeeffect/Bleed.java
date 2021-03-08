package ire.combat.statuseffects.generativeeffect;

import ire.entities.Entity;
import ire.tools.Tools;

public class Bleed extends GenerativeEffect {

    public Bleed(int effectLevel) {
        super("Bleed", "BLD", "Target loses health at end of each turn.",
                true, false, 1, 5, effectLevel, 0.2f, 0.1f);
    }

    @Override
    public void apply(Entity attacker, Entity defender) {

        statProbability = (strengthCoefficient * ((float) strength / (float) defender.getCurHlh()));
        if (statProbability < 0.05f) {
            statProbability = 0;
        }
        super.apply(attacker, defender);
    }

    @Override
    protected void displayResult(String defender, boolean success) {
        if (success) {
            System.out.println(defender + " started bleeding!");
        } else {
            System.out.println(defender + " didn't start bleeding.");
        }
        Tools.sleep(1000);
    }

    @Override
    public boolean incrementEffect(Entity target) {

        if (target.isAlive()) {
            System.out.println(target.getName() + " bled for " + strength + " damage.");
            target.bEffects.takeDamage(strength, false);
            Tools.sleep(1000);
        } else {
            target.bEffects.takeDamage(strength, false);
        }

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
        System.out.println(target.getName() + " stopped bleeding.");
        Tools.sleep(1250);
    }

    public void setStrength(int strength) {

        if (strength > 0) {
            this.strength = strength;
        } else {
            System.err.println("Bleed's strength must be greater than 0");
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
