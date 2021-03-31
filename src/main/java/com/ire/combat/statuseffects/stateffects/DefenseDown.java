package com.ire.combat.statuseffects.stateffects;

public class DefenseDown extends StatEffect {

    public DefenseDown(int effectLevel) {
        super("Defense Down", "DEF", "Lowers the defense of the afflicted target.",
                1, 5, STANDARD_DEBUFF_CONDITIONS,
                effectLevel, 0.60f, 0.075f, -0.20f, -0.05f);
    }
}
