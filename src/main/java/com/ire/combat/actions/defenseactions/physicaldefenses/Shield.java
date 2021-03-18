package com.ire.combat.actions.defenseactions.physicaldefenses;

import com.ire.combat.actions.Action;
import com.ire.combat.actions.attackactions.physicalattacks.PhysicalAttack;
import com.ire.entities.Entity;

public class Shield extends PhysicalDefense {

    public Shield() {
        super("Shield", "Reduces damage of physical attacks", 1.4f, 1);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        Action attack = attacker.getCurrentAction();

        // Ask Myles about this schmaneuver
        //String name = attack.getName();
        //if (defender.isAlive() && (name.equals("Stab") || name.equals("Lunge"))) {

        if (attack instanceof PhysicalAttack) {

            ((PhysicalAttack) attack).incrementDamage(
                Math.round((defender.getCurDef() * this.physBoost * physCoefficientDef) +
                        (defender.getCurMag() * physCoefficientMag)));
        } else {

            super.execute(attacker, defender);
        }
    }
}
