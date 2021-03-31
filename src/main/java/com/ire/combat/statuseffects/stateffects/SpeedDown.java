package com.ire.combat.statuseffects.stateffects;

public class SpeedDown extends StatEffect {

    public SpeedDown(int effectLevel) {
        super("Speed Down", "SPD", "Lowers the speed of the afflicted target.",
                1, 5, STANDARD_DEBUFF_CONDITIONS,
                effectLevel, 0.60f, 0.075f, -0.20f, -0.05f);
    }
}
