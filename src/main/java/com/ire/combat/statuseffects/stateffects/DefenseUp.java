package com.ire.combat.statuseffects.stateffects;

public class DefenseUp extends StatEffect {

    public DefenseUp(int effectLevel) {
        super("Defense Up", "DEF", "Increases the defense of the afflicted target.",
                true, true, 1, 5, effectLevel, 1, 0,
                0.20f, 0.05f);
    }
}
