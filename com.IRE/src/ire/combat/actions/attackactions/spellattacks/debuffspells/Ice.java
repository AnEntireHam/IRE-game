package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;
import ire.combat.statuseffects.stateffects.DefenseDown;
import ire.entities.Entity;
import ire.tools.Tools;

public class Ice extends DebuffSpell {

    public Ice(int spellLevel) {
        super("Ice", "Deals moderate damage, and may lower attack",
                new AudioStream("ice"), 2000, 1152, 0.85f,
                new String[]{"Pelt", "Volley", "Storm"}, 3, spellLevel, new DefenseDown(spellLevel));
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        damage = Tools.round(attacker.getCurMag() * coefficient);
        damage = Tools.round(damage * ((spellLevel - 1) * 0.5 + 1));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(attacker.getName() + " conjures an unnatural hailstorm above " + defender.getName() + ".");
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
