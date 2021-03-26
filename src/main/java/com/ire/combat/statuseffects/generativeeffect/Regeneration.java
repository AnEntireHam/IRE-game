package com.ire.combat.statuseffects.generativeeffect;

import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.tools.Tools;

public class Regeneration extends HealthGenerative{

    public Regeneration(int effectLevel) {
        super("Regenerate", "Target regains health at end of each turn",
                true, false, 1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.DEATH},
                effectLevel, 1, 0, " stopped regenerating health.");
    }

    @Override
    protected void displayResult(String defenderName, boolean success, boolean original) {

        if (success) {
            if (original) {
                System.out.println(defenderName + " started regenerating health!");
            } else {
                System.out.println(defenderName + "' regeneration increased!");
            }

        } else {
            if (original) {
                System.out.println(defenderName + " didn't start regenerating.");
            } else {
                System.out.println(defenderName + "'s regeneration didn't increase.");
            }
        }
        Tools.sleep(1000);
    }
}
