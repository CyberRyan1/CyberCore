package com.github.cyberryan1.cybercore.spigot.command.cooldown;

import com.github.cyberryan1.cybercore.spigot.utils.time.Timespan;
import com.github.cyberryan1.cybercore.spigot.utils.time.Timestamp;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Helps manage a cooldown for a particular command.
 * <br><br>
 * <b>Important Note:</b> this will <u>not</u> keep track of cooldowns between plugin reloads. If
 * you want to keep track of cooldowns between reloads, you must implement the cooldown yourself.
 * This is intended to be used for commands that have short cooldowns (e.g. anything less than 5 minutes)
 */
public class CooldownManager {

    private final CooldownSettings settings;
    //                Player UUID
    //                      When the cooldown expires
    private final Map<UUID, Timestamp> cooldowns = new HashMap<>();

    /**
     * Creates a new instance based off the given {@link CooldownSettings}
     * @param settings The {@link CooldownSettings} to use
     */
    public CooldownManager( CooldownSettings settings ) {
        this.settings = settings;
    }

    /**
     * Adds a player to the cooldown
     * @param player The {@link OfflinePlayer} to add
     */
    public void addCooldown( OfflinePlayer player ) {
        Timestamp expiration = new Timestamp();
        expiration.add( this.settings.getCooldown() );
        this.cooldowns.put( player.getUniqueId(), expiration );
    }

    /**
     * Checks if a player is on cooldown.
     * @param player The {@link OfflinePlayer} to check
     * @return Whether the player is on cooldown (true) or not (false)
     */
    public boolean isOnCooldown( OfflinePlayer player ) {
        if ( this.cooldowns.containsKey( player.getUniqueId() ) == false ) { return false; }
        if ( this.cooldowns.get( player.getUniqueId() ).isBeforeNow() ) {
            this.cooldowns.remove( player.getUniqueId() );
            return false;
        }
        return true;
    }

    /**
     * Returns the timespan until the player's cooldown expires
     * @param player The {@link OfflinePlayer} to check
     * @return The {@link Timespan} until the player's cooldown expires, or null if the player is not on cooldown
     */
    public Timespan getTimeRemaining( OfflinePlayer player ) {
        if ( this.cooldowns.containsKey( player.getUniqueId() ) == false ) { return null; }
        final Timestamp cooldownExpiration = this.cooldowns.get( player.getUniqueId() );

        if ( cooldownExpiration.isBeforeNow() ) {
            this.cooldowns.remove( player.getUniqueId() );
            return null;
        }

        return cooldownExpiration.getTimeUntilNow();
    }

    /**
     * Removes a player from the cooldown
     * @param player The {@link OfflinePlayer} to remove
     */
    public void removeCooldown( OfflinePlayer player ) {
        this.cooldowns.remove( player.getUniqueId() );
    }

    /**
     * @return The {@link CooldownSettings} used by this
     */
    public CooldownSettings getSettings() {
        return this.settings;
    }
}