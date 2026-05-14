package menu;

import java.util.Scanner;

/**
 * Reusable console menu helpers.
 */
public class MenuHelper {

    private static final Scanner sc = new Scanner(System.in);

    /** Print a numbered list of options and return the 0-based chosen index. */
    public static <T> int promptChoice(String prompt, T[] options) {
        System.out.println("\n" + prompt);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("  [%d] %s%n", i + 1, options[i]);
        }
        return readInt(1, options.length) - 1;   // convert to 0-based
    }

    /** Read a non-empty string from the user. */
    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("  ✖  Input cannot be empty. Try again.");
        }
    }

    /** Read an integer in [min, max] (inclusive). */
    public static int readInt(int min, int max) {
        while (true) {
            System.out.printf("  Enter choice (%d-%d): ", min, max);
            String raw = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(raw);
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) { }
            System.out.printf("  ✖  Please enter a number between %d and %d.%n", min, max);
        }
    }

    /** Print a section header. */
    public static void header(String title) {
        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.printf( "│  %-43s│%n", title);
        System.out.println("└─────────────────────────────────────────────┘");
    }

    /** Ask a yes/no question — returns true for yes. */
    public static boolean confirm(String question) {
        while (true) {
            System.out.print(question + " (y/n): ");
            String ans = sc.nextLine().trim().toLowerCase();
            if (ans.equals("y") || ans.equals("yes")) return true;
            if (ans.equals("n") || ans.equals("no"))  return false;
            System.out.println("  ✖  Please enter y or n.");
        }
    }
}
