package com.github.cyberryan1.cybercore.common;

import com.github.cyberryan1.cybercore.common.models.logger.ICyberLogger;

/**
 * The ECyberCore class is an extendable class.
 * By default, it holds many useful classes and methods.
 * Whatever class extends from this class needs to add a <code>getPlugin()</code>
 * method or something of that nature, depending on the server type.
 *
 * @author CyberRyan1
 */
public class ECyberCore {

    private static ICyberLogger cyberLogger = null;

    /**
     * Sets the {@link ICyberLogger} for the plugin.
     * @param logger The {@link ICyberLogger} to use
     */
    public static void setCyberLogger( ICyberLogger logger ) {
        cyberLogger = logger;
    }

    /**
     * @return The {@link ICyberLogger} for the plugin
     */
    public static ICyberLogger getCyberLogger() {
        return cyberLogger;
    }

    /**
     * @return The {@link ICyberLogger} for the plugin
     */
    public static ICyberLogger getLogger() {
        return cyberLogger;
    }
}