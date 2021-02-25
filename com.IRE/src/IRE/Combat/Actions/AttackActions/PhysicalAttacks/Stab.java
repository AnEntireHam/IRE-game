package IRE.Combat.Actions.AttackActions.PhysicalAttacks;

import IRE.Audio.AudioStream;
import IRE.Entities.Entity;
import IRE.Tools.Tools;

public class Stab extends PhysicalAttack {

    public Stab() {
        super("Stab", "Stab the enemy, dealing modest damage.",
                new AudioStream("hit1"), 2000, 1000, 1);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        this.damage = (int) (attacker.getCurAtk() * coefficient);

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(attacker.getName() + " stabbed " + defender.getName());
        Tools.sleep(DELAY);
        this.SOUND.play();

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.bEffects.takeDamage(damage, true);
    }
}
