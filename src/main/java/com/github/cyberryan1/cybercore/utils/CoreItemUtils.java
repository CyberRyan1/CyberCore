package com.github.cyberryan1.cybercore.utils;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
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

    /**
     * Adds an attribute to an item
     * @param item The item to add the attribute to
     * @param attributeName The name of the attribute to add
     * @param amount The amount of the attribute to add
     * @param attribute The {@link Attribute} to add
     * @param hideFlags Whether to add the {@link ItemFlag#HIDE_ATTRIBUTES} flag to the item
     * @return The item with the attribute added
     */
    public static ItemStack addAttributes( ItemStack item, String attributeName, double amount, Attribute attribute, boolean hideFlags ) {
        ItemMeta meta = item.getItemMeta();
        meta.addAttributeModifier(
                attribute,
                new AttributeModifier( attributeName, amount, AttributeModifier.Operation.ADD_NUMBER )
        );
        if ( hideFlags == true && meta.getItemFlags().contains( ItemFlag.HIDE_ATTRIBUTES ) == false ) {
            meta.addItemFlags( ItemFlag.HIDE_ATTRIBUTES );
        }
        item.setItemMeta( meta );
        return item;
    }

    /**
     * Removes an attribute from an item
     * @param item The item to remove the attribute from
     * @param attributeName The name of the attribute to add
     * @param amount The amount of the attribute to add
     * @param attribute The {@link Attribute} to remove
     * @param hideFlags Whether to add the {@link ItemFlag#HIDE_ATTRIBUTES} flag to the item
     * @return The item with the attribute removed
     */
    public static ItemStack removeAttributes( ItemStack item, String attributeName, double amount, Attribute attribute, boolean hideFlags ) {
        ItemMeta meta = item.getItemMeta();
        meta.removeAttributeModifier( attribute, new AttributeModifier( attributeName, amount, AttributeModifier.Operation.ADD_NUMBER ) );
        if ( hideFlags == true && meta.getItemFlags().contains( ItemFlag.HIDE_ATTRIBUTES ) == false ) {
            meta.addItemFlags( ItemFlag.HIDE_ATTRIBUTES );
        }
        item.setItemMeta( meta );
        return item;
    }

    /**
     * Adds a list of item flags to an item
     * @param item The item to add the flags to
     * @param flags A list of {@link ItemFlag} to add to the item
     * @return The item with the flags added
     */
    public static ItemStack addItemFlags( ItemStack item, ItemFlag ... flags ) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags( flags );
        item.setItemMeta( meta );
        return item;
    }

    /**
     * Removes a list of item flags from an item
     * @param item The item to remove the flags from
     * @param flags A list of {@link ItemFlag} to remove from the item
     * @return The item with the flags removed
     */
    public static ItemStack removeItemFlags( ItemStack item, ItemFlag ... flags ) {
        ItemMeta meta = item.getItemMeta();
        meta.removeItemFlags( flags );
        item.setItemMeta( meta );
        return item;
    }
}