package com.github.cyberryan1.cybercore.spigot.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Command utilities
 *
 * @author CyberRyan1
 */
public class CyberCommandUtils {

    private static String defaultPermissionMsg = "&cInsufficient Permissions!";
    private static String invalidPlayerMsg = "&sCouldn't find a player with the name &p%s";
    private static String invalidIntegerMsg = "&sInvalid integer &p%s";
    private static String invalidDoubleMsg = "&sInvalid number &p%s";

    /**
     * Sets the message that is sent, by default, when a player doesn't have
     * permission to execute a command
     * @param msg the message to send
     */
    public static void setDefaultPermissionMsg( String msg ) {
        defaultPermissionMsg = msg;
    }

    /**
     * Gets the message that is sent, by default, when a player doesn't have
     * permission to execute a command
     * @return the message to send
     */
    public static String getDefaultPermissionMsg() {
        return defaultPermissionMsg;
    }

    /**
     * Sets the message that is sent, by default, when a name provided is invalid
     * @param msg the message to send (use %s for the player's name)
     */
    public static void setInvalidPlayerMsg( String msg ) {
        invalidPlayerMsg = msg;
    }

    /**
     * Gets the message that's sent, by default, when a name provided is invalid
     * @return The unformatted message (%s = the provided name)
     */
    public static String getInvalidPlayerMsg() {
        return invalidPlayerMsg;
    }

    /**
     * Sets the message that is sent, by default, when an integer is invalid
     * @param msg the message to send (use %s for the integer)
     */
    public static void setInvalidIntegerMsg( String msg ) {
        invalidIntegerMsg = msg;
    }

    /**
     * Gets the message that's sent, by default, when an integer is invalid
     * @return The unformatted message (%s = the integer)
     */
    public static String getInvalidIntegerMsg() {
        return invalidIntegerMsg;
    }

    /**
     * Sets the message that is sent, by default, when a double is invalid
     * @param msg the message to send (use %s for the double)
     */
    public static void setInvalidDoubleMsg( String msg ) {
        invalidDoubleMsg = msg;
    }

    /**
     * Gets the message that's sent, by default, when a double is invalid
     * @return The unformatted message (%s = the double)
     */
    public static String getInvalidDoubleMsg() {
        return invalidDoubleMsg;
    }

    /**
     * Combines the list into a string with each item separated by a space
     * @param args the list to combine
     * @return the combined string
     */
    public static String combineArgs( String args[] ) {
        return String.join( " ", args );
    }

    /**
     * Combines the list into a string with each item separated by a space, starting at the index provided
     * @param args the list to combine
     * @param start the index to start at
     * @return the combined string
     */
    public static String combineArgs( String args[], int start ) {
        StringBuilder sb = new StringBuilder();
        for ( int i = start; i < args.length; i++ ) {
            sb.append( args[ i ] );
            if ( i != args.length - 1 ) {
                sb.append( " " );
            }
        }
        return sb.toString();
    }

    /**
     * @return a list of all online player names
     */
    public static List<String> getOnlinePlayerNames() {
        List<String> list = new ArrayList<>();
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            list.add( player.getName() );
        }
        return list;
    }

    /**
     * @param input the input to match
     * @return a list of all online player names that starts with the input
     */
    public static List<String> matchOnlinePlayers( String input ) {
        List<String> toReturn = new ArrayList<>();
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            if ( player.getName().toLowerCase().startsWith( input.toLowerCase() ) ) {
                toReturn.add( player.getName() );
            }
        }
        return toReturn;
    }

    /**
     * Returns all strings from a list that starts with the input
     * @param list the list to match
     * @param input the input to match
     * @return a list of all strings from the list that starts with the input
     */
    public static List<String> matchArgs( ArrayList<String> list, String input ) {
        for ( int index = list.size() - 1; index >= 0; index-- ) {
            if ( list.get( index ).toLowerCase().startsWith( input.toLowerCase() ) == false ) {
                list.remove( index );
            }
        }
        return list;
    }

    /**
     * Returns all strings from a list that starts with the input
     * @param list the list to match
     * @param input the input to match
     * @return a list of all strings from the list that starts with the input
     */
    public static List<String> matchArgs( List<String> list, String input ) {
        return matchArgs( new ArrayList<>( list ), input );
    }
}