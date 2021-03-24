package com.ire.combat.actions.defenseactions.spelldefenses;

import com.ire.audio.AudioStream;
import com.ire.combat.actions.Action;
import com.ire.combat.actions.attackactions.spellattacks.SpellAttack;
import com.ire.entities.Entity;

public class Mirror extends SpellDefense {

    private static final AudioStream reflect = new AudioStream("reflect");

    public Mirror() {
        super("Mirror", "Reflects magical attacks, but reduces physical defense",
                1, 1, 1, -0.25f);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        Action attack = attacker.getCurrentAction();

        if (attack instanceof SpellAttack) {

            ((SpellAttack) attack).incrementDamage(
                    Math.round(((SpellAttack) attack).getDamage() * spellResist));

        } else {

            super.execute(attacker, defender);
        }
    }

    public void reflect(Entity attacker, Entity defender) {

        SpellAttack attack = (SpellAttack) attacker.getCurrentAction();

        defender.takeDamage(Math.round(attack.getDamage() * 0.3333f), true);
        //  Tools.sleep(1000);
        System.out.println("... and reflected " + attacker.getName() + "'s spell!");
        reflect.play();

    }

}
