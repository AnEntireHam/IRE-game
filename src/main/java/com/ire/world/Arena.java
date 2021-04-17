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
import com.ire.combat.statuseffects.generativeeffect.Bleed;
import com.ire.combat.statuseffects.generativeeffect.Regeneration;
import com.ire.combat.statuseffects.stateffects.AttackUp;
import com.ire.combat.statuseffects.stateffects.MagicUp;
import com.ire.entities.Entity;
import com.ire.entities.enemies.Caster;
import com.ire.entities.enemies.Skeleton;
import com.ire.entities.players.Mage;
import com.ire.entities.players.Warrior;
import com.ire.tools.PrintControl;
import com.ire.tools.UserInput;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Arena {

    private ArrayList<Entity> team1;
    private ArrayList<Entity> team2;
    private int surprise = 0;
    private int battleEndBehavior = 1;
    private boolean giveRewards = false;
    private final static String SAVE_DIRECTORY = "team/";

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
            String[] options = {"Start Battle", "Edit Teams", "Edit Battle Settings", "Exit"};

            switch (UserInput.menu(options)) {
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
            PrintControl.sleep(1000);
            PrintControl.clear();
            return;
        }

        if (battleEndBehavior == 1) {
            saveTeam(team1, "temp1",false);
            saveTeam(team2, "temp2", false);
         }
        // Somehow, even with surprise favoring team1, team2 went first. Not sure how, but watch out for this.
        Battle b = new Battle(team1, team2, giveRewards);
        b.runBattle(surprise);

        if (battleEndBehavior == 1) {
            loadTeam(team1, "temp1", false);
            loadTeam(team2, "temp2", false);
            return;
        }
        if (battleEndBehavior == 2) {
            team1.forEach((e) -> e.fullHeal(RemoveCondition.LEVEL_UP));
            team2.forEach((e) -> e.fullHeal(RemoveCondition.LEVEL_UP));
        }
    }

    private void chooseEditTeam() {
        while (true) {
            System.out.println("Choose a team to edit");
            String[] options = {"Team 1", "Team 2", "Load Defaults"};

            switch (UserInput.cancelableMenu(options)) {
                case 0:
                    return;
                case 1:
                    editTeam(team1);
                    break;
                case 2:
                    editTeam(team2);
                    break;
                case 3:
                    loadDefaultTeams();
                    break;
            }
        }
    }

    private void loadDefaultTeams() {
        team1 = new ArrayList<>();
        team2 = new ArrayList<>();

        Warrior warrior = new Warrior();
        Mage mage = new Mage();
        Caster friend = new Caster(2);
        friend.setControllable(true);
        team1.add(warrior);
        team1.add(mage);
        //team1.add(friend);

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
            System.out.println("Choose an option or an entity to edit.");
            ArrayList<String> options = new ArrayList<>(
                    Arrays.asList("Add Entity", "Remove Entity", "Copy Entity", "Load Team", "Save Team"));
            for (Entity e : team) {
                options.add(e.getName() + "  Lv. " + e.getLevel());
            }

            int choice = UserInput.cancelableMenu(options);
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
                case 4:
                    loadTeam(team, "save1",true);
                    break;
                case 5:
                    saveTeam(team, "save1", true);
                    break;
                default:
                    editEntity(team.get(choice - 6));
                    break;
            }
        }
    }

    private void addEntity(ArrayList<Entity> team) {

        Skeleton s = new Skeleton(1);
        s.setName("MR JANGELY BONES");
        team.add(s);

        System.out.println("WIP.");
        PrintControl.sleep(1000);

        /*System.out.println("Choose entity type");
        ArrayList<String> options = new ArrayList<>();
        options.add("Player");
        options.add("Enemy");

        int choice = PrintControl.cancelableMenu(options);

        switch (choice) {
            case 0:
                return;
            case 1:

        }*/

    }

    private void removeEntity(ArrayList<Entity> team) {
        System.out.println("WIP.");
        PrintControl.sleep(1000);
    }

    private void copyEntity(ArrayList<Entity> team) {
        System.out.println("WIP.");
        PrintControl.sleep(1000);
    }

    private void saveTeam(ArrayList<Entity> team, String path, boolean message) {

        if (message) {
            System.out.println("Saving team to \"team/" + path + ".ser\"...");
            PrintControl.sleep(500);
        }
        File f = new File(SAVE_DIRECTORY + path + ".ser");

        try {
            if (f.createNewFile() && message) {
                System.out.println("Creating file...");
            }
        } catch (IOException e) {
            System.out.println("Error creating file.");
            e.printStackTrace();
        }

        try (FileOutputStream fileStream = new FileOutputStream(f);
             ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(team);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTeam(ArrayList<Entity> team, String path, boolean message) {

        if (message) {
            System.out.println("Loading team from \"team/" + path + ".ser\"...");
            PrintControl.sleep(500);
        }
        File f = new File(SAVE_DIRECTORY + path + ".ser");

        try (FileInputStream fileStream = new FileInputStream(f);
             ObjectInputStream objectStream = new ObjectInputStream(fileStream)) {
            //noinspection unchecked
            ArrayList<Entity> temp = (ArrayList<Entity>) objectStream.readObject();
            team.clear();
            team.addAll(temp);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void editEntity(Entity entity) {
        System.out.println("WIP.");
        // PrintControl.sleep(1000);
        System.out.println("Select an effect to apply to this entity");
        String[] options = {"Damage", "Heal", "Man Heal", "Atk Up", "Mag Up", "Apply Bleed", "Apply Regen"};
        AttackUp au = new AttackUp(1);
        MagicUp mu = new MagicUp(1);
        Bleed bleed = new Bleed(1);
        bleed.setStrength(2);
        Regeneration regen = new Regeneration(1);
        regen.setStrength(2);

        switch (UserInput.cancelableMenu(options)) {
            case 0:
                return;
            case 1:
                entity.takeDamage(3, true);
                break;
            case 2:
                entity.regenerateHealth(3, false, true);
                break;
            case 3:
                entity.regenerateMana(3, false, true);
                break;
            case 4:
                au.apply(entity, entity);
                break;
            case 5:
                mu.apply(entity, entity);
                break;
            case 6:
                bleed.apply(entity, entity);
                break;
            case 7:
                regen.apply(entity, entity);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value in editEntity");
        }
    }

    private void editSettings() {
        while (true) {
            System.out.println("Battle Settings");
            String[] options = {"Surprise: " + getSurpriseString(),
                    "Post-Battle: " + getBattleEndBehaviorString(),
                    "Give Rewards: " + giveRewards};

            switch (UserInput.cancelableMenu(options)) {
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
        if (surprise == 2) {
            surprise = 0;
            return;
        }
        surprise++;
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
        if (battleEndBehavior == 2) {
            battleEndBehavior = 0;
            return;
        }
        battleEndBehavior++;
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
