package com.github.cyberryan1.cybercore;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @deprecated Finished moving to submodules
 */
public final class CyberCore {

    private static JavaPlugin pl;
    private static String primaryColor = "&b";
    private static String secondaryColor = "&7";

    public static void setPlugin( JavaPlugin plugin ) {
        pl = plugin;
    }

    public static JavaPlugin getPlugin() { return pl; }

    public static void setPrimaryColor( String color ) {
        primaryColor = color;
    }

    public static void setSecondaryColor( String color ) {
        secondaryColor = color;
    }

    public static String getPrimaryColor() {
        return primaryColor;
    }

    public static String getSecondaryColor() {
        return secondaryColor;
    }
}
