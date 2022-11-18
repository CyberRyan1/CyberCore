package com.github.cyberryan1.cybercore.spigot.command.settings;

import com.github.cyberryan1.cybercore.spigot.utils.CyberMsgUtils;
import com.github.cyberryan1.cybercore.spigot.utils.CyberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base structure of all other command classes.
 * Used to validate argument types, send messages, etc.
 *
 * @author CyberRyan1
 */
public class BaseCommand extends CommandSettings {

    private Map<Integer, ArgType> argTypes = new HashMap<>();
    private Map<Integer, List<String>> stringArgOptions = new HashMap<>();
    private int minArgLength = 0;
    private boolean validateArgs = true;
    private boolean sendValidationMsg = true;

    public BaseCommand( String name, String permission, String permissionMsg, String usage ) {
        super( name, permission, permissionMsg, usage );
    }

    public BaseCommand( String name, String permission, String usage ) {
        super( name, permission, usage );
    }

    public BaseCommand( String name, String usage ) {
        super( name, usage );
    }

    //
    // Main Methods
    //

    /**
     * Validates that the argument provided is of the correct type
     * @param sender The command sender
     * @param arg The argument
     * @param index The index of the argument
     * @return True if the argument is valid or if the argument is not of a type, false otherwise
     */
    public final boolean validateArgument( CommandSender sender, String arg, int index ) {
        if ( this.validateArgs == false ) { return true; }
        if ( this.argTypes.containsKey( index ) == false ) { return true; }
        if ( arg == null ) { return false; }

        switch ( this.argTypes.get( index ) ) {
            case ONLINE_PLAYER -> {
                if ( CyberUtils.isValidUsername( arg ) == false || Bukkit.getPlayer( arg ) == null ) {
                    if ( this.sendValidationMsg ) { sendInvalidPlayerArg( sender, arg ); }
                    return false;
                }
                return true;
            }

            case OFFLINE_PLAYER -> {
                if ( CyberUtils.isValidUsername( arg ) == false || Bukkit.getOfflinePlayer( arg ) == null ) {
                    if ( this.sendValidationMsg ) { sendInvalidPlayerArg( sender, arg ); }
                    return false;
                }
                return true;
            }

            case INTEGER -> {
                try {
                    Integer.parseInt( arg );
                    return true;
                } catch ( NumberFormatException e ) {
                    if ( this.sendValidationMsg ) { sendInvalidIntegerArg( sender, arg ); }
                    return false;
                }
            }

            case DOUBLE -> {
                try {
                    Double.parseDouble( arg );
                    return true;
                } catch ( NumberFormatException e ) {
                    if ( this.sendValidationMsg ) { sendInvalidDoubleArg( sender, arg ); }
                    return false;
                }
            }

            case STRING -> {
                final List<String> options = this.stringArgOptions.get( index ).stream()
                        .map( String::toLowerCase )
                        .toList();
                if ( options.contains( arg.toLowerCase() ) == false ) {
                    if ( this.sendValidationMsg ) { sendUsage( sender ); }
                    return false;
                }
                return true;
            }
        }

        return true;
    }

    //
    // Senders
    //

    /**
     * Sends the permission message to the sender
     * @param sender the sender of the command
     */
    public void sendPermissionMsg( CommandSender sender ) {
        CyberMsgUtils.sendMessage( sender, super.getPermissionMsg() );
    }

    /**
     * Sends the permission message to the sender
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
        CyberMsgUtils.sendMessage( sender, super.getUsage() );
    }

    /**
     * Sends that the name provided isn't any player's name
     * @param sender The person to send this message to
     * @param name The attempted username
     */
    public void sendInvalidPlayerArg( CommandSender sender, String name ) {
        CyberMsgUtils.sendMsg( sender, String.format( super.getInvalidPlayerMsg(), name ) );
    }

    /**
     * Sends that the argument provided isn't an integer
     * @param sender The person to send this message to
     * @param arg The attempted argument
     */
    public void sendInvalidIntegerArg( CommandSender sender, String arg ) {
        CyberMsgUtils.sendMsg( sender, String.format( super.getInvalidIntegerMsg(), arg ) );
    }

    /**
     * Sends that the argument provided isn't a double
     * @param sender The person to send this message to
     * @param arg The attempted argument
     */
    public void sendInvalidDoubleArg( CommandSender sender, String arg ) {
        CyberMsgUtils.sendMsg( sender, String.format( super.getInvalidDoubleMsg(), arg ) );
    }

    //
    // Getters and Setters
    //

    /**
     * @return The {@link Map} of arg index to {@link ArgType}
     */
    public final Map<Integer, ArgType> getArgTypes() {
        return argTypes;
    }

    /**
     * @param index The index of the argument to get
     * @return The {@link ArgType} of the argument at the given index
     */
    public final ArgType getArgType( int index ) {
        if ( argTypes.containsKey( index ) == false ) { return null; }
        return argTypes.get( index );
    }

    /**
     * @param type The {@link ArgType} to search for
     * @return A {@link List} of all the indexes of the given {@link ArgType}
     */
    public final List<Integer> getArgIndexes( ArgType type ) {
        List<Integer> indexes = new ArrayList<>();
        for ( int index : argTypes.keySet() ) {
            if ( argTypes.get( index ).equals( type ) ) {
                indexes.add( index );
            }
        }
        return indexes;
    }

    /**
     * @return The {@link Map} of arg indexes that have been set
     * as {@link ArgType#STRING} to the list of options for that argument
     */
    public final Map<Integer, List<String>> getStringArgOptions() {
        return stringArgOptions;
    }

    /**
     * @param index The index of the argument to get
     * @return The {@link List} of strings that are valid
     *         for the argument at the given index
     */
    public final List<String> getStringArgOptions( int index ) {
        if ( argTypes.containsKey( index ) == false || argTypes.get( index ) != ArgType.STRING ) { return null; }
        return stringArgOptions.get( index );
    }

    /**
     * @return The minimum length of the arguments
     */
    public final int getMinArgLength() {
        return minArgLength;
    }

    /**
     * @return True if the arguments are validated, false otherwise
     */
    public final boolean isValidatingArgs() {
        return validateArgs;
    }

    /**
     * @return True if the sender is sent a message when the arguments are invalid, false otherwise
     */
    public final boolean isSendingValidationMsg() {
        return sendValidationMsg;
    }

    /**
     * @param index The index of the argument to set
     * @param type The {@link ArgType} to set the argument to
     */
    public final void setArgType( int index, ArgType type ) {
        argTypes.put( index, type );
    }

    /**
     * <b>Important Notes:</b>
     * <ul>
     *   <li>
     *       The index must be set to {@link ArgType#STRING} via
     *       the {@link #setArgType(int, ArgType)} method, otherwise
     *       nothing will happen
     *   </li>
     *   <li>
     *       If the argument provided is invalid when checking
     *       via the {@link #validateArgument(CommandSender, String, int)})
     *       method and the {@link #isSendingValidationMsg()} is true,
     *       then the usage of the command will be sent
     *   </li>
     * </ul>
     * @param index The index of the argument to set
     * @param options The {@link List} of strings that are valid for that argument <br> <p>
     */
    public final void setStringArgOptions( int index, List<String> options ) {
        if ( argTypes.containsKey( index ) == false || argTypes.get( index ) != ArgType.STRING ) { return; }
        stringArgOptions.put( index, options );
    }

    /**
     * @param length The minimum length of the arguments
     */
    public final void setMinArgLength( int length ) {
        minArgLength = length;
    }

    /**
     * @param validate True if the arguments are validated, false otherwise
     */
    public final void setValidateArgs( boolean validate ) {
        validateArgs = validate;
    }

    /**
     * @param send True if the sender is sent a message when the arguments are invalid, false otherwise
     */
    public final void setSendValidationMsg( boolean send ) {
        sendValidationMsg = send;
    }
}