package ire.combat.actions.defenseactions;

import ire.combat.actions.Action;
import ire.combat.actions.attackactions.physicalattacks.PhysicalAttack;
import ire.combat.actions.attackactions.spellattacks.SpellAttack;
import ire.entities.Entity;
import ire.tools.Tools;

public abstract class DefenseAction extends Action {

    // Consider moving to Entity?
    protected final float physCoefficientDef  = -0.5f;
    protected final float physCoefficientMag  =  0.0f;
    protected final float spellCoefficientDef = -0.25f;
    protected final float spellCoefficientMag = -0.5f;

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
                    Tools.round((curDef * physCoefficientDef) + (curMag * physCoefficientMag)));

        } else if (action instanceof SpellAttack) {

            ((SpellAttack) action).incrementDamage(
                    Tools.round((curDef * spellCoefficientDef) + (curMag * spellCoefficientMag)));
        }
    }
}
