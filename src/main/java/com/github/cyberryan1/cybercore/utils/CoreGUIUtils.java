package com.github.cyberryan1.cybercore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @deprecated Finished moving to submodules
 */
public class CoreGUIUtils {

    public static ItemStack getBackgroundGlass() {
        return CoreItemUtils.createItem( Material.GRAY_STAINED_GLASS_PANE, "&7" );
    }
}