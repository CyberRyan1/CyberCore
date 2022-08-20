package com.github.cyberryan1.cybercore.spigot;

import com.github.cyberryan1.cybercore.common.ICyberCore;
import com.github.cyberryan1.cybercore.spigot.models.color.ColorUtils;
import com.github.cyberryan1.cybercore.spigot.models.logger.LoggerUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class CyberCore implements ICyberCore {

    private static JavaPlugin plugin = null;

    public static final ColorUtils COLOR_UTILS = new ColorUtils();
    public static final LoggerUtils LOGGER_UTILS = new LoggerUtils();

    /**
     * Sets the plugin instance for the CyberCore class.
     * @param pl The plugin instance
     */
    public static void setPlugin( JavaPlugin pl ) {
        plugin = pl;
    }

    /**
     * @return The plugin instance
     */
    public static JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * @return The {@link ColorUtils} instance
     */
    public static ColorUtils getColorUtils() {
        return COLOR_UTILS;
    }

    /**
     * @return The {@link ColorUtils} instance
     */
    public static ColorUtils colorUtils() {
        return COLOR_UTILS;
    }

    /**
     * @return The {@link LoggerUtils} instance
     */
    public static LoggerUtils getLoggerUtils() {
        return LOGGER_UTILS;
    }

    /**
     * @return The {@link LoggerUtils} instance
     */
    public static LoggerUtils loggerUtils() {
        return LOGGER_UTILS;
    }
}