package com.ire.combat.statuseffects.stateffects;

import com.ire.combat.statuseffects.RemoveCondition;

public class DefenseUp extends StatEffect {

    public DefenseUp(int effectLevel) {
        super("Defense Up", "DEF", "Increases the defense of the afflicted target.",
                1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.DEATH},
                effectLevel, 1, 0, 0.20f, 0.05f);
    }
}
