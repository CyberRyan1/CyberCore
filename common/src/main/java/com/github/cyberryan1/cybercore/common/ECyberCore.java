package com.github.cyberryan1.cybercore.common;

import com.github.cyberryan1.cybercore.common.models.logger.ICyberLogger;
import com.github.cyberryan1.cybercore.common.models.messages.ICyberColor;
import com.github.cyberryan1.cybercore.common.models.messages.ICyberMessages;

/**
 * The ECyberCore class is an extendable class.
 * By default, it holds many useful classes and methods.
 * Whatever class extends from this class needs to add a <code>getPlugin()</code>
 * method or something of that nature, depending on the server type.
 *
 * @author CyberRyan1
 */
public class ECyberCore {

    protected static ICyberLogger cyberLogger = null;
    protected static ICyberColor cyberColor = null;
    protected static ICyberMessages cyberMessages = null;

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

    /**
     * @return The {@link ICyberColor} for the plugin
     */
    public static ICyberColor getCyberColor() {
        return cyberColor;
    }

    /**
     * @return The {@link ICyberColor} for the plugin
     */
    public static ICyberColor getColor() {
        return cyberColor;
    }

    /**
     * @return The {@link ICyberMessages} for the plugin
     */
    public static ICyberMessages getCyberMessages() {
        return cyberMessages;
    }

    /**
     * @return The {@link ICyberMessages} for the plugin
     */
    public static ICyberMessages getMessages() {
        return cyberMessages;
    }
}