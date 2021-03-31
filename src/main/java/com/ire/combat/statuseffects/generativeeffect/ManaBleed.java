package com.ire.combat.statuseffects.generativeeffect;

import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.tools.Tools;

public class ManaBleed extends ManaGenerative {

    public ManaBleed(int effectLevel) {
        super("Mana Drain", "MREG", "Target loses mana at end of each turn.",
                1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.END_BATTLE, RemoveCondition.LEVEL_UP},
                effectLevel, 0.6f, 0.075f, " stopped losing mana.");
    }

    @Override
    protected void displayResult(String defender, boolean success, boolean original) {

        if (success) {
            if (original) {
                System.out.println(defender + " started losing mana!");
            } else {
                System.out.println(defender + " started losing even more mana!");
            }

        } else {
            if (original) {
                System.out.println(defender + " didn't start losing mana.");
            } else {
                System.out.println(defender + " didn't start losing more mana.");
            }
        }
        Tools.sleep(1000);
    }
}
