package ire.combat.statuseffects.generativeeffect;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;

public abstract class GenerativeEffect extends StatusEffect {

    public GenerativeEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                            int stacks, int duration) {
        super(name, abbreviation, description, display, percentage, stacks, duration);
    }

    @Override
    public void apply(Entity attacker, Entity defender) {
    }

    @Override
    public boolean incrementEffect(Entity target, boolean tick) {
        System.out.println("Method under construction");
        return false;
    }

    @Override
    public void remove(Entity target) {
        System.out.println("Method under construction");
    }
}
