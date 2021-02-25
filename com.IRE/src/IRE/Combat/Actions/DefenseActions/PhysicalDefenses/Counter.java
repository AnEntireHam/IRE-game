package IRE.Combat.Actions.DefenseActions.PhysicalDefenses;

import IRE.Combat.Actions.Action;
import IRE.Combat.Actions.AttackActions.PhysicalAttacks.Lunge;
import IRE.Combat.Actions.AttackActions.PhysicalAttacks.PhysicalAttack;
import IRE.Entities.Entity;
import IRE.Tools.Tools;

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

            int counterDamage = Tools.round((defender.getCurAtk() * COUNTER_COEFFICIENT) +
                    (defender.getCurSpd() - attacker.getCurSpd()));

            ((Lunge) attack).setCounterDamage(Tools.round(defender.getCurDef() * this.physBoost));

            // remember to play sound
            System.out.println("... but was countered!");
            ((Lunge) attack).getSOUND().play();
            Tools.sleep(1000);
            attacker.bEffects.takeDamage(counterDamage, true);

        } else if (attack instanceof PhysicalAttack) {

            ((PhysicalAttack) attacker.getCurrentAction()).incrementDamage(
                    Tools.round(defender.getCurDef() * PHYS_COEFF_DEF));
        }
    }
}
