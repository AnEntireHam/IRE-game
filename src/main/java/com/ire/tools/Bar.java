package com.ire.tools;

import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.CLEAR;

public class Bar {

    // Bar methods

    // Determines which type of bar should be used
    public static String createBar(float numerator, float denominator, int length,
                                   Attribute[] colors) {
        if (denominator < 1 || length < 1 || colors.length < 1) {
            throw new IllegalArgumentException("The denominator, length, or number of colors are less than 0.");
        }
        if (PrintControl.isBotClient()) {
            colors = new Attribute[]{CLEAR()};
        }
        if (numerator < 0) {
            return createNegativeBar(numerator, denominator, length, colors);
        }
        if (colors.length == 1) {
            return createMonochromeBar(numerator, denominator, length, colors);
        }
        return createPositiveBar(numerator, denominator, length, colors);
    }

    public static String createBar(float numerator, float denominator, int length) {
        return createBar(numerator, denominator, length, new Attribute[]{CLEAR()});
    }

    // createBar methods from hereon are responsible for determining shading and colors
    // At this point, colors.length > 1
    private static String createPositiveBar(float numerator, float denominator, int length, Attribute[] colors) {
        String primaryShading = "█";
        String secondaryShading = "░";
        Attribute primaryColor = colors[0];
        Attribute secondaryColor = colors[0];
        int stacks = 0;
        String sign = "";

        while (numerator > denominator) {
            numerator -= denominator;
            stacks++;
            secondaryShading = primaryShading;
            primaryColor = colors[(Math.min((stacks), colors.length - 1))];
            secondaryColor = colors[(Math.min((stacks - 1), colors.length - 1))];
        }
        if (stacks > 0) {
            sign = " +" + stacks;
        }

        return generateBar(numerator, denominator, length,
                primaryShading, secondaryShading, sign, primaryColor, secondaryColor);
    }

    private static String createMonochromeBar(float numerator, float denominator, int length, Attribute[] color) {
        String primaryShading = "▓";
        String secondaryShading = "░";
        int stacks = 0;
        String sign = "";

        while (numerator > denominator) {
            primaryShading = "█";
            secondaryShading = "▒";
            numerator -= denominator;
            stacks++;
        }
        if (stacks > 0) {
            sign = " +" + stacks;
        }

        return generateBar(numerator, denominator, length, primaryShading, secondaryShading,
                sign, color[0], color[0]);
    }

    private static String createNegativeBar(float numerator, float denominator, int length, Attribute[] colors) {
        String primaryShading = "▒";
        String secondaryShading = " ";
        int stacks = 1;
        numerator = Math.abs(numerator);

        while (numerator > denominator) {
            numerator -= denominator;
            stacks++;
            primaryShading = " ";
        }
        String sign = " -" + stacks;

        return generateBar(numerator, denominator, length, primaryShading, secondaryShading,
                sign, colors[0], colors[0]);
    }

    // Method responsible for actually generating the bar
    private static String generateBar(float numerator, float denominator, int length,
                                      String primaryShading, String secondaryShading, String sign,
                                      Attribute primaryColor, Attribute secondaryColor) {
        if (primaryColor.toString().equals(CLEAR().toString())) {
            return generateBar(numerator, denominator, length, primaryShading, secondaryShading, sign);
        }

        int quotient = Math.round((numerator / denominator) * length);
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < quotient && i < length; i++) {
            output.append(colorize(primaryShading, primaryColor));
        }
        for (int i = 0; i < length - quotient; i++) {
            output.append(colorize(secondaryShading, secondaryColor));
        }
        output.append(sign);
        return output.toString();
    }

    // Chiefly for instances where colors are entirely unsupported
    private static String generateBar(float numerator, float denominator, int length,
                                      String primaryShading, String secondaryShading, String sign) {
        int quotient = Math.round((numerator / denominator) * length);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < quotient && i < length; i++) {
            output.append(primaryShading);
        }
        for (int i = 0; i < length - quotient; i++) {
            output.append(secondaryShading);
        }
        output.append(sign);
        return output.toString();
    }

}
