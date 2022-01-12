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

public class GUI {

    protected static List<GUIListener> listeners = new ArrayList<>();

    private Inventory gui;
    private List<GUIItem> items = new ArrayList<>();
    private String name;
    private int size;
    private ItemStack backgroundItem;

    public GUI( String name, int size ) {
        this( name, size, new ItemStack( Material.AIR ) );
    }

    public GUI( String name, int size, ItemStack backgroundItem ) {
        this.name = name;
        this.size = size;
        this.backgroundItem = backgroundItem;

        initalizeItems();
    }

    public String getName() { return name; }

    public int getSize() { return size; }

    public GUIItem getItem( int slot ) {
        if ( slot >= ( size * 9 - 1 ) ) { throw new ArrayIndexOutOfBoundsException(); }
        return items.get( slot );
    }

    public ItemStack getBackgroundItem() { return backgroundItem; }

    public void setName( String name ) {
        this.name = name;
    }

    public void setItem( int slot, GUIItem item ) {
        if ( slot >= ( size * 9 - 1 ) ) { throw new ArrayIndexOutOfBoundsException(); }
        items.set( slot, item );
    }

    private void initalizeItems() {
        for ( int i = 0; i < size * 9; i++ ) {
            items.add( new GUIItem( backgroundItem, i ) );
        }
    }

    public void createInventory() {
        gui = Bukkit.createInventory( null, 9 * size, CoreUtils.getColored( name ) );
        for ( int index = 0; index < 9 * size; index++ ) {
            gui.setItem( index, items.get( index ).getItem() );
        }
    }

    public void openInventory( Player player ) {
        player.openInventory( gui );
        GUIListener listener = new GUIListener( gui, player, items );
        listeners.add( listener );
        CyberCore.getPlugin().getServer().getPluginManager().registerEvents( listener, CyberCore.getPlugin() );
    }
}