package ire.tools;

import ire.audio.AudioStream;
import ire.entities.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Tools {

    protected static AudioStream menuBoop = new AudioStream("menuBoop");
    protected static AudioStream menuError = new AudioStream("menuError");

    // Probably include a text speed/manual skip option, and or always emptyPrompt() / always sleep
    public static void sleep(int time) {

        try {

            Thread.sleep(time);

        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // Unsure if this works on non-windows.
    public static void clear() {

        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                //System.out.print("\033\143");
                for (int i = 0; i < 10; i++) {
                    System.out.println();
                }
            }
        } catch (IOException | InterruptedException ignored) {}
    }


    public static void emptyPrompt() {
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    public static int round(double number) {
        return (int) Math.round(number);
    }

    public static int getUserInt(int min, int max) {

        Scanner s = new Scanner(System.in);
        int input = 0;
        boolean invalid;

        do {
            invalid = false;

            try {
                System.out.print("> ");
                input = s.nextInt();
            } catch(InputMismatchException n) {
                s.next();
                System.out.println("Please input a whole number between " + min + " and " + max + ".");
                invalid = true;
            }

            if (!invalid) {
                if (input < min || input > max) {
                    //System.out.println("Please input a whole number between " + min + " and " + max + ".");
                    menuError.play();
                    invalid = true;
                }
            }

        } while (invalid);

        menuBoop.play();
        clear();
        return (input);
    }

    public static int menu(ArrayList<String> options, int startIndex) {

        for (int i = startIndex; i < options.size() + startIndex; i++) {
            System.out.println("[" + (i) + "] " + options.get(i - startIndex));
        }
        System.out.println();
        return getUserInt(startIndex, options.size() + startIndex - 1);
    }

    public static int cancelableMenu(ArrayList<String> options) {

        for (int i = 1; i < options.size() + 1; i++) {
            System.out.println("[" + (i) + "] " + options.get(i - 1));
        }
        System.out.println("\n[0] Cancel\n");

        return Tools.getUserInt(0, options.size());
    }

    public static void sortEntityList(ArrayList<Entity> entities) {

        entities.sort(Comparator.comparing(Entity::isAlive).reversed()
                .thenComparing(Entity::getLevel).thenComparing(Entity::getName));

    }
}
