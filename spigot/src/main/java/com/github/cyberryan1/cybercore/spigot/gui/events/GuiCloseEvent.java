package com.github.cyberryan1.cybercore.spigot.gui.events;

import org.bukkit.inventory.Inventory;

public interface GuiCloseEvent {
    void onClose( Inventory finalInventory );
}