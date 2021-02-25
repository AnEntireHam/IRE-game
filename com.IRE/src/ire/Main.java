package ire;

import ire.combat.actions.attackactions.spellattacks.Celestial;
import ire.combat.actions.attackactions.spellattacks.Lunar;
import ire.combat.actions.attackactions.spellattacks.Solar;
import ire.combat.actions.defenseactions.spelldefenses.Screen;
import ire.combat.Battle;
import ire.entities.enemies.Skeleton;
import ire.entities.enemies.TrainingDummy;
import ire.entities.Player;
import ire.tools.SaveData;
import ire.tools.Tools;

public class Main {

    public static void main(String[] args) {

        /*BasicTextAdventure game = new BasicTextAdventure();
        game.setup();
        game.run();*/


        Tools.clear();

        SaveData s = new SaveData();
        s.Create();
        s.Write("Placeholder text.");
        Tools.sleep(500);


        s.Read("startArt.txt");

        System.out.println("\n\nPress ENTER to begin.");
        Tools.emptyPrompt();
        Tools.clear();



        //Uncanny mockery, shattering grip, impotent prayer, bolster


        /*Player p1 = new Player(1, 12, 5, 4, 2, 5,
                "Warrior Test 1", "humanDeath",
                1, 2, 1, 0, 0);*/

        /*Player p2 = new Player(1, 12, 8, 4, 2, 5,
                "Warrior OldProjects.Test 2", "X", "X", "X", "X",
                1, 1, 1, 1, 1);*/

        Player p2 = new Player(1, 70, 3, 3, 8, 6,
                "Mage Test 1", "humanDeath",
                1, 0, 0, 2, 1);

        //p1.addXp(8);
        //p2.addXp(8);


        /*Player p3 = new Player(8, 8, 2, 3, 8,
                "Rogue OldProjects.Test", "Uncanny Mockery", "Shattering Grip", "Impotent Prayer", "Bolster",
                1, 1, 1, 1, 1);*/

        Skeleton e1 = new Skeleton(2);
        Skeleton e2 = new Skeleton(2);
        Skeleton e3 = new Skeleton(2);
        Skeleton e4 = new Skeleton(2);
        Skeleton e5 = new Skeleton(2);

        //p1.addWard(new Screen());
        p2.addWard(new Screen());
        p2.addSpell(new Celestial(1));
        p2.addSpell(new Lunar(1));
        p2.addSpell(new Solar(1));

        e1.addWard(new Screen());
        e2.addWard(new Screen());


        TrainingDummy e6 = new TrainingDummy(1);

        e1.setName("Skeleton 1");
        e2.setName("Skeleton 2");
        e3.setName("Skeleton 3");
        e4.setName("Skeleton 4");
        e5.setName("Skeleton 5");

        /*e1.buffDurations[5] = 3;
        e1.buffStrengths[5] = 1;
        e1.buffDurations[1] = 3;
        e1.buffStrengths[1] = 120;*/

        //e6.setDebug(true);
        //p2.setDebug(true);

        Battle b = new Battle(p2);
        b.addEnemy(e1, e2);

        //e2.setDebug(true);

        if (b.runBattle(1)) {
            System.out.println("Players won!");
        } else {
            System.out.println("Players lost.");
        }

        System.out.println("\nTiny demo finished. Thanks for testing.");


        // add sleep to debuff function for readability

    }

}
