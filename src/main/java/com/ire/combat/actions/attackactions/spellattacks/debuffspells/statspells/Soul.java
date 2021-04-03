package com.ire.combat.actions.attackactions.spellattacks.debuffspells.statspells;

import com.ire.audio.AudioClip;
import com.ire.audio.AudioStream;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import com.ire.combat.statuseffects.stateffects.MagicDown;

public class Soul extends DebuffSpell {

    public Soul(int spellLevel) {
        super("Soul", "Deals moderate damage, and may lower magic",
                new AudioClip("soul"), 2000, 1581, 0.85f,
                new String[]{"Suck", "Sap", "Drain"}, 3, spellLevel,
                "%s unleashes a litany of binding hexes upon %s.", new MagicDown(spellLevel));
    }
}
