package ire.combat.actions.attackactions.spellattacks;

import ire.audio.AudioStream;

public class Celestial extends SpellAttack {

    public Celestial(int spellLevel) {
        super("Celestial", "Deals considerable damage.",
                new AudioStream("celestial"), 2000, 1200, 1.2f,
                new String[]{"Beam", "Blast", "Burst"}, 3, spellLevel,
                 "%s concentrates the ferocity of the stars on %s.");
    }
}
