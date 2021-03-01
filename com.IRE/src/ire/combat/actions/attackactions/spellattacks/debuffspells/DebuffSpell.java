package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;
import ire.combat.actions.attackactions.spellattacks.SpellAttack;
import ire.combat.statuseffects.stateffects.StatEffect;

public abstract class DebuffSpell extends SpellAttack {

    // Busted because of debuff implementation.
    // Will replace with debuff object later.
    protected StatEffect debuff;

    public DebuffSpell(String name, String description, AudioStream SOUND, int DURATION, int DELAY,
                       float coefficient, String[] postfixNames,  int baseManaCost, int spellLevel, StatEffect debuff) {
        super(name, description, SOUND, DURATION, DELAY, coefficient, postfixNames, baseManaCost, spellLevel);

        this.debuff = debuff;
    }

    public StatEffect getDebuff() {
        return debuff;
    }
}
