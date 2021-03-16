package com.ire;

import com.ire.combat.actions.attackactions.physicalattacks.Lunge;
import com.ire.combat.actions.attackactions.physicalattacks.Stab;
import com.ire.combat.actions.attackactions.spellattacks.Celestial;
import com.ire.combat.actions.attackactions.spellattacks.Lunar;
import com.ire.combat.actions.attackactions.spellattacks.Solar;
import com.ire.combat.actions.defenseactions.physicaldefenses.Counter;
import com.ire.combat.actions.defenseactions.physicaldefenses.Shield;
import com.ire.combat.actions.defenseactions.spelldefenses.Screen;
import com.ire.combat.statuseffects.stateffects.*;
import com.ire.entities.Player;
import com.ire.entities.enemies.Skeleton;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Test {

    public static void main(String[] args) {

        Player p2 = new Player(1, 70, 3, 3, 10, 6,
                "Mage", "humanDeath",
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

        HealthUp hu = new HealthUp(1);
        HealthDown hd = new HealthDown(1);
        AttackDown ad = new AttackDown(1);
        AttackUp au = new AttackUp(1);
        MagicDown md = new MagicDown(1);

        // HP 70, ATK 3, DEF 3, MAG 8, SPD 6
        // HP 10, ATK 7, DEF 1, MAG 2, SPD 1
        p2.setDebug(true);


        /*for (StatusEffect se: e1.getStatusEffects()) {
            System.out.println(se);
        }*/
        /*for (int i = 0; i < 10; i++) {
            System.out.println(e1.getStat(i));
        }*/


        System.out.println("Test Finished.");
    }
}
