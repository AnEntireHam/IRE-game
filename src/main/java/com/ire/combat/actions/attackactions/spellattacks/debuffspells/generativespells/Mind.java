package com.ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells;

import com.ire.audio.AudioClip;
import com.ire.combat.statuseffects.generativeeffect.ManaBleed;

public class Mind extends GenerativeSpell {

    public Mind(int spellLevel) {
        super("Mind", "Deals modest damage, and may inflict mana drain.",
                new AudioClip("mind"), 2000, 2000, 0.75f,
                new String[]{"Draw", "Sap", "Drain"}, 3, spellLevel,
                "%s stupefies %s with sorcerous paradoxes.", new ManaBleed(spellLevel));
    }
}
