package ire.combat.statuseffects.generativeeffect;

public class Bleed extends HealthGenerative {

    public Bleed(int effectLevel) {
        super("Bleed", "Target loses health at end of each turn.",
                true, false, 1, 5, effectLevel, 0.2f, 0.1f,
                " stopped bleeding.");
    }
}
