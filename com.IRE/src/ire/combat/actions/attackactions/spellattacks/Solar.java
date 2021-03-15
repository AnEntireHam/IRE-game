package ire.combat.actions.attackactions.spellattacks;

import ire.audio.AudioStream;
import ire.entities.Entity;
import ire.tools.Tools;

import java.util.Formatter;

public class Solar extends SpellAttack {

    //private final AudioStream leech = new AudioStream("leech");

    // Text appears a bit quickly.
    public Solar(int spellLevel) {
        super("Solar", "Deals modest damage, but heals the user.",
                new AudioStream("solar"), 2000, 1200, 0.75f,
                new String[]{"Beam", "Blast", "Burst"}, 3, spellLevel,
                "%s locks eyes with %s and... praises the sun?");
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        Formatter parser = new Formatter();

        damage = Math.round(attacker.getCurMag() * coefficient);
        damage = Math.round(damage * ((spellLevel - 1) * 0.5f + 1));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(parser.format(flavorText, attacker.getName(), defender.getName()));

        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.incrementMan(-baseManaCost);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.takeDamage(damage, true);
        attacker.regenerateHealth(damage, true, true);
    }
}
