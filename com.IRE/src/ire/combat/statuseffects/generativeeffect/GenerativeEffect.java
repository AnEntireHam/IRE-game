package ire.combat.statuseffects.generativeeffect;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;

public abstract class GenerativeEffect extends StatusEffect {

    public GenerativeEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                            int stacks, int duration) {
        super(name, abbreviation, description, display, percentage, stacks, duration);
    }

    @Override
    protected void apply(Entity attacker, Entity defender, int damage) {
    }

    @Override
    protected void incrementEffect(Entity target, boolean tick) {
        System.out.println("Method under construction");
    }

    @Override
    protected void remove(Entity target) {
        System.out.println("Method under construction");
    }
}
