package com.ire.combat.actions.attackactions.spellattacks.debuffspells.statspells;

import com.ire.audio.AudioClip;
import com.ire.audio.AudioStream;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import com.ire.combat.statuseffects.stateffects.AttackDown;

public class Fire extends DebuffSpell {

    public Fire(int spellLevel) {
        super("Fire", "Deals moderate damage, and may lower attack",
                new AudioClip("fire"), 2000, 1620, 0.85f,
                new String[]{"Bolt", "Ball", "Storm"}, 3, spellLevel,
                "%s ignites the air around %s.", new AttackDown(spellLevel));
    }
}
