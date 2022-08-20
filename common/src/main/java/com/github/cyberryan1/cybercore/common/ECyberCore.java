package com.github.cyberryan1.cybercore.common;

import com.github.cyberryan1.cybercore.common.logger.ICyberLogger;

/**
 * The ECyberCore class is an extendable class.
 * By default, it holds many useful classes and methods.
 * Whatever class extends from this class needs to add a <code>getPlugin()</code>
 * method or something of that nature, depending on the server type.
 *
 * @author CyberRyan1
 */
public class ECyberCore {

    private static String primaryColor = "&b";
    private static String secondaryColor = "&7";
    private static ICyberLogger cyberLogger = null;

    /**
     * Sets the primary color for the plugin.
     * This is what "&p" within strings will be replaced with.
     * @param color The primary color <i>(color code, like "&b")</i>
     */
    public static void setPrimaryColor( String color ) {
        primaryColor = color;
    }

    /**
     * Sets the secondary color for the plugin.
     * This is what "&s" within strings will be replaced with.
     * @param color The secondary color <i>(color code, like "&7")</i>
     */
    public static void setSecondaryColor( String color ) {
        secondaryColor = color;
    }

    /**
     * @return The primary color for the plugin <i>(color code, like "&b")</i>
     */
    public static String getPrimaryColor() {
        return primaryColor;
    }

    /**
     * @return The secondary color for the plugin <i>(color code, like "&7")</i>
     */
    public static String getSecondaryColor() {
        return secondaryColor;
    }

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