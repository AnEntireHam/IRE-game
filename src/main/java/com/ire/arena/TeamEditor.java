package com.ire.arena;

import com.ire.entities.Entity;
import com.ire.entities.enemies.Caster;
import com.ire.entities.enemies.Skeleton;
import com.ire.entities.enemies.TrainingDummy;
import com.ire.entities.players.Mage;
import com.ire.entities.players.Warrior;
import com.ire.tools.PrintControl;
import com.ire.tools.UserInput;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TeamEditor {

    private final static String SAVE_DIRECTORY = "team/";

    // Methods

    public static void editTeam(ArrayList<Entity> team) {
        while (true) {
            System.out.println("Choose to edit the team or an Entity.");
            ArrayList<String> options = new ArrayList<>(Collections.singletonList("Edit Team"));
            for (Entity e : team) {
                options.add(e.getName() + "  Lv. " + e.getLevel());
            }

            int choice = UserInput.cancelableMenu(options);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    chooseEditOption(team);
                    break;
                default:
                    EntityEditor.editEntity(team.get(choice - 2));
                    break;
            }
        }
    }

    private static void chooseEditOption(ArrayList<Entity> team) {
        while (true) {
            System.out.println("Choose an option or an entity to edit.");
            ArrayList<String> options = new ArrayList<>(
                    Arrays.asList("Add Entity", "Remove Entity", "Copy Entity", "Load Team", "Save Team"));

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
                    loadTeam(team, "save1", true);
                    break;
                case 5:
                    saveTeam(team, "save1", true);
                    break;
            }
        }
    }

    private static void addEntity(ArrayList<Entity> team) {
        while (true) {
            System.out.println("Select a leveling type.");
            String[] options = {"Player", "Enemy"};
            switch (UserInput.cancelableMenu(options)) {
                case 0:
                    return;
                case 1:
                    addPlayer(team);
                    return;
                case 2:
                    addEnemy(team);
                    return;
            }
        }
    }

    // Probably refactor these later.
    private static void addPlayer(ArrayList<Entity> team) {
        while (true) {
            System.out.println("Select a class.");
            String[] options = {"Warrior", "Mage"};
            int choice = UserInput.cancelableMenu(options);
            if (choice == 0) {
                break;
            }
            int level = promptStartingLevel();
            if (level == 0) {
                continue;
            }

            Entity e;
            switch (choice) {
                case 1:
                    e = new Warrior(level);
                    break;
                case 2:
                    e = new Mage(level);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
            team.add(e);
            break;
        }
    }

    private static void addEnemy(ArrayList<Entity> team) {
        while (true) {
            System.out.println("Select a class.");
            String[] options = {"Skeleton", "Caster", "Training Dummy"};
            int choice = UserInput.cancelableMenu(options);
            if (choice == 0) {
                break;
            }
            int level = promptStartingLevel();
            if (level == 0) {
                continue;
            }

            Entity e;
            switch (choice) {
                case 1:
                    e = new Skeleton(level);
                    break;
                case 2:
                    e = new Caster(level);
                    break;
                case 3:
                    e = new TrainingDummy(level);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
            team.add(e);
            break;
        }
    }

    private static int promptStartingLevel() {
        System.out.println("Choose a starting level (enter 0 to cancel).");
        return UserInput.getUserInt(0, 99);
    }

    private static void removeEntity(ArrayList<Entity> team) {
        System.out.println("Choose an entity to remove.");
        int choice = getEntityChoice(team);
        if (choice == 0) {
            return;
        }
        team.remove(choice - 1);
    }

    private static void copyEntity(ArrayList<Entity> team) {
        System.out.println("Choose an entity to copy.");
        int choice = getEntityChoice(team);
        if (choice == 0) {
            return;
        }
        ArrayList<Entity> copy = new ArrayList<>();
        copy.add(team.get(choice - 1));
        saveTeam(copy, "temp", false);
        loadTeam(copy, "temp", false);
        team.addAll(copy);
    }

    private static int getEntityChoice(ArrayList<Entity> team) {
        ArrayList<String> options = new ArrayList<>();
        for (Entity e : team) {
            options.add(e.getName() + "  Lv. " + e.getLevel());
        }
        return UserInput.cancelableMenu(options);
    }

    public static void loadTeam(ArrayList<Entity> team, String path, boolean showMessage) {
        if (showMessage) {
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

    public static void saveTeam(ArrayList<Entity> team, String path, boolean showMessage) {
        if (showMessage) {
            System.out.println("Saving team to \"team/" + path + ".ser\"...");
            PrintControl.sleep(500);
        }
        File f = new File(SAVE_DIRECTORY + path + ".ser");

        try (FileOutputStream fileStream = new FileOutputStream(f)) {
            if (f.createNewFile() && showMessage) {
                System.out.println("Creating file...");
            }
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(team);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
