package com.github.cyberryan1.cybercore;

import org.bukkit.plugin.java.JavaPlugin;

public final class CyberCore {

    private static JavaPlugin pl;

    public static void setPlugin( JavaPlugin plugin ) {
        pl = plugin;
    }

    public static JavaPlugin getPlugin() { return pl; }
}
