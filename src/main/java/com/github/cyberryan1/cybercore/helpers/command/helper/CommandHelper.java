package com.github.cyberryan1.cybercore.helpers.command.helper;

import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.github.cyberryan1.cybercore.utils.VaultUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper {
    protected String name;
    protected String permission;
    protected String permissionMsg;
    protected String usage;
    protected boolean demandPlayer = false;
    protected boolean demandConsole = false;
    protected boolean demandPermission = false;
    protected boolean isAsync = false;
    protected int minArgs = 0;
    protected List<Integer> onlinePlayerArgs = new ArrayList<>();
    protected List<Integer> offlinePlayerArgs = new ArrayList<>();
    protected List<Integer> integerArgs = new ArrayList<>();
    protected List<Integer> doubleArgs = new ArrayList<>();
    protected boolean tabcompleteEnabled = true;

    /**
     * Whether the sender has permissions or not for the command
     * @param sender the sender of the command
     * @return true if the sender has permissions or if the permission variable is null, false otherwise
     */
    public boolean permissionsAllowed( CommandSender sender ) {
        if ( permission == null ) { return true; }
        return VaultUtils.hasPerms( sender, this.permission );
    }

    /**
     * Whether the sender has permissions or not for the command
     * @param sender the sender of the command
     * @return true if the sender has permissions or if the permission variable is null, false otherwise
     */
    public boolean permsAllowed( CommandSender sender ) {
        return permissionsAllowed( sender );
    }

    /**
     * Sends the permission message to the sender
     * @param sender the sender of the command
     */
    public void sendPermissionMsg( CommandSender sender ) {
        CoreUtils.sendMessage( sender, this.permissionMsg );
    }

    /**
     * Sends the usage message to the sender
     * @param sender the sender of the command
     */
    public void sendPermMsg( CommandSender sender ) {
        sendPermissionMsg( sender );
    }

    /**
     * Sends the usage message to the sender
     * @param sender the sender of the command
     */
    public void sendUsage( CommandSender sender ) {
        CoreUtils.sendMessage( sender, this.usage );
    }

    /**
     * @return a list of all online player names
     */
    public List<String> getOnlinePlayerNames() {
        List<String> list = new ArrayList<>();
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            list.add( player.getName() );
        }
        return list;
    }

    /**
     * @param input the input to match
     * @return a list of all online player names that starts with the input
     */
    public List<String> matchOnlinePlayers( String input ) {
        List<String> toReturn = new ArrayList<>();
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            if ( player.getName().toLowerCase().startsWith( input.toLowerCase() ) ) {
                toReturn.add( player.getName() );
            }
        }
        return toReturn;
    }

    /**
     * Returns all strings from a list that starts with the input
     * @param list the list to match
     * @param input the input to match
     * @return a list of all strings from the list that starts with the input
    */
    public List<String> matchArgs( ArrayList<String> list, String input ) {
        for ( int index = list.size() - 1; index >= 0; index-- ) {
            if ( list.get( index ).toLowerCase().startsWith( input.toLowerCase() ) == false ) {
                list.remove( index );
            }
        }
        return list;
    }

    /**
     * Returns all strings from a list that starts with the input
     * @param list the list to match
     * @param input the input to match
     * @return a list of all strings from the list that starts with the input
     */
    public List<String> matchArgs( List<String> list, String input ) {
        return matchArgs( new ArrayList<>( list ), input );
    }

    /**
     * Combines the list into a string with each item separated by a space
     * @param args the list to combine
     * @return the combined string
     */
    public String combineArgs( String args[] ) {
        return String.join( " ", args );
    }

    /**
     * Combines the list into a string with each item separated by a space, starting at the index provided
     * @param args the list to combine
     * @param start the index to start at
     * @return the combined string
     */
    public String combineArgs( String args[], int start ) {
        StringBuilder sb = new StringBuilder();
        for ( int i = start; i < args.length; i++ ) {
            sb.append( args[ i ] );
            if ( i != args.length - 1 ) {
                sb.append( " " );
            }
        }
        return sb.toString();
    }

    /**
     * Returns an online player from the name provided
     * @param name The name of the player to get
     * @return The player if found, null otherwise
     */
    public Player getOnlinePlayer( String name ) {
        if ( CoreUtils.isValidUsername( name ) == false ) { return null; }
        return Bukkit.getPlayer( name );
    }

    /**
     * Returns an offline player from the name provided
     * @param name The name of the player to get
     * @return The player if found, null otherwise
     */
    public OfflinePlayer getOfflinePlayer( String name ) {
        if ( CoreUtils.isValidUsername( name ) == false ) { return null; }
        return Bukkit.getOfflinePlayer( name );
    }

    /**
     * Sends that the name provided isn't any player's name
     * @param sender The person to send this message to
     * @param name The attempted name
     */
    public void sendInvalidPlayerArg( CommandSender sender, String name ) {
        CoreUtils.sendMsg( sender, "&7Could not find a player with the named &b" + name );
    }

    //
    // Getters and setters
    //

    /**
     * @return The name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * @return The permission of the command
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @return The permission message for the command
     */
    public String getPermissionMsg() {
        return permissionMsg;
    }

    /**
     * @return The usage for the command
     */
    public String getUsage() {
        return usage;
    }

    /**
     * @return True if the command is only executable by players, false otherwise
     */
    public boolean isDemandPlayer() {
        return demandPlayer;
    }

    /**
     * @return True if the command is only executable by the console, false otherwise
     */
    public boolean isDemandConsole() {
        return demandConsole;
    }

    /**
     * @return True if the command is only executable by players with the permission, false otherwise
     */
    public boolean isDemandPermission() {
        return demandPermission;
    }

    /**
     * @return True if the command is asynchronous, false otherwise
     */
    public boolean isAsync() {
        return isAsync;
    }

    /**
     * @return The minimum number of arguments for the command to execute
     */
    public int getMinArgs() {
        return minArgs;
    }

    /**
     * @return The arguments that are required to be online players
     */
    public List<Integer> getOnlinePlayerArgs() {
        return onlinePlayerArgs;
    }

    /**
     * @return The arguments that are required to be offline players
     */
    public List<Integer> getOfflinePlayerArgs() {
        return offlinePlayerArgs;
    }

    /**
     * @return The arguments that are required to be integers
     */
    public List<Integer> getIntegerArgs() {
        return integerArgs;
    }

    /**
     * @return The arguments that are required to be doubles
     */
    public List<Integer> getDoubleArgs() {
        return doubleArgs;
    }

    /**
     * @param demandPlayer Whether the command is only executable by players (true) or not (false)
     */
    public void setDemandPlayer( boolean demandPlayer ) {
        this.demandPlayer = demandPlayer;
    }

    /**
     * Alias for the <b>setDemandPlayer()</b> method
     */
    public void demandPlayer( boolean demandPlayer ) { this.demandPlayer = demandPlayer; }

    /**
     * @param demandConsole Whether the command is only executable by the console (true) or not (false)
     */
    public void setDemandConsole( boolean demandConsole ) {
        this.demandConsole = demandConsole;
    }

    /**
     * Alias for the <b>setDemandConsole()</b> method
     */
    public void demandConsole( boolean demandConsole ) { this.demandConsole = demandConsole; }

    /**
     * @param demandPermission Whether the command is only executable by players with the permission (true) or not (false)
     */
    public void setDemandPermission( boolean demandPermission ) {
        this.demandPermission = demandPermission;
    }

    /**
     * Alias for the <b>setDemandPermission()</b> method
     */
    public void demandPermission( boolean demandPermission ) { this.demandPermission = demandPermission; }

    /**
     * @param async Whether the command is asynchronous (true) or not (false)
     */
    public void setAsync( boolean async ) {
        this.isAsync = async;
    }

    /**
     * @param minArgs The minimum number of arguments for the command to execute
    */
    public void setMinArgs( int minArgs ) {
        this.minArgs = minArgs;
    }

    /**
     * Requires the provided argument index to be an online player
     * @param argIndex The argument index that should require an online player
     */
    public void addOnlinePlayerArg( int argIndex ) {
        if ( argAlreadyUsed( argIndex ) ) {
            throw new IllegalArgumentException( "Argument " + argIndex + " is already demanded as a certain type of argument" );
        }
        this.onlinePlayerArgs.add( argIndex );
    }

    /**
     * Requires the provided argument index to be an offline player
     * @param argIndex The argument index that should require an offline player
     */
    public void addOfflinePlayerArg( int argIndex ) {
        if ( argAlreadyUsed( argIndex ) ) {
            throw new IllegalArgumentException( "Argument " + argIndex + " is already demanded as a certain type of argument" );
        }
        this.offlinePlayerArgs.add( argIndex );
    }

    /**
     * Requires the provided argument index to be an integer
     * @param argIndex The argument index that should require an integer
     */
    public void addIntegerArg( int argIndex ) {
        if ( argAlreadyUsed( argIndex ) ) {
            throw new IllegalArgumentException( "Argument " + argIndex + " is already demanded as a certain type of argument" );
        }
        this.integerArgs.add( argIndex );
    }

    /**
     * Requires the provided argument index to be a double
     * @param argIndex The argument index that should require a double
     */
    public void addDoubleArg( int argIndex ) {
        if ( argAlreadyUsed( argIndex ) ) {
            throw new IllegalArgumentException( "Argument " + argIndex + " is already demanded as a certain type of argument" );
        }
        this.doubleArgs.add( argIndex );
    }

    //
    // Private methods
    //

    /**
     * Checks if an argument index is already being demanded as a certain type (player, integer, etc)
     * @param argNumber The arg number to check
     * @return True if the arg is already in use, false if not
     */
    private boolean argAlreadyUsed( int argNumber ) {
        return this.integerArgs.contains( argNumber ) &&
                this.onlinePlayerArgs.contains( argNumber ) &&
                this.offlinePlayerArgs.contains( argNumber );
    }
}