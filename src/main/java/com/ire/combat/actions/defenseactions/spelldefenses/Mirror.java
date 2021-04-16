package com.ire.combat.actions.defenseactions.spelldefenses;

import com.ire.audio.AudioStream;
import com.ire.combat.actions.Action;
import com.ire.combat.actions.attackactions.spellattacks.SpellAttack;
import com.ire.entities.Entity;

public class Mirror extends SpellDefense {

    private static final AudioStream reflect = new AudioStream("reflect");

    public Mirror() {
        super("Mirror", "Reflects magical attacks, but reduces physical defense",
                1, 1.3333f, 1, 0.75f);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        Action attack = attacker.getCurAction();

        if (attack instanceof SpellAttack) {

            ((SpellAttack) attack).incrementDamage(
                    -Math.round(((SpellAttack) attack).getDamage() * (1 - spellResist)));

        } else {

            super.execute(attacker, defender);
        }
    }

    public void reflect(Entity attacker, Entity defender) {

        SpellAttack attack = (SpellAttack) attacker.getCurAction();

        defender.takeDamage(Math.round(attack.getDamage() * 0.3333f), true);
        //  PrintControl.sleep(1000);
        System.out.println("... and reflected " + attacker.getPossessiveName() + " spell!");
        reflect.play();

    }

}
