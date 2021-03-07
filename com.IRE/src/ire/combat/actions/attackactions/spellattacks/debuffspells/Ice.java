package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;
import ire.combat.statuseffects.stateffects.DefenseDown;

public class Ice extends DebuffSpell {

    public Ice(int spellLevel) {
        super("Ice", "Deals moderate damage, and may lower attack",
                new AudioStream("ice"), 2000, 1152, 0.85f,
                new String[]{"Pelt", "Volley", "Storm"}, 3, spellLevel,
                "%s conjures an unnatural hailstorm above %s.", new DefenseDown(spellLevel));
    }
}
