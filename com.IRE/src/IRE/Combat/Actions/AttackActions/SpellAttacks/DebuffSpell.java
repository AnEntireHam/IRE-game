package ire.combat.actions.attackactions.spellattacks;

import ire.audio.AudioStream;

public abstract class DebuffSpell extends SpellAttack {

    // Busted because of debuff implementation.
    // Will replace with debuff object later.
    protected int debuffID;

    public DebuffSpell(String name, String description, AudioStream SOUND, int DURATION, int DELAY,
                       float coefficient, String[] postfixNames, int spellLevel, int baseManaCost, int debuffID) {
        super(name, description, SOUND, DURATION, DELAY, coefficient, postfixNames, spellLevel, baseManaCost);

        this.debuffID = debuffID;
    }

    public int getDebuffID() {
        return debuffID;
    }

    public void setDebuffID(int debuffID) {
        this.debuffID = debuffID;
    }
}
