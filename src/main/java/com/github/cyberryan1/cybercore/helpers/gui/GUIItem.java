package com.github.cyberryan1.cybercore.helpers.gui;

import com.github.cyberryan1.cybercore.helpers.gui.helpers.GUIClickNoArg;
import com.github.cyberryan1.cybercore.helpers.gui.helpers.GUIClickWithArg;
import com.github.cyberryan1.cybercore.utils.CoreItemUtils;
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
    private GUIClickNoArg executeNoArg = null;
    private GUIClickWithArg executeWithArg = null;

    /**
     * Creates an item with material, name, and the slot in the GUI
     * Assumes that nothing will happen when the item is clicked
     * @param material Type of the item
     * @param name Name of the item
     * @param slot Slot the item will be placed in the GUI
     */
    public GUIItem( Material material, String name, int slot ) {
        this.item = CoreItemUtils.createItem( material, name );
        this.slot = slot;
    }

    /**
     * Creates an item with material, name, and the slot in the GUI
     * Runs the lambda statement provided when clicked
     * @param material Type of the item
     * @param name Name of the item
     * @param slot Slot the item will be placed in the GUI
     * @param executeNoArgOnClick The {@link GUIClickNoArg} to run when the item is clicked
     */
    public GUIItem( Material material, String name, int slot, GUIClickNoArg executeNoArgOnClick ) {
        this.item = CoreItemUtils.setItemName( new ItemStack( material ), CoreUtils.getColored( name ) );
        this.slot = slot;
        this.executeNoArg = executeNoArgOnClick;
    }

    /**
     * Creates an item with an {@link ItemStack} and the slot in the GUI
     * Runs the lambda statement provided when clicked
     * @param material Type of the item
     * @param name Name of the item
     * @param slot Slot the item will be placed in the GUI
     * @param executeWithArgOnClick The {@link GUIClickWithArg} to run when the item is clicked
     */
    public GUIItem( Material material, String name, int slot, GUIClickWithArg executeWithArgOnClick ) {
        this.item = item;
        this.slot = slot;
        this.executeWithArg = executeWithArgOnClick;
    }

    /**
     * Creates an item with an {@link ItemStack} and the slot in the GUI
     * Assumes that nothing will happen when the item is clicked
     * @param item {@link ItemStack} used for the item
     * @param slot Slot the item will be placed in the GUI
     */
    public GUIItem( ItemStack item, int slot ) {
        this.item = item;
        this.slot = slot;
    }

    /**
     * Creates an item with an {@link ItemStack} and the slot in the GUI
     * Runs the lambda statement provided when clicked
     * @param item ItemStack used for the item
     * @param slot Slot the item will be placed in the GUI
     * @param executeNoArgOnClick The {@link GUIClickNoArg} to run when the item is clicked
     */
    public GUIItem( ItemStack item, int slot, GUIClickNoArg executeNoArgOnClick ) {
        this.item = item;
        this.slot = slot;
        this.executeNoArg = executeNoArgOnClick;
    }

    /**
     * Creates an item with an {@link ItemStack} and the slot in the GUI
     * Runs the lambda statement provided when clicked
     * @param item ItemStack used for the item
     * @param slot Slot the item will be placed in the GUI
     * @param executeWithArgOnClick The {@link GUIClickWithArg} to run when the item is clicked
     */
    public GUIItem( ItemStack item, int slot, GUIClickWithArg executeWithArgOnClick ) {
        this.item = item;
        this.slot = slot;
        this.executeWithArg = executeWithArgOnClick;
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
        this.item = CoreItemUtils.setItemName( new ItemStack( material ), CoreUtils.getColored( name ) );
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
     * Sets what lambda statement (no args) that is run when the item is clicked
     * @param executeNoArg New lambda statement through {@link GUIClickNoArg}
     */
    public void setExecuteNoArg( GUIClickNoArg executeNoArg ) {
        this.executeNoArg = executeNoArg;
    }

    /**
     * Sets what lambda statement (with args) that is run when the item is clicked
     * @param executeWithArg New lambda statement through {@link GUIClickWithArg}
     */
    public void setExecuteWithArg( GUIClickWithArg executeWithArg ) {
        this.executeWithArg = executeWithArg;
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
     * Returns the lambda statement (no arg) currently being used when the item is clicked
     * @return {@link GUIClickNoArg} ran when clicked
     */
    public GUIClickNoArg getExecuteNoArg() { return executeNoArg; }

    /**
     * Returns the lambda statement (with arg) currently being used when the item is clicked
     * @return {@link GUIClickWithArg} ran when clicked
     */
    public GUIClickWithArg getExecuteWithArg() { return executeWithArg; }

    /**
     * Returns if the item currently runs some other code when it is clicked or if it will do nothing
     * @return true if it will run something when clicked, false if not
     */
    public boolean isExecutable() { return executeNoArg != null; }

    /**
     * Runs the lambda statement through the {@link GUIClickNoArg} or the {@link GUIClickWithArg}
     * @throws NullPointerException if the item has no lambda statements to run
     */
    public void execute() {
        if ( executeNoArg != null ) { executeNoArg.run(); }
        // Passes a copy of this item to the lambda statement
        else if ( executeWithArg != null ) { executeWithArg.run( new GUIItem( this.item.clone(), this.slot, this.executeWithArg ) ); }
        else {
            throw new NullPointerException( "Couldn't find any lambda statement to run when item is clicked" );
        }
    }
}