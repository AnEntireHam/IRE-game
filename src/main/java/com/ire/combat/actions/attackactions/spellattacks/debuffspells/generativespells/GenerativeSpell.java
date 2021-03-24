package com.ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells;

import com.ire.audio.AudioStream;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import com.ire.combat.actions.defenseactions.spelldefenses.Mirror;
import com.ire.combat.statuseffects.StatusEffect;
import com.ire.combat.statuseffects.generativeeffect.GenerativeEffect;
import com.ire.entities.Entity;

public class GenerativeSpell extends DebuffSpell {

    public GenerativeSpell(String name, String description, AudioStream SOUND, int DURATION, int DELAY,
                           float coefficient, String[] postfixNames, int baseManaCost, int spellLevel,
                           String flavorText, StatusEffect debuff) {

        super(name, description, SOUND, DURATION, DELAY, coefficient, postfixNames, baseManaCost, spellLevel,
                flavorText, debuff);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        calculateDamage(attacker, defender);

        narrateEvents(attacker, defender);

        attacker.incrementMan(-baseManaCost);

        if (defender.getCurrentAction() instanceof Mirror) {

            attacker.takeDamage(damage, true);

            ((GenerativeEffect) debuff).setStrength(-damage);
            debuff.apply(defender, attacker);
            return;
        }

        defender.takeDamage(damage, true);

        ((GenerativeEffect) debuff).setStrength(-damage);
        debuff.apply(attacker, defender);
    }
}
