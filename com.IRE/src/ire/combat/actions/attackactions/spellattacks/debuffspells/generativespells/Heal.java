package ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells;

import ire.audio.AudioStream;
import ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import ire.combat.statuseffects.generativeeffect.GenerativeEffect;
import ire.combat.statuseffects.generativeeffect.Regeneration;
import ire.entities.Entity;
import ire.tools.Tools;

import java.util.Formatter;

public class Heal extends DebuffSpell {

    public Heal(int spellLevel) {
        super("Heal", "Deals weak damage, and inflicts regeneration.",
                new AudioStream("placeholder"), 2000, 1152, 0.60f,
                new String[]{"Draw", "Sap", "Drain"}, 3, spellLevel,
                "%s imbues %s with life.", new Regeneration(spellLevel));
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        Formatter parser = new Formatter();

        damage = Math.round(attacker.getCurMag() * coefficient);
        damage = Math.round(damage * ((spellLevel - 1) * levelDamage + 1));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(parser.format(flavorText, attacker.getName(), defender.getName()));

        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.incrementMan(-baseManaCost);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.bEffects.takeDamage(damage, true);
        ((GenerativeEffect) debuff).setStrength(damage);
        debuff.apply(attacker, defender);
    }
}
