package ire.combat.statuseffects.generativeeffect;

import ire.combat.statuseffects.StatusEffect;

public abstract class GenerativeEffect extends StatusEffect {

    public GenerativeEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                            int stacks, int duration) {
        super(name, abbreviation, description, display, percentage, stacks, duration);
    }

    @Override
    protected boolean apply() {
        return false;
    }

    @Override
    protected void incrementEffect() {
        System.out.println("Method under construction");
    }

    @Override
    protected void remove() {
        System.out.println("Method under construction");
    }
}
