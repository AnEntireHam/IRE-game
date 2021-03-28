package com.ire.combat.statuseffects.stateffects;

import com.ire.combat.statuseffects.RemoveCondition;

public class DefenseDown extends StatEffect {

    public DefenseDown(int effectLevel) {
        super("Defense Down", "DEF", "Lowers the defense of the afflicted target.",
                1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.END_BATTLE},
                effectLevel, 0.60f, 0.075f, -0.20f, -0.05f);
    }
}
