package com.github.cyberryan1.cybercore.common.models;

/**
 * The CyberOfflinePlayer class is a final class.
 * It is used to simplify the tracking of offline player types between multiple different server types.
 *
 * @param <O> The type of the player
 * @author CyberRyan1
 */
public final class CyberOfflinePlayer<O> {

    private final O offlinePlayer;

    /**
     * Creates a new CyberOfflinePlayer object with the given offline player.
     * @param offlinePlayer The offline player
     */
    public CyberOfflinePlayer( O offlinePlayer ) {
        this.offlinePlayer = offlinePlayer;
    }

    /**
     * @return The offline player
     */
    public O getOfflinePlayer() {
        return this.offlinePlayer;
    }

    /**
     * @return The offline player
     */
    public O getPlayer() {
        return this.offlinePlayer;
    }
}