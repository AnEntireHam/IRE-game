package ire.combat.actions.defenseactions.physicaldefenses;

import ire.combat.actions.Action;
import ire.combat.actions.attackactions.physicalattacks.PhysicalAttack;
import ire.entities.Entity;
import ire.tools.Tools;

public class Shield extends PhysicalDefense {

    public Shield() {
        super("Shield", "Reduces damage of physical attacks", 1.4f, 1);
    }

    // Add clause to handle spell-based attacks
    @Override
    public void execute(Entity attacker, Entity defender) {

        Action attack = attacker.getCurrentAction();
        int curDef = defender.getCurDef();
        int curMag = defender.getCurMag();

        // Ask Myles about this schmaneuver
        //String name = attack.getName();
        //if (defender.isAlive() && (name.equals("Stab") || name.equals("Lunge"))) {

        if (attack instanceof PhysicalAttack) {

            ((PhysicalAttack) attack).incrementDamage(
                Tools.round((curDef * this.physBoost * physCoefficientDef) + (curMag * physCoefficientMag)));
        } else {

            super.execute(attacker, defender);
        }
    }
}
