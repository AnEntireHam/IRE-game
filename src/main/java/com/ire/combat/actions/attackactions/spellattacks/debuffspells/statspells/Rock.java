package com.ire.combat.actions.attackactions.spellattacks.debuffspells.statspells;

import com.ire.audio.AudioClip;
import com.ire.audio.AudioStream;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.DebuffSpell;
import com.ire.combat.statuseffects.stateffects.HealthDown;

;

public class Rock extends DebuffSpell {

    public Rock(int spellLevel) {
        super("Rock", "Deals moderate damage, and may lower max health",
                new AudioClip("rock"), 2000, 778, 0.80f,
                new String[]{"Pelt", "Volley", "Smash"}, 3, spellLevel,
                "%s raises stones over %s's head.", new HealthDown(spellLevel));
    }
}
