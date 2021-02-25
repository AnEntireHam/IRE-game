package IRE.Combat.Actions.AttackActions.SpellAttacks;

import IRE.Audio.AudioStream;
import IRE.Entities.Entity;
import IRE.Tools.Tools;

public class Celestial extends SpellAttack {

    public Celestial(int spellLevel) {
        super("Celestial", "Deals considerable magical damage.",
                new AudioStream("celestial"), 2000, 1200, 1.2f,
                new String[]{"Beam", "Blast", "Burst"}, 3, spellLevel);
    }

    public void execute(Entity attacker, Entity defender) {

        damage = (int) (attacker.getCurMag() * coefficient);

        damage = Tools.round(damage * ((spellLevel - 1) * 0.5 + 1));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(attacker.getName() + " concentrates the ferocity of the stars on " + defender.getName() + ".");
        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.incrementMan(-baseManaCost);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.bEffects.takeDamage(damage, true);
    }
}
