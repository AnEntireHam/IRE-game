package ire.combat.statuseffects.generativeeffect;

import ire.entities.Entity;
import ire.tools.Tools;

public abstract class HealthGenerative extends GenerativeEffect {

    public HealthGenerative(String name, String description, boolean display, boolean percentage,
                            int stacks, int duration, int effectLevel, float baseProbability, float levelProbability,
                            String expirationMessage) {
        super(name, "REG", description, display, percentage,
                stacks, duration, effectLevel, baseProbability, levelProbability, expirationMessage);

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
            System.out.println(defender + " didn't start bleeding.");  //  Generalize these messages later.
        }
        Tools.sleep(1000);
    }

    public void combineEffects(Entity target, int total) {

        if (total < 0) {

            if (target.isAlive()) {
                System.out.println(target.getName() + " bled for " + total + " damage.");
                target.bEffects.takeDamage(total, false);
                Tools.sleep(1000);
            } else {
                target.bEffects.takeDamage(total, false);
            }

        } else if (total > 0) {

            target.bEffects.heal(total, true);
            Tools.sleep(1000);

        } else {

            System.out.println(target.getName() + " regenerated as much health as they lost.");
        }
    }

    @Override
    public String generateDisplay() {
        return null;
    }
}
