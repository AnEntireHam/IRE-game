package com.ire.tools;

import com.ire.entities.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class PrintControl {

    private static boolean botClient = false;

    // TODO: Add a GUI or method which adds 5-15 characters of padding, based on user's width/height.
    // TODO: Extract the magic numbers that are ~~everywhere~~
    // TODO: Include a text speed/manual skip option, and or always emptyPrompt() / always sleep. Also account for sfx.
    public static void sleep(int time, float multiplier) {
        try {
            Thread.sleep((long) (time * multiplier));

        } catch(InterruptedException ex) {
            System.out.println("Main thread disrupted!");
            Thread.currentThread().interrupt();
        }
    }

    public static void sleep(int time) {
        sleep(time, 1);
    }

    // TODO: Ask non-windows users if "\033" works.
    public static void clear() {
        if (botClient) {
            return;
        }
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                return;
            }
            for (int i = 0; i < 75; i++) {
                System.out.println();
            }
            //System.out.print("\\033[H\\033[2J");
            Runtime.getRuntime().exec("clear");

        } catch (IOException | InterruptedException e) {
            System.out.println("Error clearing the screen");
            e.printStackTrace();
        }
    }

    public static void emptyPrompt() {
        if (botClient) {
            return;
        }
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    // TODO: Figure out if this should actually be used mid-battle.
    public static void sortEntityList(ArrayList<Entity> entities) {

        entities.sort(Comparator.comparing(Entity::isAlive).reversed()
                .thenComparing(Entity::getLevel).thenComparing(Entity::getName));
    }

    public static boolean isBotClient() {
        return PrintControl.botClient;
    }

    public static void setBotClient(boolean botClient) {
        PrintControl.botClient = botClient;
    }
}
