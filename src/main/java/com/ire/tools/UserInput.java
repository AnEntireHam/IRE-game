package com.ire.tools;

import com.ire.audio.AudioClip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {

    private static final AudioClip MENU_BOOP = new AudioClip("menuBoop");
    private static final AudioClip MENU_ERROR = new AudioClip("menuError");

    public static int getUserInt(int min, int max, ArrayList<Integer> excluded) {
        Scanner s = new Scanner(System.in);
        int input = 0;
        boolean valid = false;

        while (!valid) {
            try {
                if (!PrintControl.isBotClient()) {
                    System.out.print("> ");
                }
                input = s.nextInt();
            } catch(InputMismatchException n) {
                s.next();
                MENU_ERROR.play();
                continue;
            }

            if (input < min || input > max || excluded.contains(input)) {
                MENU_ERROR.play();
                continue;
            }
            valid = true;
        }
        MENU_BOOP.play();
        PrintControl.clear();
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
        return UserInput.getUserInt(startIndex - 1, options.size() + startIndex - 1, excluded);
    }

    public static int cancelableMenu(ArrayList<String> options, ArrayList<Integer> excluded) {
        return cancelableMenu(options, excluded, 1);
    }

    public static int cancelableMenu(ArrayList<String> options) {
        return cancelableMenu(options, new ArrayList<>(), 1);
    }

    public static int cancelableMenu(String[] options, Integer[] excluded, int startIndex) {
        return cancelableMenu(
                new ArrayList<>(Arrays.asList(options)), new ArrayList<>(Arrays.asList(excluded)), startIndex);
    }

    public static int cancelableMenu(String[] options, Integer[] excluded) {
        return cancelableMenu(
                new ArrayList<>(Arrays.asList(options)), new ArrayList<>(Arrays.asList(excluded)), 1);
    }

    public static int cancelableMenu(String[] options) {
        return cancelableMenu(new ArrayList<>(Arrays.asList(options)), new ArrayList<>(), 1);
    }

    public static int cancelableMenu(String[] options, int startIndex) {
        return cancelableMenu(new ArrayList<>(Arrays.asList(options)), new ArrayList<>(), startIndex);
    }
}
