package com.github.cyberryan1.cybercore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CoreGUIUtils {

    public static ItemStack setItemLore( ItemStack item, ArrayList<String> lore ) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore( lore );
        item.setItemMeta( meta );
        return item;
    }

    public static ItemStack setItemLore( ItemStack item, String ... lore ) {
        ArrayList<String> loreLines = new ArrayList<>();
        for ( String str : lore ) { loreLines.add( CoreUtils.getColored( str ) ); }
        return setItemLore( item, loreLines );
    }

    public static ItemStack addItemLore( ItemStack item, String ... lore ) {
        ArrayList<String> loreLines = new ArrayList<>();
        if ( item.getItemMeta().getLore() != null ) {
            for ( String str : item.getItemMeta().getLore() ) { loreLines.add( str ); }
        }
        for ( String str : lore ) { loreLines.add( CoreUtils.getColored( str ) ); }
        return setItemLore( item, loreLines );
    }

    public static ItemStack setItemName( ItemStack item, String name ) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName( CoreUtils.getColored( name ) );
        item.setItemMeta( meta );
        return item;
    }

    public static ItemStack createItem( Material material, String name ) {
        return setItemName( new ItemStack( material ), name );
    }

    public static ItemStack getBackgroundGlass() {
        return setItemName( new ItemStack( Material.GRAY_STAINED_GLASS_PANE ),  "&7" );
    }
}