package com.github.cyberryan1.cybercore.common.logger;

/**
 * The ICyberLogger class is an interface.
 * This is how logging can be achieved in CyberCore.
 *
 * @author CyberRyan1
 */
public interface ICyberLogger {

    /**
     * Logs a debug message, if the {@link #isLoggingDebug()} method is true
     * @param msg The message to log
     */
    void logDebug( String msg );

    /**
     * Logs an info message
     * @param msg The message to log
     */
    void logInfo( String msg );

    /**
     * Logs a warning message
     * @param msg The message to log
     */
    void logWarn( String msg );

    /**
     * Logs an error message
     * @param msg The message to log
     */
    void logError( String msg );

    /**
     * Sets whether debug logs or enabled
     * @param b True to enable debug logs, false otherwise
     */
    void setDebugLogging( boolean b );

    /**
     * @return True if debug logs are enabled, false otherwise
     */
    boolean isLoggingDebug();
}