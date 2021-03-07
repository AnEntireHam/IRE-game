package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;
import ire.combat.statuseffects.generativeeffect.Bleed;

public class Life extends DebuffSpell {

    public Life(int spellLevel) {
        super("Life", "Deals moderate damage, and may lower attack",
                new AudioStream("placeholder"), 2000, 1152, 0.60f,
                new String[]{"Draw", "Sap", "Drain"}, 3, spellLevel,
                "%s expels the life from %s.", new Bleed(spellLevel));
    }
}
