package com.ire.combat.actions.attackactions.spellattacks;

import com.ire.audio.AudioStream;
import com.ire.entities.Entity;

public class Solar extends SpellAttack {

    public Solar(int spellLevel) {
        super("Solar", "Deals modest damage, but heals the user.",
                new AudioStream("solar"), 2000, 1200, 0.75f,
                new String[]{"Beam", "Blast", "Burst"}, 3, spellLevel,
                "%s locks eyes with %s and... praises the sun?");
    }

    //  Might clash with mirror's implementation, since aborting in the super might not abort sub.
    @Override
    public void execute(Entity attacker, Entity defender) {

        super.execute(attacker, defender);
        attacker.regenerateHealth(damage, true, true);
    }
}
