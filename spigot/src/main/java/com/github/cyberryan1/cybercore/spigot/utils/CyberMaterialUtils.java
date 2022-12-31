package com.github.cyberryan1.cybercore.spigot.utils;

import org.bukkit.Material;

/**
 * Material utilities
 *
 * @author CyberRyan1
 */
public class CyberMaterialUtils {

    /**
     * @param material The material to check
     * @return Whether the material has durability
     */
    public static boolean hasDurability( Material material ) {
        return material.getMaxDurability() > 0;
    }

    /**
     * @param material The material to check
     * @return Whether the material is a tool
     */
    public static boolean isTool( Material material ) {
        return switch ( material ) {
            case WOODEN_AXE, WOODEN_HOE, WOODEN_PICKAXE, WOODEN_SHOVEL, WOODEN_SWORD,
                    STONE_AXE, STONE_HOE, STONE_PICKAXE, STONE_SHOVEL, STONE_SWORD,
                    IRON_AXE, IRON_HOE, IRON_PICKAXE, IRON_SHOVEL, IRON_SWORD,
                    GOLDEN_AXE, GOLDEN_HOE, GOLDEN_PICKAXE, GOLDEN_SHOVEL, GOLDEN_SWORD,
                    DIAMOND_AXE, DIAMOND_HOE, DIAMOND_PICKAXE, DIAMOND_SHOVEL, DIAMOND_SWORD,
                    NETHERITE_AXE, NETHERITE_HOE, NETHERITE_PICKAXE, NETHERITE_SHOVEL, NETHERITE_SWORD,
                    SHEARS, FLINT_AND_STEEL, BOW, CROSSBOW, FISHING_ROD, TRIDENT, SHIELD ->
                    true;
            default -> false;
        };
    }

    /**
     * @param material The material to check
     * @return Whether the material is a sword
     */
    public static boolean isArmor( Material material ) {
        return switch ( material ) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS,
                    CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS,
                    IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS,
                    GOLDEN_HELMET, GOLDEN_CHESTPLATE, GOLDEN_LEGGINGS, GOLDEN_BOOTS,
                    DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS,
                    NETHERITE_HELMET, NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS, NETHERITE_BOOTS ->
                    true;
            default -> false;
        };
    }
}