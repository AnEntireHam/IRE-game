package com.ire.combat.actions.defenseactions.physicaldefenses;

import com.ire.combat.actions.Action;
import com.ire.combat.actions.attackactions.physicalattacks.Lunge;
import com.ire.entities.Entity;
import com.ire.tools.PrintControl;

public class Counter extends PhysicalDefense {

    private static final float COUNTER_COEFFICIENT = 1.5f;

    public Counter() {
        super("Counter", "Parries lunging enemies for great damage",
                1, 1, 1, 1);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        Action attack = attacker.getCurAction();

        if (attack instanceof Lunge) {

            int counterDamage = Math.round((defender.getCurStat("atk") * COUNTER_COEFFICIENT) +
                    (defender.getCurStat("spd") - attacker.getCurStat("spd")));

            ((Lunge) attack).setCounterDamage(Math.max(Math.round(defender.getCurStat("def") * physBoost), 0));

            System.out.println("... but was countered!");
            ((Lunge) attack).getSOUND().play();
            PrintControl.sleep(1000);
            attacker.takeDamage(counterDamage, true);

        } else {

            super.execute(attacker, defender);
        }
    }
}
