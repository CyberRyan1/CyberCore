package com.github.cyberryan1.cybercore.spigot.command.helpers;

import com.github.cyberryan1.cybercore.spigot.utils.CyberCommandUtils;
import com.github.cyberryan1.cybercore.spigot.utils.CyberVaultUtils;
import org.bukkit.command.CommandSender;

/**
 * Contains all the settings for a command. Contains constructors,
 * getters, setters, and checkers (but no chess (please laugh)) for the settings.
 *
 * @author CyberRyan1
 */
public class CommandSettings {

    private String name;
    private String permission = null;
    private String permissionMsg = null;
    private String usage;

    private String invalidPlayerMsg = null;
    private String invalidIntegerMsg = null;
    private String invalidDoubleMsg = null;

    private boolean demandPlayer = false;
    private boolean demandConsole = false;
    private boolean demandPermission = false;
    private boolean runAsync = false;

    private boolean tabCompleteEnabled = true;

    public CommandSettings( String name, String permission, String permissionMsg, String usage ) {
        this.name = name;
        this.permission = permission;
        this.permissionMsg = permissionMsg;
        this.usage = usage;
    }

    public CommandSettings( String name, String permission, String usage ) {
        this.name = name;
        this.permission = permission;
        this.usage = usage;
    }

    public CommandSettings( String name, String usage ) {
        this.name = name;
        this.usage = usage;
    }

    //
    // Checkers
    //

    /**
     * Whether the sender has permissions or not for the command
     * @param sender the sender of the command
     * @return true if the sender has permissions or if the permission variable is null, false otherwise
     */
    public boolean permissionsAllowed( CommandSender sender ) {
        if ( permission == null || permission.isBlank() ) { return true; }
        return CyberVaultUtils.hasPerms( sender, permission );
    }

    /**
     * Whether the sender has permissions or not for the command
     * @param sender the sender of the command
     * @return true if the sender has permissions or if the permission variable is null, false otherwise
     */
    public boolean permsAllowed( CommandSender sender ) {
        if ( permission == null || permission.isBlank() ) { return true; }
        return CyberVaultUtils.hasPerms( sender, permission );
    }

    //
    // Getters & Setters
    //

    /**
     * @return The name of the command
     */
    public final String getName() {
        return name;
    }

    /**
     * @return The permission of the command
     */
    public final String getPermission() {
        return permission;
    }

    /**
     * @return The permission message for the command. If not set,
     * {@link CyberCommandUtils#getDefaultPermissionMsg()} is returned instead.
     */
    public final String getPermissionMsg() {
        return permissionMsg == null ? CyberCommandUtils.getDefaultPermissionMsg() : permissionMsg;
    }

    /**
     * @return The usage for the command
     */
    public final String getUsage() {
        return usage;
    }

    /**
     * @return The invalid player message for the command. If not set,
     * {@link CyberCommandUtils#getInvalidPlayerMsg()} is returned instead.
     * Note that %s is where the attempted name should be inserted.
     */
    public final String getInvalidPlayerMsg() {
        return invalidPlayerMsg == null ? CyberCommandUtils.getInvalidPlayerMsg() : invalidPlayerMsg;
    }

    /**
     * @return The invalid integer message for the command. If not set,
     * {@link CyberCommandUtils#getInvalidIntegerMsg()} is returned instead.
     * Note that %s is where the invalid integer should be inserted.
     */
    public final String getInvalidIntegerMsg() {
        return invalidIntegerMsg == null ? CyberCommandUtils.getInvalidIntegerMsg() : invalidIntegerMsg;
    }

    /**
     * @return The invalid double message for the command. If not set,
     * {@link CyberCommandUtils#getInvalidDoubleMsg()} is returned instead.
     * Note that %s is where the invalid double should be inserted.
     */
    public final String getInvalidDoubleMsg() {
        return invalidDoubleMsg == null ? CyberCommandUtils.getInvalidDoubleMsg() : invalidDoubleMsg;
    }

    /**
     * @return True if the command is only executable by players, false otherwise
     */
    public final boolean isDemandPlayer() {
        return demandPlayer;
    }

    /**
     * @return True if the command is only executable by the console, false otherwise
     */
    public final boolean isDemandConsole() {
        return demandConsole;
    }

    /**
     * @return True if the command is only executable by players with the permission, false otherwise
     */
    public final boolean isDemandPermission() {
        return demandPermission;
    }

    /**
     * @return True if the command is asynchronous, false otherwise
     */
    public final boolean runAsync() {
        return runAsync;
    }

    /**
     * @return True if the command has tab completion enabled, false otherwise
     */
    public final boolean isTabCompleteEnabled() {
        return tabCompleteEnabled;
    }

    /**
     * Sets the message that's sent to the player when they provide an invalid player.
     * Use %s to mark where the invalid player name should be inserted.
     * @param msg The message
     */
    public final void setInvalidPlayerMsg( String msg ) {
        invalidPlayerMsg = msg;
    }

    /**
     * Sets the message that's sent to the player when they provide an invalid integer.
     * Use %s to mark where the invalid integer should be inserted.
     * @param msg The message
     */
    public final void setInvalidIntegerMsg( String msg ) {
        invalidIntegerMsg = msg;
    }

    /**
     * Sets the message that's sent to the player when they provide an invalid double.
     * Use %s to mark where the invalid double should be inserted.
     * @param msg The message
     */
    public final void setInvalidDoubleMsg( String msg ) {
        invalidDoubleMsg = msg;
    }

    /**
     * @param demandPlayer Whether the command is only executable by players (true) or not (false)
     */
    public final void setDemandPlayer( boolean demandPlayer ) {
        this.demandPlayer = demandPlayer;
    }

    /**
     * Alias for the <b>setDemandPlayer()</b> method
     */
    public final void demandPlayer( boolean demandPlayer ) { this.demandPlayer = demandPlayer; }

    /**
     * @param demandConsole Whether the command is only executable by the console (true) or not (false)
     */
    public final void setDemandConsole( boolean demandConsole ) {
        this.demandConsole = demandConsole;
    }

    /**
     * Alias for the <b>setDemandConsole()</b> method
     */
    public final void demandConsole( boolean demandConsole ) { this.demandConsole = demandConsole; }

    /**
     * @param demandPermission Whether the command is only executable by players with the permission (true) or not (false)
     */
    public final void setDemandPermission( boolean demandPermission ) {
        this.demandPermission = demandPermission;
    }

    /**
     * Alias for the <b>setDemandPermission()</b> method
     */
    public final void demandPermission( boolean demandPermission ) { this.demandPermission = demandPermission; }

    /**
     * @param async Whether the command is asynchronous (true) or not (false)
     */
    public final void setRunAsync( boolean async ) {
        this.runAsync = async;
    }

    /**
     * @param tabCompleteEnabled Whether the command has tab completion enabled (true) or not (false)
     */
    public final void setTabCompleteEnabled( boolean tabCompleteEnabled ) {
        this.tabCompleteEnabled = tabCompleteEnabled;
    }
}