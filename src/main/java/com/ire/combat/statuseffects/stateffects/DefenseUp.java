package com.ire.combat.statuseffects.stateffects;

public class DefenseUp extends StatEffect {

    public DefenseUp(int effectLevel) {
        super("Defense Up", "DEF", "Increases the defense of the afflicted target.",
                1, 5, STANDARD_BUFF_CONDITIONS,
                effectLevel, 1, 0, 0.20f, 0.05f);
    }
}
