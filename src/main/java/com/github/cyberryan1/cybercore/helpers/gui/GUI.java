package com.github.cyberryan1.cybercore.helpers.gui;

import com.github.cyberryan1.cybercore.CyberCore;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
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
 * @author CyberRyan
 */
public class GUI {

    protected static List<GUIListener> listeners = new ArrayList<>();

    private Inventory gui;
    private List<GUIItem> items = new ArrayList<>();
    private String name;
    private int size;
    private ItemStack backgroundItem;

    /**
     * Creates a gui with a given name and size (rows)
     * @param name The name of the GUI
     * @param size The amount of rows within the GUI (max of 6 rows)
     */
    public GUI( String name, int size ) {
        this( name, size, new ItemStack( Material.AIR ) );
    }

    /**
     * Creates a gui with a given name, size (rows), and a background item
     * @param name The name of the GUI
     * @param size The amount of rows within the GUI (max of 6 rows)
     * @param backgroundItem The item used by default in all slots, unless replaced
     */
    public GUI( String name, int size, ItemStack backgroundItem ) {
        this.name = name;
        this.size = size;
        this.backgroundItem = backgroundItem;

        initalizeItems();
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
    public GUIItem getItem( int slot ) {
        if ( slot >= ( size * 9 - 1 ) ) { throw new ArrayIndexOutOfBoundsException(); }
        return items.get( slot );
    }

    /**
     * Gets the background item that is being used in the GUI
     * @return Background item used in the GUI
     */
    public ItemStack getBackgroundItem() { return backgroundItem; }

    /**
     * Sets the name of the GUI
     * @param name New name of the GUI
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * Sets the slot to the item provided in this GUI
     * @param slot Slot of the new item
     * @param item New item
     */
    public void setItem( int slot, GUIItem item ) {
        if ( slot >= ( size * 9 - 1 ) ) { throw new ArrayIndexOutOfBoundsException(); }
        items.set( slot, item );
    }

    private void initalizeItems() {
        for ( int i = 0; i < size * 9; i++ ) {
            items.add( new GUIItem( backgroundItem, i ) );
        }
    }

    /**
     * Creates the inventory for use. Must be done before running the openInventory() method
     */
    public void createInventory() {
        gui = Bukkit.createInventory( null, 9 * size, CoreUtils.getColored( name ) );
        for ( int index = 0; index < 9 * size; index++ ) {
            gui.setItem( index, items.get( index ).getItem() );
        }
    }

    /**
     * Opens the inventory to the player provided
     * @param player {@link Player} to open the inventory to
     */
    public void openInventory( Player player ) {
        player.openInventory( gui );
        GUIListener listener = new GUIListener( gui, player, items );
        listeners.add( listener );
        CyberCore.getPlugin().getServer().getPluginManager().registerEvents( listener, CyberCore.getPlugin() );
    }

    /**
     * Creates the inventory for use and then opens the inventory to the player provided
     * @param player {@link Player} to open the inventory to
     */
    public void createAndOpen( Player player ) {
        createInventory();
        openInventory( player );
    }
}