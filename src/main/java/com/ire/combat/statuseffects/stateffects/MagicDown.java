package com.ire.combat.statuseffects.stateffects;

public class MagicDown extends StatEffect {

    public MagicDown(int effectLevel) {
        super("Magic Down", "MAG", "Lowers the magic of the afflicted target.",
                true, true, 1, 5, effectLevel, 0.60f, 0.075f,
                -0.20f, -0.05f);
    }
}
