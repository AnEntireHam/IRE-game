package com.ire.combat.statuseffects.stateffects;

import com.ire.combat.statuseffects.RemoveCondition;

public class HealthUp extends StatEffect {

    public HealthUp(int effectLevel) {
        super("Health Up", "HLH", "Increases the maximum health of the afflicted target.",
                1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.DEATH},
                effectLevel, 1, 0, 0.20f, 0.05f);
    }
}
