package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class for handling ANSI escape codes for coloring terminal text.
 * Provides constants for various text and background colors, text attributes,
 * and methods for wrapping text with color codes.
 */
public class AsciiColors {

    // Reset color
    public static final String RESET = "\u001B[0m";

    // Regular colors
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Bright colors
    public static final String BRIGHT_BLACK = "\u001B[90m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    // Background colors
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";

    // Bright background colors
    public static final String BG_BRIGHT_BLACK = "\u001B[100m";
    public static final String BG_BRIGHT_RED = "\u001B[101m";
    public static final String BG_BRIGHT_GREEN = "\u001B[102m";
    public static final String BG_BRIGHT_YELLOW = "\u001B[103m";
    public static final String BG_BRIGHT_BLUE = "\u001B[104m";
    public static final String BG_BRIGHT_PURPLE = "\u001B[105m";
    public static final String BG_BRIGHT_CYAN = "\u001B[106m";
    public static final String BG_BRIGHT_WHITE = "\u001B[107m";

    // Text attributes
    public static final String BOLD = "\u001B[1m";
    public static final String DIM = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLINK = "\u001B[5m";
    public static final String REVERSE = "\u001B[7m";
    public static final String HIDDEN = "\u001B[8m";
    public static final String STRIKETHROUGH = "\u001B[9m";

    /**
     * Returns a randomly selected color code from a predefined list of colors.
     *
     * @return A random color code as a string.
     */
    public static String getRandomColor() {
        List<String> colors = new ArrayList<>();
        colors.add(BLACK);
        colors.add(RED);
        colors.add(GREEN);
        colors.add(YELLOW);
        colors.add(BLUE);
        colors.add(PURPLE);
        colors.add(CYAN);
        colors.add(WHITE);
        colors.add(BRIGHT_BLACK);
        colors.add(BRIGHT_RED);
        colors.add(BRIGHT_GREEN);
        colors.add(BRIGHT_YELLOW);
        colors.add(BRIGHT_BLUE);
        colors.add(BRIGHT_PURPLE);
        colors.add(BRIGHT_CYAN);
        colors.add(BRIGHT_WHITE);

        Random random = new Random();
        return colors.get(random.nextInt(colors.size()));
    }

    /**
     * Demonstrates the usage of various text and background colors and attributes.
     * This method is typically used for testing and showcasing the color codes.
     *
     * @param args Command line arguments (not used).
     */
    public static void demo(String[] args) {
        System.out.println(BLACK + "This is black" + RESET);
        System.out.println(RED + "This is red" + RESET);
        System.out.println(GREEN + "This is green" + RESET);
        System.out.println(YELLOW + "This is yellow" + RESET);
        System.out.println(BLUE + "This is blue" + RESET);
        System.out.println(PURPLE + "This is purple" + RESET);
        System.out.println(CYAN + "This is cyan" + RESET);
        System.out.println(WHITE + "This is white" + RESET);

        System.out.println(BRIGHT_BLACK + "This is bright black" + RESET);
        System.out.println(BRIGHT_RED + "This is bright red" + RESET);
        System.out.println(BRIGHT_GREEN + "This is bright green" + RESET);
        System.out.println(BRIGHT_YELLOW + "This is bright yellow" + RESET);
        System.out.println(BRIGHT_BLUE + "This is bright blue" + RESET);
        System.out.println(BRIGHT_PURPLE + "This is bright purple" + RESET);
        System.out.println(BRIGHT_CYAN + "This is bright cyan" + RESET);
        System.out.println(BRIGHT_WHITE + "This is bright white" + RESET);

        System.out.println(BOLD + "This is bold text" + RESET);
        System.out.println(DIM + "This is dim text" + RESET);
        System.out.println(ITALIC + "This is italic text" + RESET);
        System.out.println(UNDERLINE + "This is underlined text" + RESET);
        System.out.println(BLINK + "This is blinking text" + RESET);
        System.out.println(REVERSE + "This is reverse video text" + RESET);
        System.out.println(HIDDEN + "This is hidden text" + RESET);
        System.out.println(STRIKETHROUGH + "This is strikethrough text" + RESET);
    }

    /**
     * Wraps a string with the specified color code.
     *
     * @param color The color code to apply.
     * @param string The string to be colored.
     * @return The colored string.
     */
    public static String colorWrap(String color, String string) {
        return color + string + RESET;
    }

    /**
     * Wraps a double value with the specified color code.
     *
     * @param color The color code to apply.
     * @param doubleToWrap The double value to be colored.
     * @return The colored string representation of the double value.
     */
    public static String colorWrap(String color, double doubleToWrap) {
        return color + String.valueOf(doubleToWrap) + RESET;
    }

    /**
     * Wraps an integer value with the specified color code.
     *
     * @param color The color code to apply.
     * @param intToWrap The integer value to be colored.
     * @return The colored string representation of the integer value.
     */
    public static String colorWrap(String color, int intToWrap) {
        return color + String.valueOf(intToWrap) + RESET;
    }
}
