package ire.combat.statuseffects.generativeeffect;

public class ManaDrain extends ManaGenerative {

    public ManaDrain(int effectLevel) {
        super("Mana Drain", "MREG", "Target loses mana at end of each turn.",
                true, false, 1, 5, effectLevel, 0.6f, 0.075f,
                " stopped losing mana.");
    }
}
