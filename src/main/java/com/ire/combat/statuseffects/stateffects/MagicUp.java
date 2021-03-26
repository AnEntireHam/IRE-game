package com.ire.combat.statuseffects.stateffects;

import com.ire.combat.statuseffects.RemoveCondition;

public class MagicUp extends StatEffect {

    public MagicUp(int effectLevel) {
        super("Magic Up", "MAG", "Increases the magic of the afflicted target.",
                true, true, 1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.DEATH},
                effectLevel, 1, 0, 0.20f, 0.05f);
    }
}
