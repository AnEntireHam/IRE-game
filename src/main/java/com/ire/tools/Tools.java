package com.ire.tools;

import com.ire.audio.AudioStream;
import com.ire.entities.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;

public class Tools {

    private static final AudioStream MENU_BOOP = new AudioStream("menuBoop");
    private static final AudioStream MENU_ERROR = new AudioStream("menuError");


    // ***********************************
    // Display Methods
    // ***********************************

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


    // ***********************************
    // User Interface
    // ***********************************

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
                MENU_ERROR.play();
                valid = false;
            }

            if (valid && (input < min || input > max)) {
                MENU_ERROR.play();
                valid = false;
            }

        } while (!valid);

        MENU_BOOP.play();
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
                MENU_ERROR.play();
                valid = false;
            }

            if (valid && (input < min || input > max || excluded.contains(input))) {
                MENU_ERROR.play();
                valid = false;
            }

        } while (!valid);

        MENU_BOOP.play();
        clear();
        return input;
    }

    //  Can probably shove this into cancelableStructure somehow
    public static int menu(ArrayList<String> options, int startIndex) {

        for (int i = startIndex; i < options.size() + startIndex; i++) {
            System.out.println("[" + (i) + "] " + options.get(i - startIndex));
        }
        System.out.println();
        return getUserInt(startIndex, options.size() + startIndex - 1);
    }

    private static void cancelableStructure(ArrayList<String> options, ArrayList<Integer> excluded) {

        for (int i = 1; i < options.size() + 1; i++) {

            if (!excluded.contains(i)) {
                System.out.print("[" + (i) + "] ");
            } else {
                System.out.print("[X] ");
            }

            if (options.size() + 1 > 9 && i < 10) {
                System.out.print(" ");
            }
            System.out.println(options.get(i - 1));
        }

        System.out.println("\n[0] Cancel\n");
    }

    public static int cancelableMenu(ArrayList<String> options) {

        cancelableStructure(options, new ArrayList<>());
        return Tools.getUserInt(0, options.size());
    }

    public static int cancelableMenu(ArrayList<String> options, ArrayList<Integer> excluded) {

        cancelableStructure(options, excluded);
        return Tools.getUserInt(0, options.size(), excluded);
    }


    // ***********************************
    // Miscellaneous Methods
    // ***********************************

    public static void sortEntityList(ArrayList<Entity> entities) {

        entities.sort(Comparator.comparing(Entity::isAlive).reversed()
                .thenComparing(Entity::getLevel).thenComparing(Entity::getName));
    }

    public static String createBar(float numerator, float denominator, int length) {

        StringBuilder output = new StringBuilder();

        if (denominator <= 0 || length <= 0) {
            throw new IllegalArgumentException("The denominator or length is equal to or less than 0.");
        }

        float quotient = (numerator / denominator) * length;

        //  Later, use colorization to allow bars to "wrap around".
        for (int i = 0; i < quotient && i < length; i++) {
            output.append(colorize("█", GREEN_TEXT()));
        }

        for (int i = 0; i < length - quotient; i++) {
            output.append(colorize("░", GREEN_TEXT()));
        }

        return output.toString();
    }

}
