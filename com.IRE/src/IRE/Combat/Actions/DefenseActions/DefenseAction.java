package IRE.Combat.Actions.DefenseActions;

import IRE.Combat.Actions.Action;
import IRE.Combat.Actions.AttackActions.PhysicalAttacks.PhysicalAttack;
import IRE.Combat.Actions.AttackActions.SpellAttacks.SpellAttack;
import IRE.Entities.Entity;
import IRE.Tools.Tools;

public abstract class DefenseAction extends Action {

    // Consider moving to Entity?
    protected final float PHYS_COEFF_DEF  = -0.5f;
    protected final float PHYS_COEFF_MAG  =  0.0f;
    protected final float SPELL_COEFF_DEF = -0.25f;
    protected final float SPELL_COEFF_MAG = -0.5f;

    public DefenseAction(String NAME, String DESCRIPTION) {
        super(NAME, DESCRIPTION);
    }


    @Override
    public void execute(Entity attacker, Entity defender) {

        Action action = attacker.getCurrentAction();
        int curDef = defender.getCurDef();
        int curMag = defender.getCurMag();

        if (action instanceof PhysicalAttack) {

            ((PhysicalAttack) action).incrementDamage(
                    Tools.round((curDef * PHYS_COEFF_DEF) + (curMag * PHYS_COEFF_MAG)));

        } else if (action instanceof SpellAttack) {

            ((SpellAttack) action).incrementDamage(
                    Tools.round((curDef * SPELL_COEFF_DEF) + (curMag * SPELL_COEFF_MAG)));
        }
    }
}
