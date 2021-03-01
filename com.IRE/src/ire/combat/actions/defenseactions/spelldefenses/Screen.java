package ire.combat.actions.defenseactions.spelldefenses;

import ire.combat.actions.Action;
import ire.combat.actions.attackactions.spellattacks.SpellAttack;
import ire.entities.Entity;
import ire.tools.Tools;

public class Screen extends SpellDefense {

    public Screen() {
        super("Screen", "Reduces damage of magical attacks", 1, -0.3333f);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        Action attack = attacker.getCurrentAction();
        int curDef = defender.getCurDef();
        int curMag = defender.getCurMag();

        if (attack instanceof SpellAttack) {
            ((SpellAttack) attack).incrementDamage(
                    Tools.round(((SpellAttack) attack).getDamage() * spellResist));

            ((SpellAttack) attack).incrementDamage(
                    Tools.round((curDef * spellCoefficientDef) + (curMag * spellCoefficientMag)));
        } else {

            super.execute(attacker, defender);
        }
    }
}
