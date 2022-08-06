package com.github.cyberryan1.cybercore.utils;

import com.github.cyberryan1.cybercore.CyberCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.logging.Level;

public class CoreUtils {

    private static boolean logDebug = false;

    /**
     * Logs a message to the console with level INFO
     * @param msg The message to log
     */
    public static void logInfo( String msg ) { CyberCore.getPlugin().getLogger().log( Level.INFO, msg ); }

    /**
     * Logs a message to the console with level WARNING
     * @param msg The message to log
     */
    public static void logWarn( String msg ) { CyberCore.getPlugin().getLogger().log( Level.WARNING, msg ); }

    /**
     * Logs a message to the console with level SEVERE
     * @param msg The message to log
     */
    public static void logError( String msg ) { CyberCore.getPlugin().getLogger().log( Level.SEVERE, msg ); }

    /**
     * Logs a message to the console with level INFO if debug is enabled
     * @param msg The message to log
     */
    public static void logDebug( String msg ) {
        if ( logDebug ) { CyberCore.getPlugin().getLogger().log( Level.INFO, "[DEBUG] " + msg ); }
    }

    /**
     * Whether or not debug logs are enabled
     * @param debug True if debug logs are enabled, false otherwise
     */
    public static void setLogDebug( boolean debug ) { logDebug = debug; }

    /**
     * @return True if debug logs are enabled, false otherwise
     */
    public static boolean getLogDebug() { return logDebug; }

    /**
     * Gets the colored string of a given string
     * @param msg The string to color
     * @return The colored string
     */
    public static String getColored( String msg ) {
        msg = msg.replace( "&p", CyberCore.getPrimaryColor() ).replace( "&s", CyberCore.getSecondaryColor() );
        return ChatColor.translateAlternateColorCodes( '&', msg );
    }

    /**
     * Gets the colored string of a given string list
     * @param msgs The strings to color
     * @return The colored strings
     */
    public static String[] getColored( String ... msgs ) {
        String toReturn[] = new String[ msgs.length ];
        for ( int index = 0; index < msgs.length; index++ ) {
            toReturn[index] = getColored( msgs[index] );
        }
        return toReturn;
    }

    /**
     * Sends the {@link #getColored(String ...)} of a given string list to a {@link CommandSender}
     * @param entity The {@link CommandSender} to send the message to
     * @param msgs The strings to color
     */
    public static void sendMessage( CommandSender entity, String ... msgs ) {
        entity.sendMessage( getColored( msgs ) );
    }

    /**
     * Alias to the {@link #sendMessage(CommandSender, String...)} method
     * @param entity The {@link CommandSender} to send the message to
     * @param msgs The strings to color
     */
    public static void sendMsg( CommandSender entity, String ... msgs ) {
        sendMessage( entity, msgs );
    }

    /**
     * Sends the {@link #getColored(String ...)} of a given string list to a {@link CommandSender}
     * @param entity The {@link CommandSender} to send the message to
     * @param msgs The strings to color
     */
    public static void sendMessage( CommandSender entity, List<String> msgs ) {
        sendMessage( entity, msgs.toArray( new String[0] ) );
    }

    /**
     * Alias to the {@link #sendMessage(CommandSender, List)} method
     * @param entity The {@link CommandSender} to send the message to
     * @param msgs The strings to color
     */
    public static void sendMsg( CommandSender entity, List<String> msgs ) {
        sendMessage( entity, msgs );
    }

    /**
     * Removes all color from a given string
     * @param msg The string to remove color from
     * @return The string without color nor color codes
     */
    public static String reverseColor( String msg ) {
        final String COLOR_CODES[] = { "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&0",
                                        "&a", "&b", "&c", "&d", "&e", "&f", "&r", "&m", "&n", "&o", "&k", "&l" };

        for ( String str : COLOR_CODES ) {
            msg = msg.replaceAll( getColored( str ), str );
        }
        return msg;
    }

    /**
     * Alias to the {@link ChatColor#stripColor(String)} method, removing all color from a given string
     * @param msg The string to remove color from
     * @return The string without color
     */
    public static String removeColor( String msg ) {
        return ChatColor.stripColor( msg );
    }

    /**
     * Removes all color codes (&a, &b, etc.) from a given string
     * @param msg The string to remove color codes from
     * @return The string without color codes
     */
    public static String removeColorCodes( String msg ) {
        return removeColor( getColored( msg ) );
    }

    // Checks if a username is allowed by Minecraft
    // Useful so that time isn't wasted looking up a weird name

    /**
     * Checks if a username is allowed by Minecraft. Useful so that time isn't wasted looking up a weird name.
     * <i>(Allows periods for bedrock support)</i>
     * @param username The username to check
     * @return True if the username is allowed, false otherwise
     */
    public static boolean isValidUsername( String username ) {
        if ( username.length() < 3 || username.length() > 16 ) { return false; }
        if ( username.contains( " " ) ) { return false; }
        for ( char c : username.toCharArray() ) {
            if ( Character.isLetterOrDigit( c ) == false && c != '_' && c != '.' ) {
                return false;
            }
        }

        return true;
    }
}