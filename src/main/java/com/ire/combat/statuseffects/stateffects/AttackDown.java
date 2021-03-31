package com.ire.combat.statuseffects.stateffects;

public class AttackDown extends StatEffect {

    public AttackDown(int effectLevel) {
        super("Attack Down", "ATK", "Lowers the attack of the afflicted target.",
                1, 5, STANDARD_DEBUFF_CONDITIONS,
                effectLevel, 0.60f, 0.075f, -0.20f, -0.05f);
    }
}
