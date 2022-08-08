package com.github.cyberryan1.cybercore.helpers.command.helper;

import com.github.cyberryan1.cybercore.helpers.command.ArgType;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.github.cyberryan1.cybercore.utils.VaultUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected Map<Integer, ArgType> argTypes = new HashMap<>();
    protected boolean autoValidateArgs = true;
    protected boolean autoValidateSendMsg = true;
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
        CoreUtils.sendMsg( sender, "&sCould not find a player with the name &p" + name );
    }

    /**
     * Sends that the argument provided isn't an integer
     * @param sender The person to send this message to
     * @param arg The attempted argument
     */
    public void sendInvalidIntegerArg( CommandSender sender, String arg ) {
        CoreUtils.sendMsg( sender, "&sInvalid integer &p" + arg );
    }

    /**
     * Sends that the argument provided isn't a double
     * @param sender The person to send this message to
     * @param arg The attempted argument
     */
    public void sendInvalidDoubleArg( CommandSender sender, String arg ) {
        CoreUtils.sendMsg( sender, "&sInvalid number &p" + arg );
    }

    /**
     * Validates that the argument provided is of the correct type.
     * Unlike the other validateArgumentType method, this one will always send a message to the sender
     * if the argument is invalid and the {@link #willAutoValidateArgs()} method is true.
     * @param sender The command sender
     * @param arg The argument
     * @param index The index of the argument
     * @return True if the argument is valid or if the argument is not of a type, false otherwise
     */
    public boolean validateArgumentType( CommandSender sender, String arg, int index ) {
        return validateArgumentType( sender, arg, index, this.autoValidateSendMsg );
    }

    /**
     * Validates that the argument provided is of the correct type
     * @param sender The command sender
     * @param arg The argument
     * @param index The index of the argument
     * @param sendMsg Whether to send a message if the argument is invalid (true) or not (false)
     * @return True if the argument is valid or if the argument is not of a type, false otherwise
     */
    public boolean validateArgumentType( CommandSender sender, String arg, int index, boolean sendMsg ) {
        if ( this.argTypes.containsKey( index ) == false ) { return true; }

        if ( arg == null ) { return false; }
        switch ( this.argTypes.get( index ) ) {
            case ONLINE_PLAYER: {
                if ( this.getOnlinePlayer( arg ) == null ) {
                    if ( sendMsg ) { this.sendInvalidPlayerArg( sender, arg ); }
                    return false;
                }
                return true;
            }

            case OFFLINE_PLAYER: {
                if ( this.getOfflinePlayer( arg ) == null ) {
                    if ( sendMsg ) { this.sendInvalidPlayerArg( sender, arg ); }
                    return false;
                }
                return true;
            }

            case INTEGER: {
                try {
                    Integer.parseInt( arg );
                    return true;
                } catch ( NumberFormatException e ) {
                    if ( sendMsg ) { this.sendInvalidIntegerArg( sender, arg ); }
                    return false;
                }
            }

            case DOUBLE: {
                try {
                    Double.parseDouble( arg );
                    return true;
                } catch ( NumberFormatException e ) {
                    if ( sendMsg ) { this.sendInvalidDoubleArg( sender, arg ); }
                    return false;
                }
            }
        }

        return true;
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
     * @return The argument indexes (key) and the specified type (value) of the argument
     */
    public Map<Integer, ArgType> getArgTypes() {
        return argTypes;
    }

    /**
     * @param index The index of the argument to get the type of
     * @return The type of the argument at the specified index. Null if not found
     */
    public ArgType getArgType( int index ) {
        if ( argTypes.containsKey( index ) == false ) { return null; }
        return argTypes.get( index );
    }

    /**
     * Returns all indexes of arguments that have the specified type
     * @param type The type of the argument to get the indexes of
     * @return A {@link List<>} of indexes of arguments that have the specified type
     */
    public List<Integer> getArgIndexes( ArgType type ) {
        List<Integer> indexes = new ArrayList<Integer>();
        for ( Map.Entry<Integer, ArgType> entry : argTypes.entrySet() ) {
            if ( entry.getValue() == type ) {
                indexes.add( entry.getKey() );
            }
        }
        return indexes;
    }

    /**
     * @return True if the argument types are automatically validated, false otherwise
     */
    public boolean willAutoValidateArgs() {
        return autoValidateArgs;
    }

    /**
     * @return Whether a message is sent to the sender if the argument is invalid (true) or not (false)
     */
    public boolean willSendArgValidationMsg() {
        return autoValidateSendMsg;
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
     * Requires the provided argument index to be of the specified type
     * @param argIndex The argument index
     * @param type The type of the argumennt
     */
    public void setArgType( int argIndex, ArgType type ) {
        if ( argTypes.containsKey( argIndex ) ) {
            ArgType t = argTypes.get( argIndex );
            throw new IllegalArgumentException( "Argument " + argIndex + " is already demanded as a(n) " + t.name() );
        }
        this.argTypes.put( argIndex, type );
    }

    /**
     * @param bool Whether the arguments are automatically validated (true) or not (false)
     */
    public void setAutoValidateArgs( boolean bool ) {
        this.autoValidateArgs = bool;
    }

    /**
     * @param bool Whether a message is sent to the sender if the argument is invalid (true) or not (false)
     */
    public void setWillSendArgValidationMsg( boolean bool ) {
        this.autoValidateSendMsg = bool;
    }
}