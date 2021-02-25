package IRE.Combat.Actions.AttackActions.PhysicalAttacks;

import IRE.Audio.AudioStream;
import IRE.Combat.Actions.AttackActions.AttackAction;

public abstract class PhysicalAttack extends AttackAction {

    public PhysicalAttack(String name, String description,
                          AudioStream SOUND, int DURATION, int DELAY, int coefficient) {
        super(name, description, SOUND, DURATION, DELAY, coefficient);
    }
}
