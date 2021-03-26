package com.ire.combat.actions.attackactions.spellattacks.debuffspells;

import com.ire.audio.AudioStream;
import com.ire.combat.actions.attackactions.spellattacks.SpellAttack;
import com.ire.combat.actions.defenseactions.spelldefenses.Mirror;
import com.ire.combat.statuseffects.StatusEffect;
import com.ire.entities.Entity;

public abstract class DebuffSpell extends SpellAttack {

    protected StatusEffect debuff;

    public DebuffSpell(String name, String description, AudioStream SOUND, int DURATION, int DELAY,
                       float coefficient, String[] postfixNames, int baseManaCost, int spellLevel, String flavorText,
                       StatusEffect debuff) {
        super(name, description, SOUND, DURATION, DELAY, coefficient, postfixNames, baseManaCost, spellLevel,
                flavorText);

        this.debuff = debuff;
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        super.execute(attacker, defender);

        if (defender.getCurAction() instanceof Mirror) {
            this.debuff.apply(defender, attacker);
        }
        this.debuff.apply(attacker, defender);
    }

    public StatusEffect getDebuff() {
        return debuff;
    }

    @Override
    public String toString() {
        return "DebuffSpell{" +
                "debuff=" + debuff +
                "} " + super.toString();
    }
}
