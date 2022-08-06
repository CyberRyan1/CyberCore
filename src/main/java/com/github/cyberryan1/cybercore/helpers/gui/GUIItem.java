package com.github.cyberryan1.cybercore.helpers.gui;

import com.github.cyberryan1.cybercore.utils.CoreGUIUtils;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an item in a GUI.
 *
 * @author CyberRyan
 */
public class GUIItem {

    private ItemStack item;
    private int slot = -1;
    private GUIClickNoArg execute;

    /**
     * Creates an item with material, name, and the slot in the GUI
     * Assumes that nothing will happen when the item is clicked
     * @param material Type of the item
     * @param name Name of the item
     * @param slot Slot the item will be placed in the GUI
     */
    public GUIItem( Material material, String name, int slot ) {
        this( material, name, slot, null );
    }

    /**
     * Creates an item with material, name, and the slot in the GUI
     * Runs the lambda statement provided when clicked
     * @param material Type of the item
     * @param name Name of the item
     * @param slot Slot the item will be placed in the GUI
     * @param executeWhenClicked Run when the item is clicked
     */
    public GUIItem( Material material, String name, int slot, GUIClickNoArg executeWhenClicked ) {
        this.item = CoreGUIUtils.setItemName( new ItemStack( material ), CoreUtils.getColored( name ) );
        this.slot = slot;
        this.execute = executeWhenClicked;
    }

    /**
     * Creates an item with an {@link ItemStack} and the slot in the GUI
     * Assumes that nothing will happen when the item is clicked
     * @param item {@link ItemStack} used for the item
     * @param slot Slot the item will be placed in the GUI
     */
    public GUIItem( ItemStack item, int slot ) {
        this( item, slot, null );
    }

    /**
     * Creates an item with an {@link org.bukkit.inventory.ItemStack} and the slot in the GUI
     * Runs the lambda statement provided when clicked
     * @param item ItemStack used for the item
     * @param slot Slot the item will be placed in the GUI
     * @param executeWhenClicked Run when the item is clicked
     */
    public GUIItem( ItemStack item, int slot, GUIClickNoArg executeWhenClicked ) {
        this.item = item;
        this.slot = slot;
        this.execute = executeWhenClicked;
    }

    /**
     * Sets the {@link org.bukkit.inventory.ItemStack} used
     * @param item New itemstack
     */
    public void setItem( ItemStack item ) {
        this.item = item;
    }

    /**
     * Sets the item using a {@link org.bukkit.Material} and name
     * @param material New material
     * @param name New name
     */
    public void setItem( Material material, String name ) {
        this.item = CoreGUIUtils.setItemName( new ItemStack( material ), CoreUtils.getColored( name ) );
    }

    /**
     * Sets a new slot in the GUI
     * @param slot New slot
     */
    public void setSlot( int slot ) {
        this.slot = slot;
    }

    /**
     * Sets the amount of the item in the GUI
     * @param amount New amount
     */
    public void setItemAmount( int amount ) {
        this.item.setAmount( amount );
    }

    /**
     * Sets what lambda statement that is run when the item is clicked
     * @param executeWhenClicked New lambda statement through {@link GUIClickNoArg}
     */
    public void setExecute( GUIClickNoArg executeWhenClicked ) {
        this.execute = executeWhenClicked;
    }

    /**
     * Returns the item this currently represents
     * @return {@link ItemStack} currently represented
     */
    public ItemStack getItem() { return this.item; }

    /**
     * Returns the type of the item this represents
     * @return {@link Material} used for this item
     */
    public Material getType() { return this.item.getType(); }

    /**
     * Returns the current slot this item will be placed in the GUI
     * @return slot in GUI
     */
    public int getSlot() { return slot; }

    /**
     * Returns the lambda statement currently being used when the item is clicked
     * @return {@link GUIClickNoArg} ran when clicked
     */
    public GUIClickNoArg getExecute() { return execute; }

    /**
     * Returns if the item currently runs some other code when it is clicked or if it will do nothing
     * @return true if it will run something when clicked, false if not
     */
    public boolean isExecutable() { return execute != null; }

    /**
     * Runs the lambda statement through the {@link GUIClickNoArg}
     */
    public void execute() {
        execute.run();
    }
}