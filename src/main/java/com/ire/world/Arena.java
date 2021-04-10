package com.ire.world;

import com.ire.combat.Battle;
import com.ire.entities.Entity;
import com.ire.tools.Tools;

import java.util.ArrayList;
import java.util.Collections;

public class Arena {

    private ArrayList<Entity> team1;
    private ArrayList<Entity> team2;
    private int surprise = 0;
    private boolean refresh = true;


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

        }
    }

    private void startBattle() {
        if (team1 == null || team1.isEmpty() || team2 == null || team2.isEmpty()) {
            System.out.println("Teams haven't been initialized yet.");
            return;
        }
        ArrayList<Entity> copy1 = team1;
        ArrayList<Entity> copy2 = team2;

        Battle b = new Battle(team1, team2);
        b.runBattle(surprise);
        if (refresh) {
            team1 = copy1;
            team2 = copy2;
        }
    }

    private void chooseEditTeam() {
        while (true) {
            System.out.println("Choose a team to edit");
            ArrayList<String> options = new ArrayList<>(
                    Collections.singletonList("Team 1, Team2"));

            int choice = Tools.cancelableMenu(options);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    editTeam(team1);
                case 2:
                    editTeam(team2);
            }
        }
    }

    private void editTeam(ArrayList<Entity> team) {
        while (true) {
            System.out.println("Choose an entity to edit.");
            ArrayList<String> options = new ArrayList<>();
            options.add("Add Entity");
            options.add("Remove Entity");
            options.add("Copy Entity");
            for (Entity e : team) {
                options.add(e.getName() + " " + e.getLevel());
            }

            int choice = Tools.cancelableMenu(options);

            switch (choice) {
                case 0:
                    return;
                case 1:
                    addEntity(team);
                case 2:
                    removeEntity(team);
                case 3:
                    copyEntity(team);
                default:
                    editEntity(team.get(options.size() - 4));
            }
        }
    }

    private void addEntity(ArrayList<Entity> team) {

        System.out.println("");

        System.out.println("Choose player or enemy type Entity");
        ArrayList<String> options = new ArrayList<>();
        options.add("Player");
        options.add("Enemy");

        int choice = Tools.cancelableMenu(options);

        switch (choice) {
            case 0:
                return;
            case 1:

        }

    }

    private void removeEntity(ArrayList<Entity> team) {

    }

    private void copyEntity(ArrayList<Entity> team) {

    }

    private void editEntity(Entity entity) {

    }

    private void editSettings() {
        while (true) {
            System.out.println("Battle Settings");
            ArrayList<String> options = new ArrayList<>();
            options.add("Surprise: " + surprise);
            options.add("Refresh: " + refresh);

            int choice = Tools.cancelableMenu(options);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    editSurprise();
                    break;
                case 2:
                    refresh = !refresh;
                    break;
            }
        }
    }

    private void editSurprise() {
        System.out.println("Surprise Settings");
        ArrayList<String> options = new ArrayList<>();
        options.add("No Surprise");
        options.add("Team 1 Has Surprise");
        options.add("Team 2 Has Surprise");

        surprise = Tools.menu(options);
    }


}
