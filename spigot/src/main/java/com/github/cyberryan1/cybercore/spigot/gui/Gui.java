package com.github.cyberryan1.cybercore.spigot.gui;

import com.github.cyberryan1.cybercore.spigot.CyberCore;
import com.github.cyberryan1.cybercore.spigot.gui.events.GuiCloseEvent;
import com.github.cyberryan1.cybercore.spigot.utils.CyberColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    /**
     * @return A list of all active listeners
     */
    public static List<GuiListener> getActiveListeners() {
        return listeners;
    }

    private Inventory gui;
    private List<GuiItem> items = new ArrayList<>();
    private String name;
    private int size;
    private ItemStack backgroundItem;
    private GuiCloseEvent closeEvent;
    private boolean allowPlayerInvClicks = false;

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
        if ( slot >= ( size * 9 ) ) { throw new ArrayIndexOutOfBoundsException( "Cannot be greater than " + ( size * 9 ) + ", given " + slot ); }
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
     * @param allow True if the GUI allows player to click items in their inventory
     *              (not this inventory, but THEIR player inventory), false if not
     */
    public void setAllowPlayerInvClicks( boolean allow ) {
        this.allowPlayerInvClicks = allow;
    }

    /**
     * @return True if the GUI allows player to click items in their inventory
     * (not this inventory, but THEIR player inventory), false if not
     */
    public boolean getAllowPlayerInvClicks() {
        return allowPlayerInvClicks;
    }

    /**
     * Adds the item to the GUI
     * @param item The item to add
     * @throws ArrayIndexOutOfBoundsException If the slot is out of bounds
     */
    public void addItem( GuiItem item ) {
        if ( item.getSlot() >= ( size * 9 ) ) { throw new ArrayIndexOutOfBoundsException( "Cannot be greater than " + ( size * 9 ) + ", given " + item.getSlot() ); }
        items.set( item.getSlot(), item );
    }

    /**
     * Updates the slot to the item provided (should only be done for live updates)
     * @param newItem New item
     * @throws ArrayIndexOutOfBoundsException If the slot is out of bounds
     */
    public void updateItem( GuiItem newItem ) {
        if ( newItem.getSlot() >= ( size * 9 ) ) { throw new ArrayIndexOutOfBoundsException( "Cannot be greater than " + ( size * 9 ) + ", given " + newItem.getSlot() ); }
        items.set( newItem.getSlot(), newItem );
        gui.setItem( newItem.getSlot(), newItem.getItem() );
    }

    /**
     * If this GUI has not been formed yet (meaning
     * {@link #openInventory(Player)} has not been called yet),
     * then this method will add the item to the GUI (similar to
     * {@link #addItem(GuiItem)}). If the GUI has been formed,
     * then this method will update the item in the GUI (similar
     * to {@link #updateItem(GuiItem)}).
     * <br><br>
     * The intended purpose for this method is when you are making
     * updates to a GUI asynchronously.
     * @param item The item to add or update
     */
    public void addOrUpdateItem( GuiItem item ) {
        if ( item.getSlot() >= ( size * 9 ) ) { throw new ArrayIndexOutOfBoundsException( "Cannot be greater than " + ( size * 9 ) + ", given " + item.getSlot() ); }
        items.set( item.getSlot(), item );
        if ( gui != null ) {
            gui.setItem( item.getSlot(), item.getItem() );
        }
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
            items.add( new GuiItem( backgroundItem.clone(), i ) );
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