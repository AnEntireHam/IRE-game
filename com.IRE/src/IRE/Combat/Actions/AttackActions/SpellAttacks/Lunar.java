package IRE.Combat.Actions.AttackActions.SpellAttacks;

import IRE.Audio.AudioStream;
import IRE.Entities.Entity;
import IRE.Tools.Tools;

public class Lunar extends SpellAttack {

    private float baseHealthCost = 0.5f;

    public Lunar(int spellLevel) {
        super("Lunar", "Deals substantial damage, but also hurts the caster",
                new AudioStream("lunar"), 2000, 500, 3,
                new String[]{"Beam", "Blast", "Burst"}, 3, spellLevel);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        damage = (int) (attacker.getCurMag() * coefficient);
        damage = Tools.round(damage * ((spellLevel - 1) * 0.5 + 1));

        int healthCost = Tools.round((baseHealthCost * attacker.getCurMag()));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(attacker.getName() + " inflicts " + healthCost + " damage on themselves to charge a spell...");
        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.incrementMan(-baseManaCost);
        attacker.bEffects.takeDamage(healthCost, false);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.bEffects.takeDamage(damage, true);

    }

    public float getBaseHealthCost() {
        return baseHealthCost;
    }

    public void setBaseHealthCost(float baseHealthCost) {
        this.baseHealthCost = baseHealthCost;
    }

}
