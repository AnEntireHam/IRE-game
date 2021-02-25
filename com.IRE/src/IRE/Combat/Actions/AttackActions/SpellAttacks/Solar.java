package IRE.Combat.Actions.AttackActions.SpellAttacks;

import IRE.Audio.AudioStream;
import IRE.Entities.Entity;
import IRE.Tools.Tools;

public class Solar extends SpellAttack {

    //private final AudioStream leech = new AudioStream("leech");

    // Text appears a bit quickly.
    public Solar(int spellLevel) {
        super("Solar", "Deals modest damage, but heals the user.",
                new AudioStream("solar"), 2000, 1200, 0.75f,
                new String[]{"Beam", "Blast", "Burst"}, 3, spellLevel);
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        damage = (int) (attacker.getCurMag() * coefficient);

        damage = Tools.round(damage * ((spellLevel - 1) * 0.5 + 1));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(attacker.getName() + " locks eyes with " + defender.getName() + " and... praises the sun?");
        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.incrementMan(-baseManaCost);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.bEffects.takeDamage(damage, true);
        attacker.bEffects.heal(damage, true);
    }
}
