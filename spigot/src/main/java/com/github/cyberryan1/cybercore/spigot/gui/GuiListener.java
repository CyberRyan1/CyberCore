package com.github.cyberryan1.cybercore.spigot.gui;

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

/**
 * Is a listener for all the GUI events.
 * This shouldn't typically be used by anyone except the developer.
 *
 * @author CyberRyan1
 */
public class GuiListener implements Listener {

    private final Gui gui;
    private final Player player;

    /**
     * Creates a GUI listener with the GUI and player provided
     * @param gui GUI used
     * @param player Player interacting with the inventory
     */
    public GuiListener( Gui gui, Player player ) {
        this.gui = gui;
        this.player = player;
    }

    /**
     * Checks if this GUIListener is still actively used for a player's GUI
     * @return True if the listener is still in use, false if not
     */
    public boolean isListening() { return Gui.listeners.contains( this ); }

    /**
     * @return The player that is interacting with the GUI
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return The GUI that the player is interacting with
     */
    public Gui getGui() {
        return gui;
    }

    @EventHandler
    public void onInventoryClick( InventoryClickEvent event ) {
        if ( event.getInventory() == null || event.getClickedInventory() == null ) { return; }
        if ( event.getClickedInventory().getType() == InventoryType.PLAYER && gui.getAllowPlayerInvClicks() ) { return; }
        if ( gui.getItem( event.getSlot() ).isAllowItemMovement() && gui.getItem( event.getSlot() ).getClickEvent() == null ) { return; }
        if ( continueWithEvent( event.getInventory(), ( Player ) event.getWhoClicked() ) ) {
            event.setCancelled( true );
            if ( event.getClickedInventory() == null || event.getClickedInventory().getType() == InventoryType.PLAYER ) { return; }

            ItemStack item = event.getCurrentItem();
            if ( item == null || item.getType().isAir() ) { return; }

            int slot = event.getSlot();
            if ( gui.getItem( slot ).isExecutable() ) {
                gui.getItem( slot ).execute( event );
            }
        }
    }

    @EventHandler
    public void onInventoryDrag( InventoryDragEvent event ) {
        if ( event.getInventory() == null ) { return; }
        if ( event.getInventory().getType() == InventoryType.PLAYER && gui.getAllowPlayerInvClicks() ) { return; }
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
            Gui.listeners.remove( this );

            if ( gui.getCloseEvent() != null ) {
                gui.getCloseEvent().onClose( event.getInventory() );
            }
        }
    }

    private boolean continueWithEvent( Inventory eventInventory, Player eventPlayer ) {
        return isListening() && eventInventory.equals( gui.getInventory() ) && eventPlayer.getName().equals( player.getName() );
    }
}