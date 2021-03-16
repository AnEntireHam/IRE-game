package com.ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells;

import com.ire.audio.AudioStream;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import com.ire.combat.statuseffects.generativeeffect.ManaBleed;
import com.ire.entities.Entity;
import com.ire.tools.Tools;
import com.ire.combat.statuseffects.generativeeffect.GenerativeEffect;

import java.util.Formatter;

public class Mind extends DebuffSpell {

    public Mind(int spellLevel) {
        super("Mind", "Deals modest damage, and may inflict mana drain.",
                new AudioStream("mind"), 2000, 2000, 0.75f,
                new String[]{"Draw", "Sap", "Drain"}, 3, spellLevel,
                "%s stupefies %s with sorcerous paradoxes.", new ManaBleed(spellLevel));
        //  Eventually change placeholder length.
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

        defender.takeDamage(damage, true);
        ((GenerativeEffect) debuff).setStrength(damage);  //  Dunno if this is OP, maybe not?
        debuff.apply(attacker, defender);
    }
}
