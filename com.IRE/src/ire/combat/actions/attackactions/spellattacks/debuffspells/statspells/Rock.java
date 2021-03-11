package ire.combat.actions.attackactions.spellattacks.debuffspells.statspells;

import ire.audio.AudioStream;
import ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import ire.combat.statuseffects.stateffects.HealthDown;

;

public class Rock extends DebuffSpell {

    public Rock(int spellLevel) {
        super("Rock", "Deals moderate damage, and may lower max health",
                new AudioStream("rock"), 2000, 778, 0.80f,
                new String[]{"Pelt", "Volley", "Smash"}, 3, spellLevel,
                "%s raises stones over %s's head.", new HealthDown(spellLevel));
    }
}