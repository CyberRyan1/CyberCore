package com.github.cyberryan1.cybercore.spigot.utils;

/**
 * General utils for spigot.
 *
 * @author CyberRyan1
 */
public final class CyberUtils {

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