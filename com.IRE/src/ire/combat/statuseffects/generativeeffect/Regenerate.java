package ire.combat.statuseffects.generativeeffect;

public class Regenerate extends HealthGenerative{

    public Regenerate(int effectLevel) {
        super("Regenerate", "Target regains health at end of each turn",
                true, false, 1, 5, effectLevel, 1, 0,
                " stopped regenerating health.");
    }
}
