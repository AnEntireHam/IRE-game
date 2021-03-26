package com.ire.combat.actions.attackactions.physicalattacks;

import com.ire.audio.AudioStream;
import com.ire.entities.Entity;
import com.ire.tools.Tools;

public class Lunge extends PhysicalAttack {

    // TODO: Consider adding some fields for the special timing case.
    private int counterDamage;

    public Lunge() {
        super("Lunge", "Lunge the enemy for a risky attack",
                new AudioStream("lunge1"), 2000, 1000, 2);
    }

    public void execute(Entity attacker, Entity defender) {

        System.out.println(attacker.getName() + " lunged at " + defender.getName());
        Tools.sleep(1250);

        calculateDamage(attacker, defender);

        if (counterDamage != -1) {
            return;
        }

        narrateEvents(attacker, defender);

        defender.takeDamage(damage, true);
    }

    @Override
    protected void calculateDamage(Entity attacker, Entity defender) {

        this.damage = (int) (attacker.getCurAtk() * coefficient);
        this.counterDamage = -1;
        defender.getCurAction().execute(attacker, defender);
    }

    @Override
    protected void narrateEvents(Entity attacker, Entity defender) {

        if (defender.isAlive()) {
            Tools.sleep(250);
            System.out.println(defender.getName() + " used " + defender.getCurAction().getName());
            this.SOUND.play();
            Tools.sleep(1000);

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
