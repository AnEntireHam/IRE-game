package ire.combat.statuseffects.generativeeffect;

import ire.tools.Tools;

public class ManaRegeneration extends ManaGenerative {

    public ManaRegeneration(int effectLevel) {
        super("Mana Regeneration", "MREG", "Target gains additional mana at end of each turn.",
                true, false, 1, 5, effectLevel, 1, 0,
                " stopped gaining bonus mana.");
    }

    @Override
    protected void displayResult(String defender, boolean success, boolean original) {

        if (success) {
            if (original) {
                System.out.println(defender + " started gaining bonus mana!");
            } else {
                System.out.println(defender + " started gaining even more mana!");
            }

        } else {
            if (original) {
                System.out.println(defender + " didn't start gaining extra mana.");
            } else {
                System.out.println(defender + " didn't start gaining even more mana.");
            }
        }
        Tools.sleep(1000);
    }
}
