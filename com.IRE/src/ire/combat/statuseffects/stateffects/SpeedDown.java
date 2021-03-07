package ire.combat.statuseffects.stateffects;

public class SpeedDown extends StatEffect {

    public SpeedDown(int effectLevel) {
        super("Speed Down", "SPD", "Lowers the speed of the afflicted target.",
                true, true, 1, 5, effectLevel, 0.60f, 0.075f,
                -0.20f, -0.05f);
    }
}