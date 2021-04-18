package com.ire.tools;

import com.ire.combat.statuseffects.generativeeffect.Bleed;
import com.ire.combat.statuseffects.generativeeffect.Regeneration;
import com.ire.combat.statuseffects.stateffects.AttackUp;
import com.ire.combat.statuseffects.stateffects.MagicUp;
import com.ire.entities.Entity;
import com.ire.entities.enemies.Skeleton;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EntityEditor {

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
                    editEntity(team.get(choice - 2));
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
        Skeleton s = new Skeleton(1);
        s.setName("MR JANGELY BONES");
        team.add(s);

        System.out.println("WIP.");
        PrintControl.sleep(300);

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

    private static void editEntity(Entity entity) {
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
}
