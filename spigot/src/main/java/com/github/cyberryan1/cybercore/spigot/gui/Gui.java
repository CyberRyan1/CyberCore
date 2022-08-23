package com.github.cyberryan1.cybercore.spigot.gui;

import com.github.cyberryan1.cybercore.spigot.CyberCore;
import com.github.cyberryan1.cybercore.spigot.gui.events.GuiCloseEvent;
import com.github.cyberryan1.cybercore.spigot.utils.CyberColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

import java.util.ArrayList;
import java.util.List;

/**
 * This is what is mainly used by others to create GUIs, edit their contents, and open them to players
 *
 * @author CyberRyan1
 */
public class Gui {

    static List<GuiListener> listeners = new ArrayList<>();

    private Inventory gui;
    private List<GuiItem> items = new ArrayList<>();
    private String name;
    private int size;
    private ItemStack backgroundItem;
    private GuiCloseEvent closeEvent;

    /**
     * Creates a gui with a given name and size (rows)
     * @param name The name of the GUI
     * @param size The amount of rows within the GUI (max of 6 rows)
     */
    public Gui( String name, int size ) {
        this( name, size, new ItemStack( Material.AIR ) );
    }

    /**
     * Creates a gui with a given name, size (rows), and a background item
     * @param name The name of the GUI
     * @param size The amount of rows within the GUI (max of 6 rows)
     * @param backgroundItem The item used by default in all slots, unless replaced
     */
    public Gui( String name, int size, ItemStack backgroundItem ) {
        this.name = name;
        this.size = size;
        this.backgroundItem = backgroundItem;

        initializeItems();
    }

    /**
     * Gets the name of the GUI
     * @return The name of the GUI
     */
    public String getName() { return name; }

    /**
     * Gets the size (rows) of the GUI
     * @return The amount of rows in the GUI
     */
    public int getSize() { return size; }

    /**
     * Gets the item at a specified slot in the GUI
     * @param slot The slot in the GUI
     * @return Item at the passed slot
     */
    public GuiItem getItem( int slot ) {
        if ( slot > ( size * 9 - 1 ) ) { throw new ArrayIndexOutOfBoundsException(); }
        return items.get( slot );
    }

    /**
     * Gets the background item that is being used in the GUI
     * @return Background item used in the GUI
     */
    public ItemStack getBackgroundItem() { return backgroundItem; }

    /**
     * @return The {@link Inventory} of the GUI
     */
    public Inventory getInventory() { return gui; }

    /**
     * @return What's executed when the GUI is closed
     */
    public GuiCloseEvent getCloseEvent() { return closeEvent; }

    /**
     * Sets the name of the GUI
     * @param name New name of the GUI
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * Adds the item to the GUI
     * @param item The item to add
     * @throws ArrayIndexOutOfBoundsException If the slot is out of bounds
     */
    public void addItem( GuiItem item ) {
        if ( item.getSlot() >= ( size * 9 - 1 ) ) { throw new ArrayIndexOutOfBoundsException(); }
        items.set( item.getSlot(), item );
    }

    /**
     * Updates the slot to the item provided (should only be done for live updates)
     * @param newItem New item
     * @throws ArrayIndexOutOfBoundsException If the slot is out of bounds
     */
    public void updateItem( GuiItem newItem ) {
        if ( newItem.getSlot() >= ( size * 9 - 1 ) ) { throw new ArrayIndexOutOfBoundsException(); }
        items.set( newItem.getSlot(), newItem );
        gui.setItem( newItem.getSlot(), newItem.getItem() );
    }

    /**
     * Sets what is executed when the GUI is closed
     * @param closeEvent Action to execute when the GUI is closed
     */
    public void setCloseEvent( GuiCloseEvent closeEvent ) {
        this.closeEvent = closeEvent;
    }

    private void initializeItems() {
        for ( int i = 0; i < size * 9; i++ ) {
            items.add( new GuiItem( backgroundItem, i ) );
        }
    }

    /**
     * Opens the inventory to the player provided
     * @param player {@link Player} to open the inventory to
     */
    public void openInventory( Player player ) {
        gui = Bukkit.createInventory( null, 9 * size, CyberColorUtils.getColored( name ) );
        for ( int index = 0; index < 9 * size; index++ ) {
            gui.setItem( index, items.get( index ).getItem() );
        }

        player.openInventory( gui );

        GuiListener listener = new GuiListener( this, player );
        listeners.add( listener );
        CyberCore.getPlugin().getServer().getPluginManager().registerEvents( listener, CyberCore.getPlugin() );
    }
}

/**
 * Is a listener for all the GUI events.
 * This shouldn't typically be used by anyone except the developer.
 *
 * @author CyberRyan1
 */
class GuiListener implements Listener {

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
            Gui.listeners.remove( this );

            if ( gui.getCloseEvent() != null ) {
                gui.getCloseEvent().onClose();
            }
        }
    }

    private boolean continueWithEvent( Inventory eventInventory, Player eventPlayer ) {
        return isListening() && eventInventory.equals( gui.getInventory() ) && eventPlayer.getName().equals( player.getName() );
    }
}