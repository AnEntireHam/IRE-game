package com.ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells;

import com.ire.combat.statuseffects.generativeeffect.Bleed;
import com.ire.audio.AudioStream;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import com.ire.combat.statuseffects.generativeeffect.GenerativeEffect;
import com.ire.entities.Entity;
import com.ire.tools.Tools;

import java.util.Formatter;

public class Life extends DebuffSpell {

    public Life(int spellLevel) {
        super("Life", "Deals weak damage, and may inflict bleed.",
                new AudioStream("placeholder"), 2000, 1152, 0.60f,
                new String[]{"Draw", "Sap", "Drain"}, 3, spellLevel,
                "%s expels the life from %s.", new Bleed(spellLevel));
    }

    //  Can be generalized with Mind... should it?
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
        ((GenerativeEffect) debuff).setStrength(-damage);
        debuff.apply(attacker, defender);
    }
}
