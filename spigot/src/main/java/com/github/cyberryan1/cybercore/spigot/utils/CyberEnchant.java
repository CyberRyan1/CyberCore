package com.github.cyberryan1.cybercore.spigot.utils;

import org.bukkit.enchantments.Enchantment;

/**
 * A way to convert between the in game enchantment names and the weird
 * enchantment objects that Spigot uses.
 *
 * @author CyberRyan1
 */
public enum CyberEnchant {
    PROTECTION ( "Protection", Enchantment.PROTECTION ),
    FIRE_PROTECTION ( "Fire Protection", Enchantment.FIRE_PROTECTION ),
    FEATHER_FALLING ( "Feather Falling", Enchantment.FEATHER_FALLING ),
    BLAST_PROTECTION ( "Blast Protection", Enchantment.BLAST_PROTECTION ),
    PROJECTILE_PROTECTION ( "Projectile Protection", Enchantment.PROJECTILE_PROTECTION ),
    RESPIRATION ( "Respiration", Enchantment.RESPIRATION ),
    AQUA_AFFINITY ( "Aqua Affinity", Enchantment.AQUA_AFFINITY ),
    THORNS ( "Thorns", Enchantment.THORNS ),
    DEPTH_STRIDER ( "Depth Strider", Enchantment.DEPTH_STRIDER ),
    FROST_WALKER ( "Frost Walker", Enchantment.FROST_WALKER ),
    BINDING_CURSE ( "Curse of Binding", Enchantment.BINDING_CURSE ),
    SHARPNESS ( "Sharpness", Enchantment.SHARPNESS ),
    SMITE ( "Smite", Enchantment.SMITE ),
    BANE_OF_ARTHROPODS ( "Bane of Arthropods", Enchantment.BANE_OF_ARTHROPODS ),
    KNOCKBACK ( "Knockback", Enchantment.KNOCKBACK ),
    FIRE_ASPECT ( "Fire Aspect", Enchantment.FIRE_ASPECT ),
    LOOTING ( "Looting", Enchantment.LOOTING ),
    SWEEPING_EDGE ( "Sweeping Edge", Enchantment.SWEEPING_EDGE ),
    EFFICIENCY ( "Efficiency", Enchantment.EFFICIENCY ),
    SILK_TOUCH ( "Silk Touch", Enchantment.SILK_TOUCH ),
    UNBREAKING ( "Unbreaking", Enchantment.UNBREAKING ),
    FORTUNE ( "Fortune", Enchantment.FORTUNE ),
    POWER ( "Power", Enchantment.POWER ),
    PUNCH ( "Punch", Enchantment.PUNCH ),
    FLAME ( "Flame", Enchantment.FLAME ),
    INFINITY ( "Infinity", Enchantment.INFINITY ),
    LUCK_OF_THE_SEA ( "Luck of the Sea", Enchantment.LUCK_OF_THE_SEA ),
    VANISHING_CURSE ( "Curse of Vanishing", Enchantment.VANISHING_CURSE ),
    LURE ( "Lure", Enchantment.LURE ),
    SOUL_SPEED ( "Soul Speed", Enchantment.SOUL_SPEED ),
    PIERCING ( "Piercing", Enchantment.PIERCING ),
    IMPALING ( "Impaling", Enchantment.IMPALING ),
    QUICK_CHARGE ( "Quick Charge", Enchantment.QUICK_CHARGE ),
    LOYALTY ( "Loyalty", Enchantment.LOYALTY ),
    RIPTIDE ( "Riptide", Enchantment.RIPTIDE ),
    MENDING ( "Mending", Enchantment.MENDING ),
    CHANNELING ( "Channeling", Enchantment.CHANNELING ),
    MULTISHOT ( "Multishot", Enchantment.MULTISHOT );
    
    private final String name;
    private final Enchantment enchantment;

    /**
     * @param name In game name of the enchantment
     * @param enchant The enchantment object
     */
    CyberEnchant( String name, Enchantment enchant ) {
        this.name = name;
        this.enchantment = enchant;
    }

    /**
     * @return The in game name of the enchantment
     */
    public String getName() {
        return name;
    }

    /**
     * @return The enchantment object
     */
    public Enchantment getEnchantment() {
        return enchantment;
    }

    public static CyberEnchant getEnchant( String name ) {
        for ( CyberEnchant enchant : CyberEnchant.values() ) {
            if ( enchant.getName().equalsIgnoreCase( name ) ) {
                return enchant;
            }
        }

        return null;
    }

    public static CyberEnchant getEnchant( Enchantment enchantment ) {
        for ( CyberEnchant enchant : CyberEnchant.values() ) {
            if ( enchant.getEnchantment().equals( enchantment ) ) {
                return enchant;
            }
        }

        return null;
    }
}