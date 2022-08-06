package com.github.cyberryan1.cybercore.helpers.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Is a listener for all the GUI events
 * This shouldn't typically be used by someone using this API
 *
 * @author CyberRyan
 */
public class GUIListener implements Listener {

    private GUI gui;
    private Player player;

    /**
     * Creates a new GUIListener, and assumes all variables are null
     */
    public GUIListener() {
        this.gui = null;
        this.player = null;
    }

    /**
     * Creates a GUI listener with the GUI and player provided
     * @param gui GUI used
     * @param player Player interacting with the inventory
     */
    public GUIListener( GUI gui, Player player ) {
        this.gui = gui;
        this.player = player;
    }

    /**
     * Checks if this GUIListener is still actively used for a player's GUI
     * @return True if the listener is still in use, false if not
     */
    public boolean isListening() { return GUI.listeners.contains( this ); }

    @EventHandler
    public void onInventoryClick( InventoryClickEvent event ) {
        if ( continueWithEvent( event.getInventory(), ( Player ) event.getWhoClicked() ) ) {
            event.setCancelled( true );
            if ( event.getClickedInventory() == null || event.getClickedInventory().getType() == InventoryType.PLAYER ) { return; }

            ItemStack item = event.getCurrentItem();
            if ( item == null || item.getType().isAir() ) { return; }

            int slot = event.getSlot();
            if ( gui.getItem( slot ).isExecutable() ) {
                gui.getItem( slot ).execute();
            }
        }
    }

    @EventHandler
    public void onInventoryDrag( InventoryDragEvent event ) {
        if ( continueWithEvent( event.getInventory(), ( Player ) event.getWhoClicked() ) ) {
            event.setCancelled( true );
        }
    }

    @EventHandler
    public void onItemSwapEvent( PlayerSwapHandItemsEvent event ) {
        if ( isListening() && event.getPlayer().getName().equals( player.getName() ) ) {
            event.setCancelled( true );
        }
    }

    @EventHandler
    public void onInventoryClose( InventoryCloseEvent event ) {
        if ( continueWithEvent( event.getInventory(), ( Player ) event.getPlayer() ) ) {
            GUI.listeners.remove( this );
            if ( gui.getCloseAction() != null ) {
                gui.getCloseAction().run();
            }
        }
    }

    private boolean continueWithEvent( Inventory eventInventory, Player eventPlayer ) {
        return isListening() && eventInventory.equals( gui.getInventory() ) && eventPlayer.getName().equals( player.getName() );
    }
}