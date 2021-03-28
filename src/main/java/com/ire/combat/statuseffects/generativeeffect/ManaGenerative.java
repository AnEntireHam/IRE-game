package com.ire.combat.statuseffects.generativeeffect;

import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.entities.Entity;
import com.ire.tools.Tools;

public abstract class ManaGenerative extends GenerativeEffect {

    float statCoefficient = 0.003333f;

    public ManaGenerative(String name, String abbreviation, String description,
                          int stacks, int duration, RemoveCondition[] removeConditions, int effectLevel,
                          float baseProbability, float levelProbability, String expirationMessage) {

        super(name, abbreviation, description, stacks, duration, removeConditions,
                effectLevel, baseProbability, levelProbability, expirationMessage);
    }

    @Override
    public void apply(Entity attacker, Entity defender) {

        statProbability = (statCoefficient * (attacker.getBaseMag() - defender.getBaseMag()));
        super.apply(attacker, defender);
    }

    @Override
    public void executeGenerative(Entity target, int total) {

        if (total < 0) {

            if (target.isAlive()) {
                System.out.println(target.getName() + " lost " + -total + " mana.");
                Tools.sleep(1000);
            }
            target.bleedMana(-total, false, false);
            return;
        }

        if (total > 0) {

            target.regenerateMana(total, true, false);
            return;
        }

        System.out.println(target.getName() + " regenerated a normal amount of mana.");
        Tools.sleep(1000);
    }
}
