package com.github.cyberryan1.cybercore.spigot;

import org.bukkit.plugin.java.JavaPlugin;

// TODO add a way to add cooldowns to commands/subcommands (w/ a way to bypass them as well)

public class CyberCore {

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