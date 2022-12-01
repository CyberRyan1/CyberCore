package com.github.cyberryan1.cybercore.spigot.command.cooldown;

import com.github.cyberryan1.cybercore.spigot.utils.time.Timespan;

/**
 * Manages the settings for a cooldown of a command
 */
public class CooldownSettings {

    private Timespan cooldown = null;
    private String permissionBypass = null;
    private String cooldownMsg = null;
    private boolean resetOnRetry = false;

    /**
     * Sets the cooldown for the command
     * @param cooldown The {@link Timespan} of the cooldown
     */
    public void setCooldown( Timespan cooldown ) {
        this.cooldown = cooldown;
    }

    /**
     * Sets the permission to bypass the cooldown
     * @param permission The permission to bypass the cooldown, or null if none
     */
    public void setPermissionBypass( String permission ) {
        this.permissionBypass = permission;
    }

    /**
     * Sets the message to send when the command is on cooldown.
     * Use [REMAIN] to show the remaining time
     * @param msg The message to send, or null if none
     */
    public void setCooldownMsg( String msg ) {
        this.cooldownMsg = msg;
    }

    /**
     * Whether the cooldown should be reset when the command is executed and it is
     * on cooldown for the player. Defaults to false
     * @param reset Whether the cooldown should be reset
     */
    public void setResetOnRetry( boolean reset ) {
        this.resetOnRetry = reset;
    }

    /**
     * Returns the cooldown for the command
     * @return The {@link Timespan} of the cooldown, or null if none
     */
    public Timespan getCooldown() { return this.cooldown; }

    /**
     * Returns the permission to bypass the cooldown
     * @return The permission to bypass the cooldown, or null if none
     */
    public String getPermissionBypass() { return this.permissionBypass; }

    /**
     * Returns the message to send when the command is on cooldown
     * @return The message to send, or null if none
     */
    public String getCooldownMsg() { return this.cooldownMsg; }

    /**
     * Whether the cooldown should be reset when the command is executed and it is
     * on cooldown for the player. Defaults to false.
     * @return Whether the cooldown should be reset
     */
    public boolean resetOnRetry() { return this.resetOnRetry; }
}