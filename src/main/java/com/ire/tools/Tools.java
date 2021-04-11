package com.ire.tools;

import com.diogonunes.jcolor.Attribute;
import com.ire.audio.AudioClip;
import com.ire.entities.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Tools {

    private static final AudioClip MENU_BOOP = new AudioClip("menuBoop");
    private static final AudioClip MENU_ERROR = new AudioClip("menuError");

    private static boolean botClient = false;

    // Display Methods

    // TODO: Extract the magic numbers that are ~~everywhere~~
    // TODO: Include a text speed/manual skip option, and or always emptyPrompt() / always sleep

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
        try {
            if (!botClient && System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                return;
            }
            if (!botClient) {
                for (int i = 0; i < 75; i++) {
                    System.out.println();
                }
                //System.out.print("\\033[H\\033[2J");
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error clearing the screen");
            e.printStackTrace();
        }
    }

    public static void emptyPrompt() {
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }


    // User Interface

    public static int getUserInt(int min, int max, ArrayList<Integer> excluded) {

        Scanner s = new Scanner(System.in);
        int input = 0;
        boolean valid;

        do {
            valid = true;

            try {
                if (!Tools.isBotClient()) {
                    System.out.print("> ");
                }
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

            if (!botClient && !valid) {
                System.out.println("Enter a valid integer between " + min + " and " + max);
            }

        } while (!valid);

        MENU_BOOP.play();
        clear();
        return input;
    }

    public static int getUserInt(int min, int max) {

        return getUserInt(min, max, new ArrayList<>());
    }

    // TODO: Integrate excluded feature with normal menus
    public static int menu(String[] options, int startIndex) {
        for (int i = startIndex; i < options.length + startIndex; i++) {
            System.out.println("[" + (i) + "] " + options[(i - startIndex)]);
        }
        System.out.println();
        return getUserInt(startIndex, options.length + startIndex - 1);
    }

    public static int menu(String[] options) {
        return menu(options, 1);
    }

    public static int menu(ArrayList<String> options, int startIndex) {
        return menu((String[]) options.toArray(), startIndex);
    }

    public static int menu(ArrayList<String> options) {
        return menu(options.toArray(new String[0]), 1);
    }

    public static int cancelableMenu(ArrayList<String> options, ArrayList<Integer> excluded, int startIndex) {
        for (int i = startIndex; i < options.size() + startIndex; i++) {

            if (!excluded.contains(i)) {
                System.out.print("[" + (i) + "] ");
            } else {
                System.out.print("[X] ");
            }

            if (options.size() + 1 > 9 && i < 10) {
                System.out.print(" ");
            }
            System.out.println(options.get(i - startIndex));
        }
        System.out.println("\n[0] Cancel\n");
        return Tools.getUserInt(startIndex - 1, options.size() + startIndex - 1, excluded);
    }

    public static int cancelableMenu(ArrayList<String> options, ArrayList<Integer> excluded) {
        return cancelableMenu(options, excluded, 1);
    }

    public static int cancelableMenu(ArrayList<String> options) {
        return cancelableMenu(options, new ArrayList<>(), 1);
    }


    // Miscellaneous Methods

    // TODO: Figure out if this should actually be used mid-battle.
    public static void sortEntityList(ArrayList<Entity> entities) {

        entities.sort(Comparator.comparing(Entity::isAlive).reversed()
                .thenComparing(Entity::getLevel).thenComparing(Entity::getName));
    }

    // colors must contain at least one color.
    // TODO: These methods are WET.
    // TODO: Redirect coloredBar calls if botClient
    public static String createColoredBar(float numerator, float denominator, int length,
                                          Attribute[] colors) {

        if (denominator <= 0 || length <= 0) {
            throw new IllegalArgumentException("The denominator or length is less than or equal to 0.");
        }

        if (numerator < 0) {
            return createColoredNegativeBar(numerator, denominator, length, colors);
        }

        StringBuilder output = new StringBuilder();
        String primaryShading = "█";
        String secondaryShading = "▒";
        Attribute primaryColor = colors[0];
        Attribute secondaryColor = colors[0];
        float quotient;
        int stacks = 0;

        while (numerator > denominator) {

            numerator -= denominator;
            stacks++;
            secondaryShading = primaryShading;
            primaryColor = colors[(Math.min((stacks), colors.length - 1))];
            secondaryColor = colors[(Math.min((stacks - 1), colors.length - 1))];
        }

        quotient = Math.round((numerator / denominator) * length);


        for (int i = 0; i < quotient && i < length; i++) {
            output.append(colorize(primaryShading, primaryColor));
        }
        for (int i = 0; i < length - quotient; i++) {
            output.append(colorize(secondaryShading, secondaryColor));
        }

        return output.toString();

    }

    public static String createBar(float numerator, float denominator, int length) {

        if (denominator <= 0 || length <= 0) {
            throw new IllegalArgumentException("The denominator or length is less than or equal to 0.");
        }

        if (numerator < 0) {
            return createNegativeBar(numerator, denominator, length);
        }

        StringBuilder output = new StringBuilder();
        String primaryShading = "█";
        String secondaryShading = "▓";
        String[] shadingSequence = new String[] {"▒", "▓"};
        float quotient;
        int stacks = 0;

        while (numerator > denominator) {

            numerator -= denominator;
            stacks++;
            secondaryShading = shadingSequence[(Math.min((stacks - 1), shadingSequence.length - 1))];
        }

        quotient = Math.round((numerator / denominator) * length);


        for (int i = 0; i < quotient && i < length; i++) {
            output.append(primaryShading);
        }
        for (int i = 0; i < length - quotient; i++) {
            output.append(secondaryShading);
        }

        if (stacks > 0) {

            output.append(" +").append(stacks);
        }

        return output.toString();
    }

    private static String createNegativeBar(float numerator, float denominator, int length) {

        StringBuilder output = new StringBuilder();
        String primaryShading = "▒";
        String secondaryShading = " ";
        float quotient;
        int stacks = 1;
        numerator = Math.abs(numerator);

        while (numerator > denominator) {

            numerator -= denominator;
            stacks++;
            primaryShading = " ";
        }

        quotient = Math.round((numerator / denominator) * length);


        for (int i = 0; i < quotient && i < length; i++) {
            output.append(primaryShading);
        }
        for (int i = 0; i < length - quotient; i++) {
            output.append(secondaryShading);
        }

        output.append(" -").append(stacks);

        return output.toString();
    }

    private static String createColoredNegativeBar(float numerator, float denominator, int length,
                                                   Attribute[] colors) {
        StringBuilder output = new StringBuilder();
        String primaryShading = "▒";
        String secondaryShading = " ";
        float quotient;
        int stacks = 1;
        numerator = Math.abs(numerator);

        while (numerator > denominator) {

            numerator -= denominator;
            stacks++;
            primaryShading = " ";
        }

        quotient = Math.round((numerator / denominator) * length);


        for (int i = 0; i < quotient && i < length; i++) {
            output.append(colorize(primaryShading, colors[0]));
        }
        for (int i = 0; i < length - quotient; i++) {
            output.append(colorize(secondaryShading, colors[0]));
        }

        output.append(" -").append(stacks);

        return output.toString();
    }

    public static boolean isBotClient() {
        return Tools.botClient;
    }

    public static void setBotClient(boolean botClient) {
        Tools.botClient = botClient;
    }

    /*public static String createBar(float numerator, float denominator, int length) {

        Attribute[] colors = new Attribute[] {WHITE_TEXT()};

        return createBar(numerator, denominator, length, colors);
    }*/

}
