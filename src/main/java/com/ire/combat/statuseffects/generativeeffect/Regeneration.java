package com.ire.combat.statuseffects.generativeeffect;

import com.ire.tools.Tools;

public class Regeneration extends HealthGenerative{

    public Regeneration(int effectLevel) {
        super("Regenerate", "Target regains health at end of each turn",
                1, 5, STANDARD_BUFF_CONDITIONS,
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
