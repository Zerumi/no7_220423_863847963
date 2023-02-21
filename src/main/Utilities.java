package main;

import exceptions.WrongAmountOfArgumentsException;

public class Utilities {
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static Long handleUserInputID(String input) {
        if (!Utilities.isNumeric(input))
        {
            System.out.println("Provided argument id: \"" + input + "\" is not a number! Try again.");
            return null;
        }

        Long id = null;
        try {
            id = Long.valueOf(input);
        } catch (NumberFormatException e) {
            System.out.println("Provided argument: \"" + input + "\" is too large for ID field. Try again.");
        }
        return id;
    }

    public static void checkArgumentsOrThrow(int provided, int expected) throws WrongAmountOfArgumentsException
    {
        if (provided - 1 != expected) throw new WrongAmountOfArgumentsException("Provided " + (provided - 1) + " arguments, expected " + expected);
    }
}
