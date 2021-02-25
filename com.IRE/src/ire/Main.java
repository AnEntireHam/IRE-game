package ire;


import ire.combat.Battle;
import ire.combat.actions.attackactions.spellattacks.Celestial;
import ire.combat.actions.attackactions.spellattacks.Lunar;
import ire.combat.actions.attackactions.spellattacks.Solar;
import ire.combat.actions.defenseactions.spelldefenses.Screen;
import ire.entities.Player;
import ire.entities.enemies.Skeleton;
import ire.entities.enemies.TrainingDummy;
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
        s.Write("Placeholder save data text!");

        s.Read("startArt.txt");

        System.out.println("\nPress ENTER to begin...");

        Tools.emptyPrompt();




        //Uncanny mockery, shattering grip, impotent prayer, bolster


        Player p1 = new Player(1, 12, 8, 4, 2, 5,
                "Warrior Test 1", "humanDeath",
                1, 2, 1, 0, 0);

        Player p2 = new Player(1, 70, 3, 3, 8, 6,
                "Mage Test 1", "humanDeath",
                1, 0, 0, 2, 1);

        Skeleton e1 = new Skeleton(2);
        Skeleton e2 = new Skeleton(2);
        Skeleton e3 = new Skeleton(2);
        Skeleton e4 = new Skeleton(2);
        Skeleton e5 = new Skeleton(2);

        TrainingDummy e6 = new TrainingDummy(1);

        Celestial celestial = new Celestial(1);
        Lunar lunar = new Lunar(1);
        Solar solar = new Solar(1);
        Screen screen = new Screen();

        p2.addSpell(celestial);
        p2.addSpell(lunar);
        p2.addSpell(solar);
        p2.addWard(screen);

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
            System.out.println("Players won!!");
        } else {
            System.out.println("Players lost.");
        }

        System.out.println("\nThanks for playing this tiny demo.");
        Tools.emptyPrompt();

    }

}
