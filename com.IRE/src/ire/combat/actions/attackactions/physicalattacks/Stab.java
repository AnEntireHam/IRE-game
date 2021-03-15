package ire.combat.actions.attackactions.physicalattacks;

import ire.audio.AudioStream;
import ire.entities.Entity;
import ire.tools.Tools;

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

        defender.takeDamage(damage, true);
    }
}
