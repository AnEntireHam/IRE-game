package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;
import ire.combat.statuseffects.stateffects.MagicDown;
import ire.entities.Entity;
import ire.tools.Tools;

public class Soul extends DebuffSpell {

    public Soul(int spellLevel) {
        super("Soul", "Deals moderate damage, and may lower magic",
                new AudioStream("soul"), 2000, 1581, 0.85f,
                new String[]{"Suck", "Sap", "Drain"}, 3, spellLevel, new MagicDown(spellLevel));
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        damage = Tools.round(attacker.getCurMag() * coefficient);
        damage = Tools.round(damage * ((spellLevel - 1) * 0.5 + 1));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(attacker.getName() + " unleashes a litany of binding hexes upon " + defender.getName() + ".");
        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.incrementMan(-baseManaCost);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.bEffects.takeDamage(damage, true);
        this.debuff.apply(attacker, defender);
    }
}
