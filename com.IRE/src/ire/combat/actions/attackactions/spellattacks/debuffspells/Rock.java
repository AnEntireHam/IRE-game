package ire.combat.actions.attackactions.spellattacks.debuffspells;

import ire.audio.AudioStream;;
import ire.combat.statuseffects.stateffects.HealthDown;
import ire.entities.Entity;
import ire.tools.Tools;

public class Rock extends DebuffSpell {

    public Rock(int spellLevel) {
        super("Rock", "Deals moderate damage, and may lower max health",
                new AudioStream("rock"), 2000, 778, 0.80f,
                new String[]{"Pelt", "Volley", "Smash"}, 3, spellLevel, new HealthDown(spellLevel));
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        damage = Tools.round(attacker.getCurMag() * coefficient);
        damage = Tools.round(damage * ((spellLevel - 1) * 0.5 + 1));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(attacker.getName() + " raises stones over " + defender.getName() + "'s head.");
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
