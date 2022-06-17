package com.github.cyberryan1.cybercore.utils;

import com.github.cyberryan1.cybercore.CyberCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.logging.Level;

public class CoreUtils {

    public static void logInfo( String msg ) { CyberCore.getPlugin().getLogger().log( Level.INFO, msg ); }

    public static void logWarn( String msg ) { CyberCore.getPlugin().getLogger().log( Level.WARNING, msg ); }

    public static void logError( String msg ) { CyberCore.getPlugin().getLogger().log( Level.SEVERE, msg ); }

    public static String getColored( String msg ) {
        return ChatColor.translateAlternateColorCodes( '&', msg );
    }

    public static String[] getColored( String ... msgs ) {
        String toReturn[] = new String[ msgs.length ];
        for ( int index = 0; index < msgs.length; index++ ) {
            toReturn[index] = ChatColor.translateAlternateColorCodes( '&', msgs[index] );
        }
        return toReturn;
    }

    public static void sendMessage( CommandSender entity, String ... msgs ) {
        entity.sendMessage( getColored( msgs ) );
    }

    public static void sendMsg( CommandSender entity, String ... msgs ) {
        sendMessage( entity, msgs );
    }

    public static void sendMessage( CommandSender entity, List<String> msgs ) {
        sendMessage( entity, msgs.toArray( new String[0] ) );
    }

    public static void sendMsg( CommandSender entity, List<String> msgs ) {
        sendMessage( entity, msgs );
    }

    public static String reverseColor( String msg ) {
        final String COLOR_CODES[] = { "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&0",
                                        "&a", "&b", "&c", "&d", "&e", "&f", "&r", "&m", "&n", "&o", "&k", "&l" };

        for ( String str : COLOR_CODES ) {
            msg = msg.replaceAll( getColored( str ), str );
        }
        return msg;
    }

    public static String removeColor( String msg ) {
        return ChatColor.stripColor( msg );
    }

    // Checks if a username is allowed by Minecraft
    // Useful so that time isn't wasted looking up a weird name
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