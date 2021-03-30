package com.ire.tools;

import com.diogonunes.jcolor.Attribute;
import com.ire.audio.AudioClip;
import com.ire.audio.AudioStream;
import com.ire.entities.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.WHITE_TEXT;

public class Tools {

    private static final AudioClip MENU_BOOP = new AudioClip("menuBoop");
    private static final AudioClip MENU_ERROR = new AudioClip("menuError");


    // Display Methods

    // TODO: Extract the magic numbers that are ~~everywhere~~
    // TODO: Include a text speed/manual skip option, and or always emptyPrompt() / always sleep

    public static void sleep(int time, float multiplier) {

        try {
            Thread.sleep((long) (time * multiplier));
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void sleep(int time) {

        sleep(time, 1);
    }

    // TODO: Ask non-windows users if "\033" works.
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
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ignored) {}
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

    public static int getUserInt(int min, int max) {

        return getUserInt(min, max, new ArrayList<>());
    }

    public static int menu(ArrayList<String> options, int startIndex) {

        for (int i = startIndex; i < options.size() + startIndex; i++) {
            System.out.println("[" + (i) + "] " + options.get(i - startIndex));
        }
        System.out.println();
        return getUserInt(startIndex, options.size() + startIndex - 1);
    }

    public static int menu(ArrayList<String> options) {

        return menu(options, 1);
    }

    public static int cancelableMenu(ArrayList<String> options, ArrayList<Integer> excluded) {

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
        return Tools.getUserInt(0, options.size(), excluded);
    }

    public static int cancelableMenu(ArrayList<String> options) {

        return cancelableMenu(options, new ArrayList<>());
    }


    // Miscellaneous Methods

    // TODO: Figure out if this should actually be used mid-battle.
    public static void sortEntityList(ArrayList<Entity> entities) {

        entities.sort(Comparator.comparing(Entity::isAlive).reversed()
                .thenComparing(Entity::getLevel).thenComparing(Entity::getName));
    }

    // colors must contain at least one color.
    // TODO: These methods are WET.
    public static String createColoredBar(float numerator, float denominator, int length,
                                          Attribute[] colors) {

        if (denominator <= 0 || length <= 0) {
            throw new IllegalArgumentException("The denominator or length is less than or equal to 0.");
        }

        if (numerator < 0) {
            return createNegativeBar(numerator, denominator, length, colors);
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

    public static String createBar(float numerator, float denominator, int length,
                                   Attribute[] colors) {

        if (denominator <= 0 || length <= 0) {
            throw new IllegalArgumentException("The denominator or length is less than or equal to 0.");
        }

        if (numerator < 0) {
            return createNegativeBar(numerator, denominator, length, colors);
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
            output.append(colorize(primaryShading, colors[(Math.min((stacks), colors.length - 1))]));
        }
        for (int i = 0; i < length - quotient; i++) {
            output.append(colorize(secondaryShading, colors[(Math.min((stacks), colors.length - 1))]));
        }

        if (stacks > 0) {

            output.append(" +").append(stacks);
        }

        return output.toString();

    }

    private static String createNegativeBar(float numerator, float denominator, int length,
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

    public static String createBar(float numerator, float denominator, int length) {

        Attribute[] colors = new Attribute[] {WHITE_TEXT()};

        return createBar(numerator, denominator, length, colors);
    }

}
