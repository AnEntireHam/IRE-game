package ire.combat.statuseffects.generativeeffect;

import ire.entities.Entity;

public class Bleed extends GenerativeEffect {

    public Bleed(int effectLevel) {
        super("Bleed", "BLD", "Target loses health at end of each turn.",
                true, false, 1, 5, effectLevel, 0.2f, 0.1f);
    }

    @Override
    protected void displayResult(String defender, boolean success) {
        if (success) {
            System.out.println(defender + " started bleeding!");
        } else {
            System.out.println(defender + " didn't start bleeding.");
        }
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
