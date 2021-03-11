package ire.combat.statuseffects.generativeeffect;

import ire.tools.Tools;

public class Bleed extends HealthGenerative {

    public Bleed(int effectLevel) {
        super("Bleed", "Target loses health at end of each turn.",
                true, false, 1, 5, effectLevel, 0.2f, 0.1f,
                " stopped bleeding.");
    }

    @Override
    protected void displayResult(String defenderName, boolean success, boolean original) {

        if (success) {
            if (original) {
                System.out.println(defenderName + " started bleeding!");
            } else {
                System.out.println(defenderName + "'s bleeding worsened!");
            }

        } else {
            if (original) {
                System.out.println(defenderName + " didn't start bleeding.");
            } else {
                System.out.println(defenderName + "'s bleeding didn't worsen.");
            }
        }
        Tools.sleep(1000);
    }
}
