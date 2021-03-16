package com.ire.world;

import java.util.Scanner;

// Fix requirement for second word on inventory/help

public class BasicTextAdventure {

    private boolean gameOver = false;
    private Inventory playerInventory;
    private Room currentRoom;

    public void setup() {

        playerInventory = new Inventory();

        Item sword = new Item("sword", "A tall, glowing sword", "There is a tall, glowing sword lying here.");
        Item dragon = new Item("dragon", "X", "A massive red dragon glares down at you.");

        Room room1 = new Room("You are in a large underground cavern. ",
                "A small crystal bridge extends north over a ",
                "rushing river, flowing from northwest to southeast.");
        Room room2 = new Room("This is a large lair filled with gold. Tall columns ",
                "of ornate stonework ring a foreboding mountain of stone at the center of the treasure.");

        room1.addPath("north", room2);
        room2.addPath("south", room1);

        room1.addItem(sword);
        room2.addItem(dragon);

        currentRoom = room1;

    }

    public void run() {

        Scanner in = new Scanner(System.in);

        System.out.println("The adventure begins...");

        while (!gameOver) {

            System.out.println(currentRoom.getLongDescription());

            System.out.print("> ");
            String message = parse(in.nextLine().trim());
            System.out.println(message);
            System.out.println();
        }

        System.out.println("You are victorious! The end!");
    }

    public String parse(String command) {

        String[] parts = command.split(" ", 2);

        if (parts.length != 2) {
            return "Unknown command: " + command;
        }

        String action = parts[0];
        String target = parts[1];

        //. Second word doesn't matter: inventory ************me
        switch (action) {
            case "move":
            case "go":
                return move(target);
            case "get":
            case "take":
                return get(target);
            case "drop":
                return drop(target);
            case "use":
                return use(target);
            case "inventory":
                return inventory();
            case "help":
                return help();
            default:
                return "Unknown command: " + command;
        }

    }

    public String move(String direction) {

        Room nextRoom = currentRoom.roomAtPath(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            return "Moving that direction.";
        } else {
            return "You cannot move that direction.";
        }
    }

    public String get(String target) {

        Item i = currentRoom.removeItem(target);
        if (i != null) {
            playerInventory.addItem(i);
            return "Taken.";
        } else {
            return "You cannot find that item.";
        }
    }

    public String drop(String target) {

        Item i = playerInventory.removeItem(target);
        if (i != null) {
            currentRoom.addItem(i);
            return "Dropped.";
        } else {
            return "You are not carrying that item.";
        }
    }

    public String use(String target) {

        Item i = playerInventory.getItem(target);
        if (i != null) {
            Item dragon = currentRoom.getItem("dragon");
            if (dragon != null) {
                gameOver = true;
                return "You swing the sword at the dragon, fatally striking it beneath the scales!";
            } else {
                return "You swing the sword, but there is no target here.";
            }
        } else {
            return "You cannot use that item. You are not carrying it.";
        }
    }

    public String inventory() {

        String returnValue = "You have the following items:\n";
        for (Item i : playerInventory.getInventory()) {
            returnValue += i.getName() + "\n";
        }
        return returnValue;
    }

    public String help() {

        return "Commands:\nMove\nGet\nDrop\nUse\nInventory";
    }
}

