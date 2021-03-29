package com.ire;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import com.ire.combat.Battle;
import com.ire.combat.actions.attackactions.spellattacks.Celestial;
import com.ire.combat.actions.attackactions.spellattacks.Lunar;
import com.ire.combat.actions.attackactions.spellattacks.Solar;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells.Life;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.generativespells.Mind;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.statspells.*;
import com.ire.combat.actions.defenseactions.spelldefenses.Mirror;
import com.ire.combat.actions.defenseactions.spelldefenses.Screen;
import com.ire.combat.statuseffects.generativeeffect.Bleed;
import com.ire.combat.statuseffects.generativeeffect.ManaBleed;
import com.ire.combat.statuseffects.generativeeffect.ManaRegeneration;
import com.ire.combat.statuseffects.generativeeffect.Regeneration;
import com.ire.combat.statuseffects.stateffects.AttackDown;
import com.ire.combat.statuseffects.stateffects.AttackUp;
import com.ire.entities.Entity;
import com.ire.entities.Player;
import com.ire.entities.enemies.Skeleton;
import com.ire.entities.enemies.TrainingDummy;
import com.ire.tools.SaveData;
import com.ire.tools.Tools;

import java.util.ArrayList;

import static com.diogonunes.jcolor.Attribute.*;

public class Main {

    // TODO: Clean up Main, add small arena, export this out to test.
    public static void main(String[] args) {

        Attribute[] myFormat = new Attribute[]{BLACK_TEXT(), MAGENTA_BACK(), DIM()};
        AnsiFormat bigDamage = new AnsiFormat(RED_TEXT(), CYAN_BACK());
        AnsiFormat smallDamage = new AnsiFormat(BRIGHT_RED_TEXT());

        /*BasicTextAdventure game = new BasicTextAdventure();
        game.setup();
        game.run();*/

        String reset = "\u001B[0m";
        String purple = "\u001B[35m";
        String red = "\u001B[31m";

        System.out.println("test");
        Tools.clear();

        SaveData s = new SaveData();
        /*s.Create();
        s.Write("Placeholder save data text!");*/

        /*System.out.print(red);
        s.Read("startArt.txt");
        System.out.println("^^ ANSI color code ^^" + reset);

        System.out.println();

        System.out.println(colorize("Bright green text, black bg", BRIGHT_GREEN_TEXT(), BLACK_BACK()));
        System.out.println(colorize("Bright green text, black bg, bold", BRIGHT_GREEN_TEXT(), BLACK_BACK(), BOLD()));
        System.out.println(colorize("Bright green text, black bg, italic", BRIGHT_GREEN_TEXT(), BLACK_BACK(), ITALIC()));
        System.out.println(colorize("Bright green text, black bg, underline", BRIGHT_GREEN_TEXT(), BLACK_BACK(), UNDERLINE()));
        System.out.println(colorize("Bright green text, black bg, strike through", BRIGHT_GREEN_TEXT(), BLACK_BACK(), STRIKETHROUGH()));
        System.out.println(colorize("Black text, bright green bg", BRIGHT_GREEN_TEXT(), BLACK_BACK(), REVERSE()));

        System.out.println(colorize("Brownish text", BLACK_BACK(), TEXT_COLOR(100)));
        System.out.println(colorize("Purplish text", TEXT_COLOR(125, 16, 204)));
        System.out.println();

        Tools.sleep(300);*/

        //System.out.println("Press ENTER to begin...");

        //Tools.emptyPrompt();
        Tools.clear();

        //Uncanny mockery, shattering grip, impotent prayer, bolster

        Player p1 = new Player(1, 14, 8, 4, 2, 5,
                "Warrior", "humanDeath",
                1, 2, 1, 0, 0);

        Player p2 = new Player(1, 8, 3, 3, 6, 6,
                "Mage", "humanDeath",
                1, 0, 0, 2, 1);

        Skeleton s1 = new Skeleton(1);
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
        Mind mind = new Mind(1);
        Screen screen = new Screen();
        Mirror mirror = new Mirror();

        //p2.addSpell(celestial);
        p2.addSpell(lunar);
        p2.addSpell(solar);
        p2.addSpell(fire);
        /*p2.addSpell(ice);
        p2.addSpell(lightning);
        p2.addSpell(rock);
        p2.addSpell(soul);*/
        p2.addSpell(life);
        p2.addSpell(mind);

        p2.addWard(screen);

        s1.addWard(mirror);
        s2.addWard(mirror);

        Bleed bleed = new Bleed(1);
        Regeneration regen = new Regeneration(1);
        ManaBleed mBleed = new ManaBleed(1);
        ManaRegeneration mRegen = new ManaRegeneration(1);
        AttackUp au = new AttackUp(1);
        AttackDown ad = new AttackDown(1);

        /*regen.setStrength(4);
        regen.apply(p1, s1);

        bleed.setStrength(-1);
        bleed.apply(s1, p1);*/

        /*mRegen.setStrength(1);
        mRegen.apply(p1, s1);

        mBleed.setStrength(-6);
        mBleed.apply(s1, p2);

        au.apply(p2, s1);
        ad.apply(p2, s1);*/
/*        s1.setHlh(-s1.getCurHlh() + 5);
        s2.setHlh(s2.getCurHlh() + 5);*/
        p2.setMan(8);

        ArrayList<Entity> players = new ArrayList<>();
        ArrayList<Entity> enemies = new ArrayList<>();

        players.add(p1);
        players.add(p2);
        enemies.add(s1);
        enemies.add(s2);

        Battle b = new Battle(players, enemies);

        if (b.runBattle(1)) {
            System.out.println(purple + "Players won!!");
        } else {
            System.out.println(red + "Players lost.");
        }

        System.out.println(reset + "\nTiny demo finished. Thanks for playing.");
    }

}
