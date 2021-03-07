package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;
import ire.combat.actions.attackactions.spellattacks.SpellAttack;
import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;

public abstract class DebuffSpell extends SpellAttack {

    protected StatusEffect debuff;

    public DebuffSpell(String name, String description, AudioStream SOUND, int DURATION, int DELAY,
                       float coefficient, String[] postfixNames,  int baseManaCost, int spellLevel, String flavorText,
                       StatusEffect debuff) {
        super(name, description, SOUND, DURATION, DELAY, coefficient, postfixNames, baseManaCost, spellLevel,
                flavorText);

        this.debuff = debuff;
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        super.execute(attacker, defender);
        this.debuff.apply(attacker, defender);
    }

    public StatusEffect getDebuff() {
        return debuff;
    }
}
