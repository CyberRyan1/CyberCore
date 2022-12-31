package com.github.cyberryan1.cybercore.spigot.gui.events;

import com.github.cyberryan1.cybercore.spigot.utils.CyberItemUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickedGuiItem {

    private ItemStack item;
    private int slot;
    private final InventoryClickEvent event;

    public ClickedGuiItem( Material material, String name, int slot, InventoryClickEvent event ) {
        this.item = CyberItemUtils.createItem( material, name );
        this.slot = slot;
        this.event = event;
    }

    public ClickedGuiItem( ItemStack item, int slot, InventoryClickEvent event ) {
        this.item = item;
        this.slot = slot;
        this.event = event;
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
     * Returns the {@link InventoryClickEvent} that triggered this
     * @return {@link InventoryClickEvent} that triggered this
     */
    public InventoryClickEvent getEvent() { return event; }
}