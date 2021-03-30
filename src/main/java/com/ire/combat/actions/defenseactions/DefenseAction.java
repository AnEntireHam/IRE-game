package com.ire.combat.actions.defenseactions;

import com.ire.combat.actions.Action;
import com.ire.combat.actions.attackactions.physicalattacks.PhysicalAttack;
import com.ire.combat.actions.attackactions.spellattacks.SpellAttack;
import com.ire.entities.Entity;

public abstract class DefenseAction extends Action {

    // Consider moving to Entity?
    protected final float physCoefficientDef  = 0.5f;
    protected final float physCoefficientMag  = 0.0f;
    protected final float spellCoefficientDef = 0.25f;
    protected final float spellCoefficientMag = 0.5f;
    protected float physBoost;
    protected float physResist;
    protected float spellBoost;
    protected float spellResist;

    public DefenseAction(String NAME, String DESCRIPTION, float physBoost, float physResist,
                         float spellBoost, float spellResist) {
        super(NAME, DESCRIPTION);

        this.physBoost = physBoost;
        this.physResist = physResist;
        this.spellBoost = spellBoost;
        this.spellResist = spellResist;
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        Action action = attacker.getCurAction();
        int curDef = defender.getCurDef();
        int curMag = defender.getCurMag();

        if (action instanceof PhysicalAttack) {

            ((PhysicalAttack) action).incrementDamage(
                    -Math.round(((PhysicalAttack) action).getDamage() * (1 - physResist)));

            ((PhysicalAttack) action).incrementDamage(
                    -Math.round((curDef * physCoefficientDef * physBoost) +
                            (curMag * physCoefficientMag * spellBoost)));
            return;

        }

        if (action instanceof SpellAttack) {

            ((SpellAttack) action).incrementDamage(
                    -Math.round(((SpellAttack) action).getDamage() * (1 - spellResist)));

            ((SpellAttack) action).incrementDamage(
                    -Math.round((curDef * spellCoefficientDef * physBoost) +
                            (curMag * spellCoefficientMag * spellBoost)));
        }
    }

    public float getPhysBoost() {
        return physBoost;
    }

    public void setPhysBoost(float physBoost) {
        this.physBoost = physBoost;
    }

    public float getPhysResist() {
        return physResist;
    }

    public void setPhysResist(float physResist) {
        this.physResist = physResist;
    }

    public float getSpellBoost() {
        return spellBoost;
    }

    public void setSpellBoost(float spellBoost) {
        this.spellBoost = spellBoost;
    }

    public float getSpellResist() {
        return spellResist;
    }

    public void setSpellResist(float spellResist) {
        this.spellResist = spellResist;
    }

    @Override
    public String toString() {
        return "DefenseAction{" +
                "physBoost=" + physBoost +
                ", physResist=" + physResist +
                ", spellBoost=" + spellBoost +
                ", spellResist=" + spellResist +
                "} " + super.toString();
    }
}
