package com.github.cyberryan1.cybercore.helpers.command.helper;

import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.github.cyberryan1.cybercore.utils.VaultUtils;
import org.bukkit.Bukkit;
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
    protected int minArgs = 0;
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
     * @return The minimum number of arguments for the command to execute
     */
    public int getMinArgs() {
        return minArgs;
    }

    /**
     * @param demandPlayer Whether the command is only executable by players (true) or not (false)
     */
    public void setDemandPlayer( boolean demandPlayer ) {
        this.demandPlayer = demandPlayer;
    }

    /**
     * @param demandConsole Whether the command is only executable by the console (true) or not (false)
     */
    public void setDemandConsole( boolean demandConsole ) {
        this.demandConsole = demandConsole;
    }

    /**
     * @param demandPermission Whether the command is only executable by players with the permission (true) or not (false)
     */
    public void setDemandPermission( boolean demandPermission ) {
        this.demandPermission = demandPermission;
    }

    /**
     * @param minArgs The minimum number of arguments for the command to execute
    */
    public void setMinArgs( int minArgs ) {
        this.minArgs = minArgs;
    }
}