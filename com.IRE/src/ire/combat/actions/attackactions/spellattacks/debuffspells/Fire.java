package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;
import ire.combat.statuseffects.stateffects.AttackDown;

public class Fire extends DebuffSpell {

    public Fire(int spellLevel) {
        super("Fire", "Deals moderate damage, and may lower attack",
                new AudioStream("fire"), 2000, 1620, 0.85f,
                new String[]{"Bolt", "Ball", "Storm"}, 3, spellLevel,
                "%s ignites the air around %s.", new AttackDown(spellLevel));
    }
}
