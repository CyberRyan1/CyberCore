package com.github.cyberryan1.cybercore.spigot.utils;

import com.github.cyberryan1.cybercore.spigot.CyberCore;

import java.util.logging.Level;

/**
 * Logging utilities
 *
 * @author CyberRyan1
 */
public final class CyberLogUtils {

    private static boolean debugLogging = false;

    /**
     * Logs a debug message, if the {@link #isLoggingDebug()} method is true
     * @param msg The message to log
     */
    public static void logDebug( String msg ) {
        if ( debugLogging ) {
            CyberCore.getPlugin().getLogger().log( Level.INFO, "[DEBUG] " + msg );
        }
    }

    /**
     * Logs an info message
     * @param msg The message to log
     */
    public static void logInfo( String msg ) {
        CyberCore.getPlugin().getLogger().log( Level.INFO, msg );
    }

    /**
     * Logs a warning message
     * @param msg The message to log
     */
    public static void logWarn( String msg ) {
        CyberCore.getPlugin().getLogger().log( Level.WARNING, msg );
    }

    /**
     * Logs an error message
     * @param msg The message to log
     */
    public static void logError( String msg ) {
        CyberCore.getPlugin().getLogger().log( Level.SEVERE, msg );
    }

    /**
     * Sets whether debug logs or enabled
     * @param b True to enable debug logs, false otherwise
     */
    public static void setDebugLogging( boolean b ) {
        debugLogging = b;
    }

    /**
     * @return True if debug logs are enabled, false otherwise
     */
    public static boolean isLoggingDebug() {
        return debugLogging;
    }
}