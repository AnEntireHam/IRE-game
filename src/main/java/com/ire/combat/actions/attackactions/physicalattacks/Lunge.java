package com.ire.combat.actions.attackactions.physicalattacks;

import com.ire.audio.AudioClip;
import com.ire.entities.Entity;
import com.ire.tools.PrintControl;

public class Lunge extends PhysicalAttack {


    private int counterDamage;

    public Lunge() {
        super("Lunge", "Lunge the enemy for a risky attack",
                new AudioClip("lunge1"), 2000, 1000, 2);
    }

    public void execute(Entity attacker, Entity defender) {

        System.out.println(attacker.getName() + " lunged at " + defender.getName());
        PrintControl.sleep(1250);

        calculateDamage(attacker, defender);

        if (counterDamage != -1) {
            return;
        }

        narrateEvents(attacker, defender);

        defender.takeDamage(damage, true);
    }

    @Override
    protected void calculateDamage(Entity attacker, Entity defender) {

        this.damage = (int) (attacker.getCurStat("atk") * coefficient);
        this.counterDamage = -1;
        defender.getCurAction().execute(attacker, defender);
    }

    @Override
    protected void narrateEvents(Entity attacker, Entity defender) {

        if (defender.isAlive()) {
            PrintControl.sleep(250);
            System.out.println(defender.getName() + " used " + defender.getCurAction().getName());
            this.SOUND.play();
            PrintControl.sleep(1000);

        } else {
            this.SOUND.play();
        }
    }


    public int getCounterDamage() {
        return counterDamage;
    }

    public void setCounterDamage(int counterDamage) {
        this.counterDamage = counterDamage;
    }
}
