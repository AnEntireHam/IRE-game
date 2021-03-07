package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;
import ire.combat.statuseffects.stateffects.AttackDown;

public class Lightning extends DebuffSpell {

    public Lightning(int spellLevel) {
        super("Lightning", "Deals moderate damage, and may lower speed",
                new AudioStream("lightning"), 2000, 778, 0.85f,
                new String[]{"Bolt", "Ball", "Storm"}, 3, spellLevel,
                "%s summons enraged and thunderous clouds above %s.", new AttackDown(spellLevel));
    }
}
