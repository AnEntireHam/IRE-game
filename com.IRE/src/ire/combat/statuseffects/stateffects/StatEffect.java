package ire.combat.statuseffects.stateffects;

import ire.combat.statuseffects.StatusEffect;

public class StatEffect extends StatusEffect {

    protected int strength;

    public StatEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                            int stacks, int duration, int strength) {
        super(name, abbreviation, description, display, percentage, stacks, duration);

        this.strength = strength;
    }

    //  Calculates to see if a status effect or stack should be added.
    @Override
    protected boolean apply() {
        return false;
    }

    //  Reduces duration by 1 and executes effects if either are applicable.
    @Override
    protected void incrementEffect() {
        System.out.println("Method under construction");
    }

    //  Removes the status effect from its host. Probably also does math to figure out if it should.
    @Override
    protected void remove() {
        System.out.println("Method under construction");
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        if (strength < 1) {
            System.err.println("Strength must be greater than 0");
        }  //  Handle max strength values in each spell?
    }

    //  For status effects with specific or unique conditions, add separate methods.
}
