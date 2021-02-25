package IRE.Combat.Actions.AttackActions.PhysicalAttacks;

import IRE.Audio.AudioStream;
import IRE.Entities.Entity;
import IRE.Tools.Tools;

public class Lunge extends PhysicalAttack {

    // add some fields for the special timing case.
    private int counterDamage;

    public Lunge() {
        super("Lunge", "Lunge the enemy for a risky attack",
                new AudioStream("lunge1"), 2000, 1000, 2);
    }

    // Remember to include Mirror functionality

    public void execute(Entity attacker, Entity defender) {

        this.damage = (int) (attacker.getCurAtk() * coefficient);
        this.counterDamage = 0;

        System.out.println(attacker.getName() + " lunged at " + defender.getName());
        Tools.sleep(1250);

        defender.getCurrentAction().execute(attacker, defender);

        if (defender.isAlive() && counterDamage == 0) {
            Tools.sleep(250);
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            this.SOUND.play();
            Tools.sleep(1000);
            defender.bEffects.takeDamage(damage, true);

        } else if (counterDamage == 0) {
            this.SOUND.play();
            defender.bEffects.takeDamage(damage, true);
        }
    }

    public int getCounterDamage() {
        return counterDamage;
    }

    public void setCounterDamage(int counterDamage) {
        this.counterDamage = counterDamage;
    }
}
