package com.ire.combat.statuseffects.stateffects;

import com.ire.combat.statuseffects.RemoveCondition;

public class HealthDown extends StatEffect {

    public HealthDown(int effectLevel) {
        super("Health Down", "HLH", "Lowers the maximum health of the afflicted target.",
                1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.END_BATTLE, RemoveCondition.LEVEL_UP},
                effectLevel, 0.60f, 0.075f, -0.20f, -0.05f);
    }
}
