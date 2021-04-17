package com.ire.combat.statuseffects.generativeeffect;

import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.entities.Entity;
import com.ire.tools.PrintControl;

public abstract class HealthGenerative extends GenerativeEffect {

    public HealthGenerative(String name, String description,
                            int stacks, int duration, RemoveCondition[] removeConditions,
                            int effectLevel, float baseProbability, float levelProbability, String expirationMessage) {

        super(name, "REG", description, stacks, duration,
                removeConditions, effectLevel, baseProbability, levelProbability, expirationMessage);
    }

    @Override
    public void apply(Entity attacker, Entity defender) {

        statProbability = (strengthCoefficient * ((float) Math.abs(strength) / (float) defender.getCurStat("hlh")));
        if (statProbability < 0.05f) {
            statProbability = 0;
        }
        super.apply(attacker, defender);
    }

    @Override
    public void execute(Entity target, int total) {

        if (total < 0) {

            if (target.isAlive()) {
                System.out.println(target.getName() + " bled for " + -total + " damage.");
                PrintControl.sleep(1000);
            }
            target.takeDamage(-total, false);
            return;

        }

        if (total > 0) {

            target.regenerateHealth(total, true, false);
            return;
        }

        System.out.println(target.getName() + " regenerated as much health as they bled for.");
        PrintControl.sleep(1000);
    }
}
