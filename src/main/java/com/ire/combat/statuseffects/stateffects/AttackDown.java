package com.ire.combat.statuseffects.stateffects;

import com.ire.combat.statuseffects.RemoveCondition;

public class AttackDown extends StatEffect {

    public AttackDown(int effectLevel) {
        super("Attack Down", "ATK", "Lowers the attack of the afflicted target.",
                1, 5,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.END_BATTLE, RemoveCondition.LEVEL_UP},
                effectLevel, 0.60f, 0.075f, -0.20f, -0.05f);
    }
}
