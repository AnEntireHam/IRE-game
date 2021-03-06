package ire;

import ire.combat.actions.attackactions.physicalattacks.Lunge;
import ire.combat.actions.attackactions.physicalattacks.Stab;
import ire.combat.actions.attackactions.spellattacks.Celestial;
import ire.combat.actions.attackactions.spellattacks.Lunar;
import ire.combat.actions.attackactions.spellattacks.Solar;
import ire.combat.actions.defenseactions.physicaldefenses.Counter;
import ire.combat.actions.defenseactions.physicaldefenses.Shield;
import ire.combat.actions.defenseactions.spelldefenses.Screen;
import ire.combat.statuseffects.StatusEffect;
import ire.combat.statuseffects.stateffects.AttackDown;
import ire.combat.statuseffects.stateffects.AttackUp;
import ire.entities.Player;
import ire.entities.enemies.Skeleton;
import ire.tools.Tools;

public class Test {

    public static void main(String[] args) {

        Player p2 = new Player(1, 70, 3, 3, 10, 6,
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
        AttackUp au = new AttackUp(1);

        // HP 70, ATK 3, DEF 3, MAG 8, SPD 6
        // HP 10, ATK 7, DEF 1, MAG 2, SPD 1
        p2.setDebug(true);
        au.apply(p2, e1);
        //ad.apply(p2, e1);
        /*ad.apply(p2, e1);
        ad.apply(p2, e1);
        ad.apply(p2, e1);
        ad.apply(p2, e1);*/
        for (StatusEffect se: e1.getStatusEffects()) {
            System.out.println(se.toString());
        }



        System.out.println("Test Finished.");
    }
}
