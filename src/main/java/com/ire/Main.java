package com.ire;

import com.ire.audio.AudioClip;
import com.ire.bot.ClientConnection;
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
import com.ire.entities.players.Mage;
import com.ire.entities.players.Player;
import com.ire.entities.enemies.Caster;
import com.ire.entities.enemies.Skeleton;
import com.ire.entities.enemies.TrainingDummy;
import com.ire.entities.players.Warrior;
import com.ire.tools.SaveData;
import com.ire.tools.Tools;
import com.ire.world.Arena;

import java.util.ArrayList;

public class Main {

    // TODO: Clean up Main, add small arena, export this out to test.
    public static void main(String[] args) {

        ClientConnection connect = null;
        if ((args.length > 2) && args[0].equals("useBot")) {
            connect = new ClientConnection(args[1], Integer.parseInt(args[2]));
            Tools.setBotClient(true);

        } else if (args.length > 0) {
            System.err.println("Passed arguments are insufficient to create client.");
            for (int i = 0; i < args.length; i++) {
                System.out.println("Arg " + i + ": " + args[i]);
            }
            Tools.sleep(3000);
        }

        Tools.clear();

        AudioClip start = new AudioClip("woosh");
        start.play();
        Tools.sleep(500);
        SaveData s = new SaveData();
        s.Read("startArt");
        Tools.sleep(300);

        System.out.println("Press ENTER to begin...");
        Tools.emptyPrompt();
        Tools.clear();
        start.end();

        Arena a = new Arena();
        a.startArenaLoop();

        System.exit(0);

        Player p1 = new Mage();
        //p1.addXp(27);

        Player p2 = new Warrior();
        //p2.addXp(27);

        Skeleton s1 = new Skeleton(1);
        Skeleton s2 = new Skeleton(2);
        Skeleton s3 = new Skeleton(2);
        Skeleton s4 = new Skeleton(2);
        Skeleton s5 = new Skeleton(2);

        Caster c1 = new Caster(1);
        Caster c2 = new Caster(2);

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

        c1.removeAttack(0);
        c1.removeDefense(0);
        c1.addWard(mirror);
        c1.addWard(screen);
        c1.removeDefense(0);
        c1.addSpell(celestial);
        c1.addSpell(lunar);
        //c1.addSpell(life);

        c2.removeDefense(0);
        c2.addWard(mirror);
        c2.addWard(screen);
        c2.removeDefense(0);
        c2.addSpell(celestial);
        c2.addSpell(lunar);
        //c2.addSpell(life);

        p2.addSpell(celestial);
        p2.addSpell(lunar);
        p2.addSpell(solar);
        p2.addSpell(fire);
        /*p2.addSpell(ice);
        p2.addSpell(lightning);
        p2.addSpell(rock);*/
        p2.addSpell(soul);
        /*p2.addSpell(life);
        p2.addSpell(mind);*/

        p2.addWard(screen);
        p2.addWard(mirror);

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
        //p2.setMan(8);
        //ad.apply(p2, p2);
        //p2.addXp(7);

        ArrayList<Entity> players = new ArrayList<>();
        ArrayList<Entity> enemies = new ArrayList<>();

        players.add(p1);
        players.add(p2);
        enemies.add(s1);
        enemies.add(s2);
        enemies.add(c1);
        //enemies.add(c2);

        Battle b = new Battle(players, enemies);

        if (b.runBattle(1)) {
            System.out.println("Players won!!");
        } else {
            System.out.println("Players lost.");
        }

        System.out.println("\nTiny demo finished. Thanks for playing.");

        if ((args.length > 2) && args[0].equals("useBot")) {
            assert connect != null;
            connect.end();
        }
    }

}
