package ire.combat.statuseffects.stateffects;

import ire.combat.statuseffects.StatusEffect;
import ire.entities.Entity;
import ire.tools.Tools;

public class AttackDown extends StatEffect {

    public AttackDown(int effectLevel) {
        super("Attack Down", "ATK", "Lowers the attack of the afflicted target.",
                true, true, 1, 5, effectLevel, 0.60f, 0.075f,
                -0.20f, -0.05f);
    }
}
