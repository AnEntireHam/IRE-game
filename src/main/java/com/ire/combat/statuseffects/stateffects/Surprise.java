package com.ire.combat.statuseffects.stateffects;

import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.entities.Entity;

public class Surprise extends StatEffect {

    // TODO: Fix "x had this Surprise! increased text". Probably add Formatter or @Override or boolean doDisplay.
    public Surprise() {
        super("Surprise!", "SPD", "Increases the maximum health of the afflicted target.",
                true, true, 1, 1,
                new RemoveCondition[]{RemoveCondition.EXPIRATION, RemoveCondition.DEATH},
                1, 1, 0, 2.0f, 0.00f);
    }

    @Override
    protected void displayResult(String defender, String statName, boolean debuff, boolean success) {

        /*if (success) {
            System.out.println(defender + " surprised the enemy!");
        } else {
            System.out.println(defender + " couldn't surprise the enemy.");
        }*/
        //  Probably just print "xTeam could(n't) surprise the enemy!" in battle.
    }

    @Override
    public void remove(Entity target) {

        target.removeStatusEffect(this);
        /*System.out.println(target.getName() + "'s status effect " + name.toLowerCase() + " expired.");
        Tools.sleep(1250);*/    // It may be worthwhile adding some logic to only print this once per party.
    }
}
