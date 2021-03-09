package ire.combat.statuseffects.generativeeffect;

import ire.entities.Entity;
import ire.tools.Tools;

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
    protected void displayResult(String defender, boolean success) {
        if (success) {
            System.out.println(defender + " started losing mana!");
        } else {
            System.out.println(defender + " didn't start losing mana.");
        }
        Tools.sleep(1000);
    }

    @Override
    public void combineEffects(Entity target, int total) {
        return;
    }

    @Override
    public boolean incrementEffect(Entity target) {

        if (target.getMan() - strength > 0) {
            if (target.isAlive()) {
                System.out.println(target.getName() + " lost " + strength + " mana.");
            }
            target.incrementMan(-strength);
        } else {
            if (target.isAlive()) {
                System.out.println(target.getName() + " lost " + target.getMan() + " mana.");
            }
            target.setMan(0);
        }
        Tools.sleep(1000);

        this.incrementDuration(-1);
        if (this.duration <= 0) {
            remove(target);
            return true;
        }
        return false;
    }

    @Override
    public String generateDisplay() {
        return null;
    }
}
