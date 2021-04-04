package com.ire.combat.actions.attackactions.spellattacks;

import com.ire.audio.AudioClip;

public class Celestial extends SpellAttack {

    public Celestial(int spellLevel) {
        super("Celestial", "Deals considerable damage.",
                new AudioClip("celestial"), 2000, 1200, 1.2f,
                new String[]{"Beam", "Blast", "Burst"}, 3, spellLevel,
                 "%s concentrates the ferocity of the stars on %s.");
    }
}
