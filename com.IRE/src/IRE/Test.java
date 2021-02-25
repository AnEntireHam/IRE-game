package IRE;

import IRE.Combat.Actions.AttackActions.PhysicalAttacks.Lunge;
import IRE.Combat.Actions.AttackActions.PhysicalAttacks.Stab;
import IRE.Combat.Actions.AttackActions.SpellAttacks.Celestial;
import IRE.Combat.Actions.AttackActions.SpellAttacks.Lunar;
import IRE.Combat.Actions.AttackActions.SpellAttacks.Solar;
import IRE.Combat.Actions.DefenseActions.PhysicalDefenses.Counter;
import IRE.Combat.Actions.DefenseActions.PhysicalDefenses.Shield;
import IRE.Combat.Actions.DefenseActions.SpellDefenses.Screen;
import IRE.Entities.Enemies.Skeleton;
import IRE.Entities.Player;

public class Test {

    public static void main(String[] args) {

        Player p1 = new Player(1, 12, 20, 4, 10, 5,
                "Warrior Test 1", "humanDeath",
                1, 2, 1, 0, 0);

        Skeleton e1 = new Skeleton(1);
        e1.setName("MISTER JANGELY BONE");

        Stab stab = new Stab();
        Shield shield = new Shield();
        Lunge lunge = new Lunge();
        Counter counter = new Counter();
        Screen screen = new Screen();
        Celestial c = new Celestial(1);
        Lunar lunar = new Lunar(1);
        Solar solar = new Solar(1);

        p1.bEffects.takeDamage(10, true);

        p1.setCurrentAction(solar);
        e1.setCurrentAction(counter);

        p1.getCurrentAction().execute(p1, e1);
        System.out.println("okay");

    }
}
