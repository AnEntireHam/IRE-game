package com.ire.combat.actions.attackactions.spellattacks;

import com.ire.audio.AudioStream;
import com.ire.entities.Entity;
import com.ire.tools.Tools;

import java.util.Formatter;

public class Lunar extends SpellAttack {

    private float baseHealthCost = 0.8f;

    public Lunar(int spellLevel) {
        super("Lunar", "Deals substantial damage, but also hurts the caster",
                new AudioStream("lunar"), 2000, 500, 2.5f,
                new String[]{"Beam", "Blast", "Burst"}, 5, spellLevel,
                "%s inflicts %d damage on themselves to charge a spell...");
    }

    // Damage is taken in here, contrary to in execute(). It shall suffice, I suppose.
    @Override
    protected void narrateEvents(Entity attacker, Entity defender) {

        Formatter parser = new Formatter();
        int healthCost = Math.round((baseHealthCost * attacker.getCurMag()));

        System.out.println(parser.format(flavorText, attacker.getName(), healthCost));

        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.takeDamage(healthCost, false);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurAction().getName());
            Tools.sleep(DURATION - DELAY);
        }
    }

    public float getBaseHealthCost() {
        return baseHealthCost;
    }

    public void setBaseHealthCost(float baseHealthCost) {
        this.baseHealthCost = baseHealthCost;
    }

}
