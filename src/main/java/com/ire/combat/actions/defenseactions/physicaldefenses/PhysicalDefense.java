package com.ire.combat.actions.defenseactions.physicaldefenses;

import com.ire.combat.actions.defenseactions.DefenseAction;

public class PhysicalDefense extends DefenseAction {

    public PhysicalDefense(String NAME, String DESCRIPTION, float physBoost,
                           float physResist, float spellBoost, float spellResist) {
        super(NAME, DESCRIPTION, physBoost, physResist, spellBoost, spellResist);
    }
}
