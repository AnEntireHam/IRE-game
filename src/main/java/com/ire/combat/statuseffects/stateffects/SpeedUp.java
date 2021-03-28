package com.ire.combat.statuseffects.stateffects;

import com.ire.combat.statuseffects.RemoveCondition;

public class SpeedUp extends StatEffect {

    public SpeedUp(int effectLevel) {
        super("Speed Up", "SPD", "Increases the speed of the afflicted target.",
                1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.DEATH},
                effectLevel, 1, 0, 0.20f, 0.05f);
    }
}
