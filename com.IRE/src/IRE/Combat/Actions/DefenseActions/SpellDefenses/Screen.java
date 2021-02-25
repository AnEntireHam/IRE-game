package IRE.Combat.Actions.DefenseActions.SpellDefenses;

import IRE.Combat.Actions.Action;
import IRE.Combat.Actions.AttackActions.SpellAttacks.SpellAttack;
import IRE.Entities.Entity;
import IRE.Tools.Tools;

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
                    Tools.round((curDef * SPELL_COEFF_DEF) + (curMag * SPELL_COEFF_MAG)));
        } else {
            super.execute(attacker, defender);
        }
    }
}
