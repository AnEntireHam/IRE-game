package ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells;

import ire.audio.AudioStream;
import ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import ire.combat.statuseffects.generativeeffect.Bleed;
import ire.combat.statuseffects.generativeeffect.GenerativeEffect;
import ire.entities.Entity;
import ire.tools.Tools;

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

        Formatter parser = new Formatter();

        damage = Tools.round(attacker.getCurMag() * coefficient);
        damage = Tools.round(damage * ((spellLevel - 1) * levelDamage + 1));

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
