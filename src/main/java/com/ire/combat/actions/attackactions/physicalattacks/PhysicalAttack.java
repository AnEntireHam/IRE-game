package com.ire.combat.actions.attackactions.physicalattacks;

import com.ire.audio.AudioClip;
import com.ire.combat.actions.attackactions.AttackAction;

public abstract class PhysicalAttack extends AttackAction {

    public PhysicalAttack(String name, String description,
                          AudioClip SOUND, int DURATION, int DELAY, int coefficient) {
        super(name, description, SOUND, DURATION, DELAY, coefficient);
    }
}
