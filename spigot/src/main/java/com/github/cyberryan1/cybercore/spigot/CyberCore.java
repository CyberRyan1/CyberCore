package com.github.cyberryan1.cybercore.spigot;

import com.github.cyberryan1.cybercore.common.ICyberCore;
import org.bukkit.plugin.java.JavaPlugin;

public class CyberCore implements ICyberCore {

    private static JavaPlugin plugin = null;

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
}