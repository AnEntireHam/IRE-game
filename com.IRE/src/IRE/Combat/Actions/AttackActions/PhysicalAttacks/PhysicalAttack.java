package ire.combat.actions.attackactions.physicalattacks;

import ire.audio.AudioStream;
import ire.combat.actions.attackactions.AttackAction;

public abstract class PhysicalAttack extends AttackAction {

    public PhysicalAttack(String name, String description,
                          AudioStream SOUND, int DURATION, int DELAY, int coefficient) {
        super(name, description, SOUND, DURATION, DELAY, coefficient);
    }
}
