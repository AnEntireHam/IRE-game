package ire;

import ire.combat.Battle;
import ire.combat.actions.attackactions.spellattacks.Celestial;
import ire.combat.actions.attackactions.spellattacks.Lunar;
import ire.combat.actions.attackactions.spellattacks.Solar;
import ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells.Heal;
import ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells.Life;
import ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells.Mind;
import ire.combat.actions.attackactions.spellattacks.debuffspells.statspells.*;
import ire.combat.actions.defenseactions.spelldefenses.Screen;
import ire.combat.statuseffects.generativeeffect.Bleed;
import ire.combat.statuseffects.generativeeffect.ManaBleed;
import ire.combat.statuseffects.generativeeffect.ManaRegeneration;
import ire.combat.statuseffects.generativeeffect.Regeneration;
import ire.entities.Player;
import ire.entities.enemies.Skeleton;
import ire.entities.enemies.TrainingDummy;
import ire.tools.Tools;

public class Main {

    public static void main(String[] args) {

        /*BasicTextAdventure game = new BasicTextAdventure();
        game.setup();
        game.run();*/

        Tools.clear();

        /*SaveData s = new SaveData();
        s.Create();
        s.Write("Placeholder save data text!");

        s.Read("startArt.txt");

        System.out.println("\nPress ENTER to begin...");

        Tools.emptyPrompt();*/


        //Uncanny mockery, shattering grip, impotent prayer, bolster

        Player p1 = new Player(1, 12, 8, 4, 2, 5,
                "Warrior", "humanDeath",
                1, 2, 1, 0, 0);

        Player p2 = new Player(1, 70, 3, 3, 8, 6,
                "Mage", "humanDeath",
                1, 0, 0, 2, 1);

        p2.setDebug(true);

        Skeleton s1 = new Skeleton(2);
        Skeleton s2 = new Skeleton(2);
        Skeleton s3 = new Skeleton(2);
        Skeleton s4 = new Skeleton(2);
        Skeleton s5 = new Skeleton(2);

        TrainingDummy t1 = new TrainingDummy(1);

        Celestial celestial = new Celestial(1);
        Lunar lunar = new Lunar(1);
        Solar solar = new Solar(1);
        Fire fire = new Fire(1);
        Ice ice = new Ice(1);
        Lightning lightning = new Lightning(1);
        Rock rock = new Rock(1);
        Soul soul = new Soul(1);
        Life life = new Life(1);
        Heal heal = new Heal(1);
        Mind mind = new Mind(1);
        Screen screen = new Screen();

        //p2.addSpell(celestial);
        p2.addSpell(lunar);
        /*p2.addSpell(solar);
        p2.addSpell(fire);
        p2.addSpell(ice);
        p2.addSpell(lightning);
        p2.addSpell(rock);
        p2.addSpell(soul);*/
        p2.addSpell(life);
        p2.addSpell(heal);
        p2.addSpell(mind);

        p2.addWard(screen);

        Bleed bleed = new Bleed(1);
        Regeneration regen = new Regeneration(1);
        ManaBleed mBleed = new ManaBleed(1);
        ManaRegeneration mRegen = new ManaRegeneration(1);

        /*regen.setStrength(4);
        regen.apply(p1, s1);

        bleed.setStrength(-5);
        bleed.apply(p1, s1);*/

        mRegen.setStrength(4);
        mRegen.apply(s1, p2);

        mBleed.setStrength(-6);
        mBleed.apply(s1, p2);


        Battle b = new Battle(p2);
        b.addEnemy(s1);

        if (b.runBattle(0)) {
            System.out.println("Players won!!");
        } else {
            System.out.println("Players lost.");
        }

        System.out.println("\nTiny demo finished. Thanks for playing.");
    }

}
