package ire.combat.statuseffects.generativeeffect;

public class ManaRegeneration extends ManaGenerative {

    public ManaRegeneration(int effectLevel) {
        super("Mana Regeneration", "MREG", "Target gains additional mana at end of each turn.",
                true, false, 1, 5, effectLevel, 1, 0,
                " stopped gaining additional mana.");
    }
}
