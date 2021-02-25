package ire;

import ire.combat.actions.attackactions.physicalattacks.Lunge;
import ire.combat.actions.attackactions.physicalattacks.Stab;
import ire.combat.actions.attackactions.spellattacks.Celestial;
import ire.combat.actions.attackactions.spellattacks.Lunar;
import ire.combat.actions.attackactions.spellattacks.Solar;
import ire.combat.actions.defenseactions.physicaldefenses.Counter;
import ire.combat.actions.defenseactions.physicaldefenses.Shield;
import ire.combat.actions.defenseactions.spelldefenses.Screen;
import ire.entities.Player;
import ire.entities.enemies.Skeleton;

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
