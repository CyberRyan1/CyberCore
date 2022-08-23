package com.github.cyberryan1.cybercore.spigot.gui;

import com.github.cyberryan1.cybercore.spigot.gui.events.GuiClickEvent;
import com.github.cyberryan1.cybercore.spigot.utils.CyberItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an item in the {@link Gui} class. Contains information about
 * the item, the slot in the GUI, and the lambda statement (if any) to run when
 * this item is clicked by the player.
 *
 * @author CyberRyan1
 */
public class GuiItem {

    private ItemStack item;
    private int slot;
    private GuiClickEvent clickEvent = null;

    public GuiItem( Material material, String name, int slot ) {
        this.item = CyberItemUtils.createItem( material, name );
        this.slot = slot;
    }

    public GuiItem( Material material, String name, int slot, GuiClickEvent clickEvent ) {
        this.item = CyberItemUtils.createItem( material, name );
        this.slot = slot;
        this.clickEvent = clickEvent;
    }

    public GuiItem( ItemStack item, int slot ) {
        this.item = item;
        this.slot = slot;
    }

    public GuiItem( ItemStack item, int slot, GuiClickEvent clickEvent ) {
        this.item = item;
        this.slot = slot;
        this.clickEvent = clickEvent;
    }

    /**
     * Sets the {@link ItemStack} for this item
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
        this.item = CyberItemUtils.setItemName( new ItemStack( material ), name );
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
     * @param clickEvent New lambda statement through {@link GuiClickEvent}
     */
    public void setExecuteOnClick( GuiClickEvent clickEvent ) {
        this.clickEvent = clickEvent;
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
     * @return {@link GuiClickEvent} ran when clicked
     */
    public GuiClickEvent getClickEvent() { return clickEvent; }

    /**
     * Returns if the item currently runs some other code when it is clicked or if it will do nothing
     * @return true if it will run something when clicked, false if not
     */
    public boolean isExecutable() {
        return clickEvent != null;
    }

    /**
     * Runs the lambda statement through the {@link GuiClickEvent}
     * @throws NullPointerException if the item has no lambda statements to run
     */
    public void execute() {
        // Passes a copy of this item to the lambda statement
        if ( clickEvent != null ) { clickEvent.onClick( new GuiItem( this.item.clone(), this.slot, this.clickEvent ) ); }
        else { throw new NullPointerException( "Couldn't find any lambda statement to run when item is clicked" ); }
    }
}