package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;
import ire.combat.statuseffects.stateffects.AttackDown;
import ire.entities.Entity;
import ire.tools.Tools;

public class Lightning extends DebuffSpell {

    public Lightning(int spellLevel) {
        super("Lightning", "Deals moderate damage, and may lower speed",
                new AudioStream("lightning"), 2000, 778, 0.85f,
                new String[]{"Bolt", "Ball", "Storm"}, 3, spellLevel, new AttackDown(spellLevel));
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        damage = Tools.round(attacker.getCurMag() * coefficient);
        damage = Tools.round(damage * ((spellLevel - 1) * 0.5 + 1));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(attacker.getName() + " summons enraged and thunderous clouds above " + defender.getName() + ".");
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
