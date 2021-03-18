package com.ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells;

import com.ire.audio.AudioStream;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import com.ire.combat.statuseffects.generativeeffect.Regeneration;
import com.ire.entities.Entity;
import com.ire.tools.Tools;
import com.ire.combat.statuseffects.generativeeffect.GenerativeEffect;

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

        damage = Math.round(attacker.getCurMag() * coefficient);
        damage = Math.round(damage * ((spellLevel - 1) * levelDamage + 1));

        defender.getCurrentAction().execute(attacker, defender);

        Formatter parser = new Formatter();
        System.out.println(parser.format(flavorText, attacker.getName(), defender.getName()));

        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.incrementMan(-baseManaCost);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.takeDamage(damage, true);
        ((GenerativeEffect) debuff).setStrength(damage);
        debuff.apply(attacker, defender);
    }
}
