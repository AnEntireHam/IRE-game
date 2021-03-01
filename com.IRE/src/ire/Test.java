package ire;

import ire.combat.actions.attackactions.physicalattacks.Lunge;
import ire.combat.actions.attackactions.physicalattacks.Stab;
import ire.combat.actions.attackactions.spellattacks.Celestial;
import ire.combat.actions.attackactions.spellattacks.Lunar;
import ire.combat.actions.attackactions.spellattacks.Solar;
import ire.combat.actions.defenseactions.physicaldefenses.Counter;
import ire.combat.actions.defenseactions.physicaldefenses.Shield;
import ire.combat.actions.defenseactions.spelldefenses.Screen;
import ire.combat.statuseffects.stateffects.AttackDown;
import ire.entities.Player;
import ire.entities.enemies.Skeleton;

public class Test {

    public static void main(String[] args) {

        Player p2 = new Player(1, 70, 3, 3, 8, 6,
                "Mage Test 1", "humanDeath",
                1, 0, 0, 2, 1);

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

        AttackDown ad = new AttackDown(1);

        ad.apply(p2, e1, 10);

        System.out.println("okay");

    }
}
