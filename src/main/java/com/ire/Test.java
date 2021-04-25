package com.ire;

import com.ire.combat.Battle;
import com.ire.combat.actions.attackactions.physicalattacks.Lunge;
import com.ire.combat.actions.attackactions.physicalattacks.Stab;
import com.ire.combat.actions.attackactions.spellattacks.Celestial;
import com.ire.combat.actions.attackactions.spellattacks.Lunar;
import com.ire.combat.actions.attackactions.spellattacks.Solar;
import com.ire.combat.actions.defenseactions.physicaldefenses.Counter;
import com.ire.combat.actions.defenseactions.physicaldefenses.Shield;
import com.ire.combat.actions.defenseactions.spelldefenses.Screen;
import com.ire.combat.statuseffects.generativeeffect.Bleed;
import com.ire.combat.statuseffects.generativeeffect.Regeneration;
import com.ire.combat.statuseffects.stateffects.*;
import com.ire.entities.Entity;
import com.ire.entities.enemies.Skeleton;
import com.ire.entities.players.Mage;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        
        Mage p1 = new Mage(1);

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

        Bleed bleed1 = new Bleed(1);
        bleed1.setStrength(-5);

        Bleed bleed2 = new Bleed(1);
        bleed2.setStrength(-2);

        Regeneration regen1 = new Regeneration(1);
        regen1.setStrength(8);

        Regeneration regen2 = new Regeneration(1);
        regen2.setStrength(4);

        // HP 70, ATK 3, DEF 3, MAG 8, SPD 6
        // HP 10, ATK 7, DEF 1, MAG 2, SPD 1
        //p2.setDebug(true);

        bleed1.apply(p1, e1);
        bleed2.apply(p1, e1);
        regen1.apply(e1, e1);
        regen2.apply(e1, e1);


        ArrayList<Entity> team1 = new ArrayList<>();
        ArrayList<Entity> team2 = new ArrayList<>();

        team1.add(p1);
        team2.add(e1);

        Battle battle = new Battle(team1, team2);

        battle.runBattle(1);

        /*for (StatusEffect se: e1.getStatusEffects()) {
            System.out.println(se);
        }*/
        /*for (int i = 0; i < 10; i++) {
            System.out.println(e1.getStat(i));
        }*/


        System.out.println("Test Finished.");
    }
}
