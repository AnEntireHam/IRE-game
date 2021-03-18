package com.ire.combat.actions.attackactions.spellattacks;

import com.ire.audio.AudioStream;
import com.ire.tools.Tools;
import com.ire.entities.Entity;

import java.util.Formatter;

public class Lunar extends SpellAttack {

    private float baseHealthCost = 0.8f;

    public Lunar(int spellLevel) {
        super("Lunar", "Deals substantial damage, but also hurts the caster",
                new AudioStream("lunar"), 2000, 500, 2.5f,
                new String[]{"Beam", "Blast", "Burst"}, 3, spellLevel,
                "%s inflicts %d damage on themselves to charge a spell...");
    }

    @Override
    public void execute(Entity attacker, Entity defender) {


        damage = Math.round(attacker.getCurMag() * coefficient);
        damage = Math.round(damage * ((spellLevel - 1) * 0.5f + 1));

        int healthCost = Math.round((baseHealthCost * attacker.getCurMag()));

        defender.getCurrentAction().execute(attacker, defender);

        Formatter parser = new Formatter();
        System.out.println(parser.format(flavorText, attacker.getName(), healthCost));

        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.incrementMan(-baseManaCost);
        attacker.takeDamage(healthCost, false);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.takeDamage(damage, true);

    }

    public float getBaseHealthCost() {
        return baseHealthCost;
    }

    public void setBaseHealthCost(float baseHealthCost) {
        this.baseHealthCost = baseHealthCost;
    }

}
