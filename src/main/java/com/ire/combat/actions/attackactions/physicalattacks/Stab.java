package com.ire.combat.actions.attackactions.physicalattacks;

import com.ire.audio.AudioClip;
import com.ire.entities.Entity;
import com.ire.tools.PrintControl;

public class Stab extends PhysicalAttack {

    public Stab() {
        super("Stab", "Stab the enemy, dealing modest damage.",
                new AudioClip("hit1"), 2000, 1000, 1);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        calculateDamage(attacker, defender);
        narrateEvents(attacker, defender);

        defender.takeDamage(damage, true);
    }

    @Override
    protected void calculateDamage(Entity attacker, Entity defender) {

        this.damage = (int) (attacker.getCurStat("atk") * coefficient);
        defender.getCurAction().execute(attacker, defender);
    }

    @Override
    protected void narrateEvents(Entity attacker, Entity defender) {

        System.out.println(attacker.getName() + " stabbed " + defender.getName());
        PrintControl.sleep(DELAY);
        this.SOUND.play();

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurAction().getName());
            PrintControl.sleep(DURATION - DELAY);
        }
    }

}
