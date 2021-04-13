package com.ire.world;

import com.ire.combat.Battle;
import com.ire.combat.actions.attackactions.spellattacks.Celestial;
import com.ire.combat.actions.attackactions.spellattacks.Lunar;
import com.ire.combat.actions.attackactions.spellattacks.Solar;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.statspells.Fire;
import com.ire.combat.actions.attackactions.spellattacks.debuffspells.statspells.Ice;
import com.ire.combat.actions.defenseactions.spelldefenses.Mirror;
import com.ire.combat.actions.defenseactions.spelldefenses.Screen;
import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.entities.Entity;
import com.ire.entities.enemies.Caster;
import com.ire.entities.enemies.Skeleton;
import com.ire.entities.players.Mage;
import com.ire.entities.players.Warrior;
import com.ire.tools.Tools;

import java.util.ArrayList;

public class Arena {

    private ArrayList<Entity> team1;
    private ArrayList<Entity> team2;
    private int surprise = 0;
    private int battleEndBehavior = 1;
    private boolean giveRewards = false;


    // Constructors

    public Arena() {
        this.team1 = new ArrayList<>();
        this.team2 = new ArrayList<>();
    }

    public Arena(ArrayList<Entity> team1) {
        this.team1 = team1;
    }

    public Arena(ArrayList<Entity> team1, ArrayList<Entity> team2) {
        this.team1 = team1;
        this.team2 = team2;
    }


    // Methods

    public void startArenaLoop() {
        while (true) {
            System.out.println("Welcome to the arena.");
            ArrayList<String> options = new ArrayList<>();
            options.add("Start Battle");
            options.add("Edit Teams");
            options.add("Edit Battle Settings");
            options.add("Exit");

            int choice = Tools.menu(options);

            switch (choice) {
                case 1:
                    startBattle();
                    break;
                case 2:
                    chooseEditTeam();
                    break;
                case 3:
                    editSettings();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void startBattle() {
        System.out.println("Starting Battle...");
        if (team1 == null || team2 == null || team1.isEmpty() || team2.isEmpty()) {
            System.out.println("Teams haven't been initialized yet.");
            Tools.sleep(1000);
            Tools.clear();
            return;
        }

        // This is a shallow copy. Fix with serialization later.
        /*if (battleEndBehavior == 1) {
            ArrayList<Entity> copy1 = new ArrayList<>(team1.size() + 1);
            ArrayList<Entity> copy2 = new ArrayList<>(team2.size() + 1);
         }*/


        // Somehow, even with surprise favoring team1, team2 went first. Not sure how, but watch out for this.
        Battle b = new Battle(team1, team2, giveRewards);
        b.runBattle(surprise);
        if (battleEndBehavior == 2) {
            team1.forEach((e) -> e.fullHeal(RemoveCondition.LEVEL_UP));
            team2.forEach((e) -> e.fullHeal(RemoveCondition.LEVEL_UP));
        }
    }

    private void chooseEditTeam() {
        while (true) {
            System.out.println("Choose a team to edit");
            ArrayList<String> options = new ArrayList<>();
            options.add("Team 1");
            options.add("Team 2");
            options.add("Load Defaults");

            int choice = Tools.cancelableMenu(options);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    editTeam(team1);
                    break;
                case 2:
                    editTeam(team2);
                    break;
                case 3:
                    loadDefaults();
                    break;
            }
        }
    }

    private void loadDefaults() {
        team1 = new ArrayList<>();
        team2 = new ArrayList<>();

        Warrior warrior = new Warrior();
        Mage mage = new Mage();
        Caster c2 = new Caster(2);
        team1.add(warrior);
        team1.add(mage);
        team1.add(c2);

        warrior.addWard(new Screen());
        warrior.addWard(new Mirror());

        mage.addSpell(new Celestial(1));
        mage.addSpell(new Lunar(1));
        mage.addSpell(new Solar(1));
        mage.addSpell(new Fire(1));
        mage.addWard(new Mirror());


        Skeleton s1 = new Skeleton(1);
        Skeleton s2 = new Skeleton(2);
        Caster c1 = new Caster(2);

        team2.add(s1);
        team2.add(s2);
        team2.add(c1);

        c1.addSpell(new Celestial(1));
        c1.addSpell(new Lunar(1));
        c1.addSpell(new Ice(1));
        c1.addWard(new Mirror());

    }

    private void editTeam(ArrayList<Entity> team) {
        while (true) {
            System.out.println("Choose an entity to edit.");
            ArrayList<String> options = new ArrayList<>();
            options.add("Add Entity");
            options.add("Remove Entity");
            options.add("Copy Entity");
            for (Entity e : team) {
                options.add(e.getName() + "  Lv. " + e.getLevel());
            }

            int choice = Tools.cancelableMenu(options);

            switch (choice) {
                case 0:
                    return;
                case 1:
                    addEntity(team);
                    break;
                case 2:
                    removeEntity(team);
                    break;
                case 3:
                    copyEntity(team);
                    break;
                default:
                    editEntity(team.get(options.size() - 4));
                    break;
            }
        }
    }

    private void addEntity(ArrayList<Entity> team) {

        System.out.println("WIP.");
        Tools.sleep(1000);

        /*System.out.println("Choose entity type");
        ArrayList<String> options = new ArrayList<>();
        options.add("Player");
        options.add("Enemy");

        int choice = Tools.cancelableMenu(options);

        switch (choice) {
            case 0:
                return;
            case 1:

        }*/

    }

    private void removeEntity(ArrayList<Entity> team) {
        System.out.println("WIP.");
        Tools.sleep(1000);
    }

    private void copyEntity(ArrayList<Entity> team) {
        System.out.println("WIP.");
        Tools.sleep(1000);
    }

    private void editEntity(Entity entity) {
        System.out.println("WIP.");
        Tools.sleep(1000);
    }

    private void editSettings() {
        while (true) {
            System.out.println("Battle Settings");
            ArrayList<String> options = new ArrayList<>();
            options.add("Surprise: " + getSurpriseString());
            options.add("Post-Battle: " + getBattleEndBehaviorString());
            options.add("Give Rewards: " + giveRewards);

            int choice = Tools.cancelableMenu(options);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    editSurprise();
                    break;
                case 2:
                    editBattleEndBehavior();
                    break;
                case 3:
                    giveRewards = !giveRewards;
                    break;
            }
        }
    }

    private void editSurprise() {
        System.out.println("Surprise Settings");
        ArrayList<String> options = new ArrayList<>();
        options.add("Neither has surprise");
        options.add("Team 1 has surprise");
        options.add("Team 2 has surprise");

        int choice = Tools.cancelableMenu(options);
        if (choice == 0) {
            return;
        }
        surprise = choice - 1;
    }

    private String getSurpriseString() {

        switch (surprise) {
            case 0:
                return "Neither";
            case 1:
                return "Team 1";
            case 2:
                return "Team 2";
            default:
                throw new IllegalStateException("surprise set to illegal value.");
        }
    }

    private void editBattleEndBehavior() {
        System.out.println("Post-Battle Settings");
        ArrayList<String> options = new ArrayList<>();
        options.add("Do nothing");
        options.add("Reset teams");
        options.add("Heal teams");

        int choice = Tools.cancelableMenu(options);
        if (choice == 0) {
            return;
        }
        battleEndBehavior = choice - 1;
    }

    private String getBattleEndBehaviorString() {

        switch (battleEndBehavior) {
            case 0:
                return "Nothing";
            case 1:
                return "Reset";
            case 2:
                return "Heal";
            default:
                throw new IllegalStateException("battleEndBehavior set to illegal value.");
        }
    }


}
