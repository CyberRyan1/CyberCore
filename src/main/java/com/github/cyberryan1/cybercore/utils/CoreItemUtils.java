package com.github.cyberryan1.cybercore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CoreItemUtils {

    /**
     * Sets the lore of an item
     * @param item The item to set the lore of
     * @param lore The lore to set the item to
     * @return The item with the lore set
     */
    public static ItemStack setItemLore( ItemStack item, List<String> lore ) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore( lore );
        item.setItemMeta( meta );
        return item;
    }

    /**
     * Sets the lore of an item
     * @param item The item to set the lore of
     * @param lore The lore to set the item to
     * @return The item with the lore set
     */
    public static ItemStack setItemLore( ItemStack item, String ... lore ) {
        List<String> loreLines = new ArrayList<>();
        for ( String str : lore ) { loreLines.add( CoreUtils.getColored( str ) ); }
        return setItemLore( item, loreLines );
    }

    /**
     * Adds the provided lines to the lore of an item
     * @param item The item to add the lore to
     * @param lore The lore to add to the item
     * @return The item with the lore added
     */
    public static ItemStack addItemLore( ItemStack item, String ... lore ) {
        List<String> loreLines = new ArrayList<>();
        if ( item.getItemMeta().getLore() != null ) {
            loreLines.addAll( item.getItemMeta().getLore() );
        }
        for ( String str : lore ) { loreLines.add( CoreUtils.getColored( str ) ); }
        return setItemLore( item, loreLines );
    }

    /**
     * Sets the name of an item
     * @param item The item to set the name of
     * @param name The name to set the item to
     * @return The item with the name set
     */
    public static ItemStack setItemName( ItemStack item, String name ) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName( CoreUtils.getColored( name ) );
        item.setItemMeta( meta );
        return item;
    }

    /**
     * Creates an item from the given material and name
     * @param material The material of the item
     * @param name The name of the item
     * @return The created item
     */
    public static ItemStack createItem( Material material, String name ) {
        return setItemName( new ItemStack( material ), name );
    }
}