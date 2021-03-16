package com.ire.combat.statuseffects.stateffects;

public class SpeedUp extends StatEffect {

    public SpeedUp(int effectLevel) {
        super("Speed Up", "SPD", "Increases the speed of the afflicted target.",
                true, true, 1, 5, effectLevel, 1, 0,
                0.20f, 0.05f);
    }
}
