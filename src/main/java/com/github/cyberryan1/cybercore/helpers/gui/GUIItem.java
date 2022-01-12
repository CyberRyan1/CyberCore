package com.github.cyberryan1.cybercore.helpers.gui;

import com.github.cyberryan1.cybercore.utils.CoreGUIUtils;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GUIItem {

    private ItemStack item;
    private int slot = -1;
    private ExecuteOperator execute;

    public GUIItem( Material material, String name, int slot ) {
        this( material, name, slot, null );
    }

    public GUIItem( Material material, String name, int slot, ExecuteOperator executeWhenClicked ) {
        this.item = CoreGUIUtils.setItemName( new ItemStack( material ), CoreUtils.getColored( name ) );
        this.slot = slot;
        this.execute = executeWhenClicked;
    }

    public GUIItem( ItemStack item, int slot ) {
        this( item, slot, null );
    }

    public GUIItem( ItemStack item, int slot, ExecuteOperator executeWhenClicked ) {
        this.item = item;
        this.slot = slot;
        this.execute = executeWhenClicked;
    }

    public void setItem( ItemStack item ) {
        this.item = item;
    }

    public void setItem( Material material, String name ) {
        this.item = CoreGUIUtils.setItemName( new ItemStack( material ), CoreUtils.getColored( name ) );
    }

    public void setSlot( int slot ) {
        this.slot = slot;
    }

    public void setItemAmount( int amount ) {
        this.item.setAmount( amount );
    }

    public void setExecute( ExecuteOperator executeWhenClicked ) {
        this.execute = executeWhenClicked;
    }

    public ItemStack getItem() { return this.item; }

    public Material getType() { return this.item.getType(); }

    public int getSlot() { return slot; }

    public ExecuteOperator getExecute() { return execute; }

    public boolean isExecutable() { return execute != null; }

    public void execute() {
        execute.run();
    }
}