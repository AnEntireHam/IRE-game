package ire.combat.actions.attackactions.spellattacks.debuffspells.statspells;

import ire.audio.AudioStream;
import ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import ire.combat.statuseffects.stateffects.MagicDown;

public class Soul extends DebuffSpell {

    public Soul(int spellLevel) {
        super("Soul", "Deals moderate damage, and may lower magic",
                new AudioStream("soul"), 2000, 1581, 0.85f,
                new String[]{"Suck", "Sap", "Drain"}, 3, spellLevel,
                "%s unleashes a litany of binding hexes upon %s.", new MagicDown(spellLevel));
    }
}
