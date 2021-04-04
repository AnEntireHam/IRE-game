package com.ire.combat.actions.attackactions.spellattacks.debuffspells.statspells;

import com.ire.audio.AudioClip;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import com.ire.combat.statuseffects.stateffects.SpeedDown;

public class Lightning extends DebuffSpell {

    public Lightning(int spellLevel) {
        super("Lightning", "Deals moderate damage, and may lower speed",
                new AudioClip("lightning"), 2000, 778, 0.85f,
                new String[]{"Bolt", "Ball", "Storm"}, 3, spellLevel,
                "%s summons enraged and thunderous clouds above %s.", new SpeedDown(spellLevel));
    }
}
