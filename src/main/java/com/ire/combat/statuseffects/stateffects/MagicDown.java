package com.ire.combat.statuseffects.stateffects;

import com.ire.combat.statuseffects.RemoveCondition;

public class MagicDown extends StatEffect {

    public MagicDown(int effectLevel) {
        super("Magic Down", "MAG", "Lowers the magic of the afflicted target.",
                true, true, 1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.END_BATTLE},
                effectLevel, 0.60f, 0.075f, -0.20f, -0.05f);
    }
}
