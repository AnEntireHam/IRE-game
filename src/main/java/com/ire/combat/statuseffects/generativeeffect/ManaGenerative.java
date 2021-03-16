package com.ire.combat.statuseffects.generativeeffect;

import com.ire.entities.Entity;
import com.ire.tools.Tools;

public abstract class ManaGenerative extends GenerativeEffect {

    float statCoefficient = 0.003333f;

    public ManaGenerative(String name, String abbreviation, String description, boolean display, boolean percentage,
                          int stacks, int duration, int effectLevel, float baseProbability, float levelProbability,
                          String expirationMessage) {
        super(name, abbreviation, description, display, percentage,
                stacks, duration, effectLevel, baseProbability, levelProbability, expirationMessage);
    }

    @Override
    public void apply(Entity attacker, Entity defender) {

        statProbability = (statCoefficient * (attacker.getBaseMag() - defender.getBaseMag()));
        super.apply(attacker, defender);
    }

    @Override
    public void combineEffects(Entity target, int total) {

        if (total < 0) {

            if (target.isAlive()) {
                System.out.println(target.getName() + " lost " + -total + " mana.");
                Tools.sleep(1000);
            }
            target.bleedMana(-total, false, false);

        } else if (total > 0) {

            target.regenerateMana(total, true, false);
            Tools.sleep(1000);

        } else {

            System.out.println(target.getName() + " regenerated a normal amount of mana.");
        }
    }

    @Override
    public String generateDisplay() {
        return null;
    }
}
