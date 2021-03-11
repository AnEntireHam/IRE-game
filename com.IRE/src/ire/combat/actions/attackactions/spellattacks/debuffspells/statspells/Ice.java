package ire.combat.actions.attackactions.spellattacks.debuffspells.statspells;

import ire.audio.AudioStream;
import ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import ire.combat.statuseffects.stateffects.DefenseDown;

public class Ice extends DebuffSpell {

    public Ice(int spellLevel) {
        super("Ice", "Deals moderate damage, and may lower attack",
                new AudioStream("placeholder"), 2000, 1152, 0.85f,
                new String[]{"Pelt", "Volley", "Storm"}, 3, spellLevel,
                "%s conjures an unnatural hailstorm above %s.", new DefenseDown(spellLevel));
    }
}