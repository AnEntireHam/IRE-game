package com.ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells;

import com.ire.audio.AudioClip;
import com.ire.combat.statuseffects.generativeeffect.Bleed;

public class Life extends GenerativeSpell {

    public Life(int spellLevel) {
        super("Life", "Deals weak damage, and may inflict bleed.",
                new AudioClip("placeholder"), 2000, 1152, 0.60f,
                new String[]{"Draw", "Sap", "Drain"}, 3, spellLevel,
                "%s expels the life from %s.", new Bleed(spellLevel));
    }
}
