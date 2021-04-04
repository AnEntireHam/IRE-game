package com.ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells;

import com.ire.audio.AudioClip;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import com.ire.combat.statuseffects.generativeeffect.GenerativeEffect;
import com.ire.combat.statuseffects.generativeeffect.Regeneration;
import com.ire.entities.Entity;
import com.ire.tools.Tools;

import java.util.Formatter;

public class Heal extends DebuffSpell {

    // TODO: Replace with method that doesn't damage to apply regen.
    public Heal(int spellLevel) {
        super("Heal", "Deals weak damage, and inflicts regeneration.",
                new AudioClip("placeholder"), 2000, 1152, 0.60f,
                new String[]{"Draw", "Sap", "Drain"}, 3, spellLevel,
                "%s imbues %s with life.", new Regeneration(spellLevel));
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        calculateDamage(attacker, defender);

        Formatter parser = new Formatter();
        System.out.println(parser.format(flavorText, attacker.getName(), defender.getName()));

        Tools.sleep(DELAY);
        this.SOUND.play();


        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        attacker.incrementMan(-baseManaCost);
        defender.takeDamage(damage, true);
        ((GenerativeEffect) debuff).setStrength(damage);
        debuff.apply(attacker, defender);
    }
}
