package com.ire.combat.statuseffects.stateffects;

public class AttackUp extends StatEffect {

    public AttackUp(int effectLevel) {
        super("Attack Up", "ATK", "Increases the attack of the afflicted target.",
                1, 5, STANDARD_BUFF_CONDITIONS,
                effectLevel, 1, 0, 0.20f, 0.05f);
    }
}
