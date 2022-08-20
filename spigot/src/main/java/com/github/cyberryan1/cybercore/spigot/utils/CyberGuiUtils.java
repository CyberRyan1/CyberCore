package com.github.cyberryan1.cybercore.spigot.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Utilities for GUIs
 *
 * @author CyberRyan1
 */
public final class CyberGuiUtils {

    /**
     * @return A gray stained glass pane with an empty name
     */
    public static ItemStack getBackgroundGlass() {
        return CyberItemUtils.createItem( Material.GRAY_STAINED_GLASS_PANE, "&7" );
    }
}