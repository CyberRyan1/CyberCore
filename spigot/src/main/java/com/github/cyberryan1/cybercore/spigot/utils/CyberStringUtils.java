package com.github.cyberryan1.cybercore.spigot.utils;

import java.util.Map;

/**
 * String utilities
 *
 * @author Ryan
 */
public class CyberStringUtils {

    /**
     * Converts a string to an integer, or returns the default if it fails
     * @param str The string to convert
     * @param def The default value to return if the conversion fails
     * @return The integer representation of the string, or the default if it fails
     */
    public static int toIntOrDefault( String str, int def ) {
        try {
            return Integer.parseInt( str );
        } catch ( NumberFormatException e ) {
            return def;
        }
    }

    /**
     * Converts a string to a double, or returns the default if it fails
     * @param str The string to convert
     * @param def The default value to return if the conversion fails
     * @return The double representation of the string, or the default if it fails
     */
    public static double toDoubleOrDefault( String str, double def ) {
        try {
            return Double.parseDouble( str );
        } catch ( NumberFormatException e ) {
            return def;
        }
    }

    /**
     * Checks if a string contains only numbers
     * @param str The string to check
     * @return True if the string is numeric, false otherwise
     */
    public static boolean isNumeric( String str ) {
        for ( char c : str.toCharArray() ) {
            if ( Character.isDigit( c ) == false ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a string contains only alphabetical characters
     * @param str The string to check
     * @return True if the string is alphabetical, false otherwise
     */
    public static boolean isAlphabetical( String str ) {
        for ( char c : str.toCharArray() ) {
            if ( Character.isAlphabetic( c ) == false ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Capitalizes the first letter of a string
     * @param str The string to capitalize
     * @return The capitalized string
     */
    public static String capitalizeFirstWord( String str ) {
        return str.substring( 0, 1 ).toUpperCase() + str.substring( 1 );
    }

    /**
     * Capitalizes the first letter of all words in a string
     * <i>(Generated via ChatGPT)</i>
     * @param str The string to capitalize
     * @return The capitalized string
     */
    public static String capitalizeAllWords( String str ) {
        str = str.replace( "_", " " ).toLowerCase();
        String split[] = str.split( " " );
        for ( int i = 0; i < split.length; i++ ) {
            split[i] = split[i].substring( 0, 1 ).toUpperCase() + split[i].substring( 1 );
        }
        return String.join( " ", split );
    }

    /**
     * Converts an integer to a roman numeral
     * <i>(Generated via ChatGPT)</i>
     * @param num The integer to convert
     * @return The roman numeral
     */
    public static String intToRomanNumeral( int num ) {
        int[] values = {
                1000, 900, 500, 400,
                100, 90, 50, 40,
                10, 9, 5, 4,
                1
        };

        String[] symbols = {
                "M", "CM", "D", "CD",
                "C", "XC", "L", "XL",
                "X", "IX", "V", "IV",
                "I"
        };

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                result.append(symbols[i]);
            }
        }

        return result.toString();
    }

    /**
     * Converts a roman numeral to an integer
     * <i>(Generated via ChatGPT)</i>
     * @param s The roman numeral to convert
     * @return The integer representation of the roman numeral
     */
    public static int romanToInt(String s) {
        final Map<Character, Integer> romanMap = Map.of(
                'I', 1,
                'V', 5,
                'X', 10,
                'L', 50,
                'C', 100,
                'D', 500,
                'M', 1000
        );

        int total = 0;

        for (int i = 0; i < s.length(); i++) {
            int current = romanMap.get(s.charAt(i));

            // Look ahead to next value (if it exists)
            if (i + 1 < s.length()) {
                int next = romanMap.get(s.charAt(i + 1));

                if (current < next) {
                    total -= current; // subtractive case
                } else {
                    total += current;
                }
            } else {
                total += current;
            }
        }

        return total;
    }
}