package com.github.cyberryan1.cybercore.spigot.utils.factories.items;

import com.github.cyberryan1.cybercore.spigot.utils.CyberItemUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class CyberItemFactory {

    private ItemStack item;
    public CyberItemFactory() {
        this.item = new ItemStack( Material.AIR );
    }

    public CyberItemFactory( Material material ) {
        this.item = new ItemStack( material );
    }

    public CyberItemFactory( ItemStack item ) {
        this.item = item;
    }

    public CyberItemFactory material( Material material ) {
        item.setType( material );
        return this;
    }

    public CyberItemFactory name( String name ) {
        item = CyberItemUtils.setItemName( item, name );
        return this;
    }

    public CyberItemFactory setLore( String... lore ) {
        item = CyberItemUtils.setItemLore( item, lore );
        return this;
    }

    public CyberItemFactory setLore( List<String> lore ) {
        item = CyberItemUtils.setItemLore( item, lore );
        return this;
    }

    public CyberItemFactory addLore( String... lore ) {
        item = CyberItemUtils.addItemLore( item, lore );
        return this;
    }

    public CyberItemFactory attribute( String attributeName, double amount, Attribute attribute ) {
        ItemMeta meta = item.getItemMeta();
        meta.addAttributeModifier( attribute, new AttributeModifier( attributeName, amount, AttributeModifier.Operation.ADD_NUMBER ) );
        item.setItemMeta( meta );
        return this;
    }

    public CyberItemFactory flags( ItemFlag ... flags ) {
        item = CyberItemUtils.addItemFlags( item, flags );
        return this;
    }

    public CyberItemFactory enchant( Enchantment enchant, int level ) {
        item.addUnsafeEnchantment( enchant, level );
        return this;
    }

    public CyberItemFactory hideEnchants() {
        item = CyberItemUtils.addItemFlags( item, ItemFlag.HIDE_ENCHANTS );
        return this;
    }

    public CyberItemFactory hideAttributes() {
        item = CyberItemUtils.addItemFlags( item, ItemFlag.HIDE_ATTRIBUTES );
        return this;
    }

    public CyberItemFactory amount( int amount ) {
        item.setAmount( amount );
        return this;
    }

    public CyberItemFactory durabilityRemaining( int durability ) {
        item.setDurability( (short) durability );
        return this;
    }

    public CyberItemFactory damage( int damage ) {
        item.setDurability( ( short ) ( item.getType().getMaxDurability() - damage ) );
        return this;
    }

    public <T, Z> CyberItemFactory persistentData( NamespacedKey key, PersistentDataType<T, Z> type, Z value ) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set( key, type, value );
        item.setItemMeta( meta );
        return this;
    }

    public ItemStack build() {
        return item;
    }
}