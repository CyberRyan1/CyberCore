package com.github.cyberryan1.cybercore.common.models;

/**
 * The CyberSender class is a final class.
 * It is used to simplify the tracking of player types between multiple different server types.
 *
 * @param <P> The type of the player
 * @author CyberRyan1
 */
public final class CyberPlayer<P> {

    private final P player;

    /**
     * Creates a new CyberPlayer object with the given player.
     * @param player The player
     */
    public CyberPlayer( P player ) {
        this.player = player;
    }

    /**
     * @return The player
     */
    public P getPlayer() {
        return this.player;
    }
}