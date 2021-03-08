package ire.combat.actions.defenseactions.physicaldefenses;

import ire.combat.actions.Action;
import ire.combat.actions.attackactions.physicalattacks.Lunge;
import ire.entities.Entity;
import ire.tools.Tools;

public class Counter extends PhysicalDefense {

    // final keyword may change with masteries
    @SuppressWarnings("FieldCanBeLocal")
    private final float COUNTER_COEFFICIENT = 1.5f;

    public Counter() {
        super("Counter", "Parries lunging enemies for great damage", 1, 1);
    }

    // URGENT: ADD IF CLAUSE TO DETERMINE IF ATTACK IS PHYSICAL OR NOT
    // Ask myles about "defender.isAlive()" clause
    @Override
    public void execute(Entity attacker, Entity defender) {

        Action attack = attacker.getCurrentAction();

        if (attack instanceof Lunge) {

            int counterDamage = Math.round((defender.getCurAtk() * COUNTER_COEFFICIENT) +
                    (defender.getCurSpd() - attacker.getCurSpd()));

            ((Lunge) attack).setCounterDamage(Math.round(defender.getCurDef() * this.physBoost));

            // remember to play sound
            System.out.println("... but was countered!");
            ((Lunge) attack).getSOUND().play();
            Tools.sleep(1000);
            attacker.bEffects.takeDamage(counterDamage, true);

        } else {

            super.execute(attacker, defender);
        }
    }
}
