package ire.combat.statuseffects.stateffects;

public class HealthUp extends StatEffect {

    public HealthUp(int effectLevel) {
        super("Health Up", "HLH", "Increases the maximum health of the afflicted target.",
                true, true, 1, 5, effectLevel, 1.0f, 0.0f,
                0.20f, 0.05f);
    }
}
