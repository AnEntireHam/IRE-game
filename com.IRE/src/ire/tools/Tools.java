package ire.tools;

import ire.audio.AudioStream;
import ire.entities.Entity;

import java.io.IOException;
import java.util.*;

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

    // Not sure if \033 works, but the for loop puts text at bottom of screen.
    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                for (int i = 0; i < 75; i++) {
                    System.out.println();
                }
                System.out.print("\033");
            }
        } catch (IOException | InterruptedException ignored) {}
    }

    public static void emptyPrompt() {
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    public static int getUserInt(int min, int max) {

        Scanner s = new Scanner(System.in);
        int input = 0;
        boolean valid;

        do {
            valid = true;

            try {
                System.out.print("> ");
                input = s.nextInt();
            } catch(InputMismatchException n) {
                s.next();
                menuError.play();
                valid = false;
            }

            if (valid && (input < min || input > max)) {
                menuError.play();
                valid = false;
            }

        } while (!valid);

        menuBoop.play();
        clear();
        return input;
    }

    public static int getUserInt(int min, int max, ArrayList<Integer> excluded) {

        Scanner s = new Scanner(System.in);
        int input = 0;
        boolean valid;

        do {
            valid = true;

            try {
                System.out.print("> ");
                input = s.nextInt();
            } catch(InputMismatchException n) {
                s.next();
                menuError.play();
                valid = false;
            }

            if (valid && (input < min || input > max || excluded.contains(input))) {
                menuError.play();
                valid = false;
            }

        } while (!valid);

        menuBoop.play();
        clear();
        return input;
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

    public static int cancelableMenu(ArrayList<String> options, ArrayList<Integer> excluded) {

        for (int i = 1; i < options.size() + 1; i++) {
            if (excluded.contains(i)) {
                System.out.println("[X] " + options.get(i - 1));
            } else {
                System.out.println("[" + (i) + "] " + options.get(i - 1));
            }
        }
        System.out.println("\n[0] Cancel\n");

        return Tools.getUserInt(0, options.size(), excluded);
    }

    public static void sortEntityList(ArrayList<Entity> entities) {

        entities.sort(Comparator.comparing(Entity::isAlive).reversed()
                .thenComparing(Entity::getLevel).thenComparing(Entity::getName));
    }
}
