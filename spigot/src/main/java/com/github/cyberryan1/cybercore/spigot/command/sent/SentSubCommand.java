package com.github.cyberryan1.cybercore.spigot.command.sent;

import com.github.cyberryan1.cybercore.spigot.utils.CyberMsgUtils;
import com.github.cyberryan1.cybercore.spigot.utils.CyberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Used to represent an executed subcommand, with the sender and arguments of the subcommand.
 * Allows for one to easily get the arguments of the executed subcommand into a specified
 * type.
 *
 * @author CyberRyan1
 */
public class SentSubCommand {

    private final String subcommand;
    private final CommandSender sender;
    private final String subcommandArgs[];

    public SentSubCommand( CommandSender sender, String subcommand, String subcommandArgs[] ) {
        this.sender = sender;
        this.subcommand = subcommand;
        this.subcommandArgs = subcommandArgs;
    }

    /**
     * @return The name of the subcommand that was executed
     */
    public String getSubcommand() {
        return subcommand;
    }

    /**
     * @return The {@link CommandSender} that executed the subcommand
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * @return The {@link Player} who executed the subcommand
     * @throws ClassCastException If the sender isn't a player
     */
    public Player getPlayer() {
        if ( sender instanceof Player ) { return ( Player ) sender; }
        throw new ClassCastException( "Sender is not a player" );
    }

    /**
     * @return The arguments that were provided to the subcommand <br>
     *              <b>IMPORTANT:</b> The index of each argument is
     *              relative to the index of the subcommand in the command.
     *              Example: if <code>/numbergen random 1 10</code> was executed,
     *              and the subcommand was "random", then the argument at index 0
     *              would be "1" and the argument at index 1 would be "10".
     */
    public String[] getSubcommandArgs() {
        return subcommandArgs;
    }

    /**
     * @param index The index of the argument to get. <br>
     *              <b>IMPORTANT:</b> The index of the argument is
     *              relative to the subcommand, not the command <br>
     *              <i>(see the comments at {@link #getSubcommandArgs()} for more information)</i>
     * @return The argument at the provided index
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public String getArg( int index ) {
        if ( index >= subcommandArgs.length ) { throw new IndexOutOfBoundsException( "Argument at index " + index + " doesn't exist" ); }
        return subcommandArgs[index];
    }

    /**
     * Combines the arguments starting at the provided index into a single string, separated by spaces.
     * @param startingIndex The index of the argument to start at (inclusive). <br>
     *                      <b>IMPORTANT:</b> The index of the argument is
     *                      relative to the subcommand, not the command <br>
     *                      <i>(see the comments at {@link #getSubcommandArgs()} for more information)</i>
     * @return The combined arguments
     */
    public String getCombinedArgs( int startingIndex ) {
        String argsToCombine[] = new String[ subcommandArgs.length - startingIndex ];
        for ( int i = startingIndex; i < subcommandArgs.length; i++ ) {
            argsToCombine[i - startingIndex] = subcommandArgs[i];
        }

        return String.join( " ", argsToCombine );
    }

    /**
     * Combines the arguments starting at the provided index and ending at the provided index into
     * a single string, separated by spaces. <br>
     * <b>IMPORTANT:</b> The index of the argument is
     * relative to the subcommand, not the command <br>
     * <i>(see the comments at {@link #getSubcommandArgs()} for more information)</i>
     * @param startingIndex The index of the argument to start at (inclusive)
     * @param endingIndex The index of the argument to end at (inclusive)
     * @return The combined arguments
     */
    public String getCombinedArgs( int startingIndex, int endingIndex ) {
        if ( startingIndex > endingIndex ) { throw new IllegalArgumentException( "Starting index cannot be greater than ending index" ); }
        String argsToCombine[] = new String[ endingIndex - startingIndex ];
        for ( int i = startingIndex; i <= endingIndex; i++ ) {
            argsToCombine[i - startingIndex] = subcommandArgs[i];
        }

        return String.join( " ", argsToCombine );
    }

    /**
     * Tries to convert the argument at the provided index to an {@link OfflinePlayer}
     * @param index The index of the argument to convert <br>
     *              <b>IMPORTANT:</b> The index of the argument is
     *              relative to the subcommand, not the command <br>
     *              <i>(see the comments at {@link #getSubcommandArgs()} for more information)</i>
     * @return The {@link OfflinePlayer} at the provided index
     * @throws IndexOutOfBoundsException If the index is out of bounds
     * @throws ClassCastException If the argument at the provided index isn't a valid username
     */
    public OfflinePlayer getOfflinePlayerAtArg( int index ) {
        if ( index >= subcommandArgs.length ) { throw new IndexOutOfBoundsException( "Argument at index " + index + " doesn't exist" ); }

        if ( CyberUtils.isValidUsername( subcommandArgs[index] ) ) {
            OfflinePlayer toReturn = Bukkit.getOfflinePlayer( getArg( index ) );
            if ( toReturn != null ) { return toReturn; }
        }
        throw new ClassCastException( "Argument at index " + index + " is not an offline player" );
    }

    /**
     * Tries to convert the argument at the provided index to a {@link Player}
     * @param index The index of the argument to convert <br>
     *              <b>IMPORTANT:</b> The index of the argument is
     *              relative to the subcommand, not the command <br>
     *              <i>(see the comments at {@link #getSubcommandArgs()} for more information)</i>
     * @return The {@link Player} at the provided index
     * @throws IndexOutOfBoundsException If the index is out of bounds
     * @throws ClassCastException If the argument at the provided index isn't a valid username
     */
    public Player getPlayerAtArg( int index ) {
        if ( index >= subcommandArgs.length ) { throw new IndexOutOfBoundsException( "Argument at index " + index + " doesn't exist" ); }

        if ( CyberUtils.isValidUsername( subcommandArgs[index] ) ) {
            Player toReturn = Bukkit.getPlayer( getArg( index ) );
            if ( toReturn != null ) { return toReturn; }
        }
        throw new ClassCastException( "Argument at index " + index + " is not an online player" );
    }

    /**
     * Tries to convert the argument at the provided index to an integer
     * @param index The index of the argument to convert <br>
     *              <b>IMPORTANT:</b> The index of the argument is
     *              relative to the subcommand, not the command <br>
     *              <i>(see the comments at {@link #getSubcommandArgs()} for more information)</i>
     * @return The integer at the provided index
     * @throws IndexOutOfBoundsException If the index is out of bounds
     * @throws ClassCastException If the argument at the provided index isn't a valid integer
     */
    public int getIntegerAtArg( int index ) {
        if ( index >= subcommandArgs.length ) { throw new IndexOutOfBoundsException( "Argument at index " + index + " doesn't exist" ); }

        try {
            return Integer.parseInt( subcommandArgs[index] );
        } catch ( NumberFormatException e ) {
            throw new ClassCastException( "Argument at index " + index + " is not an integer" );
        }
    }

    /**
     * Tries to convert the argument at the provided index to a double
     * @param index The index of the argument to convert <br>
     *              <b>IMPORTANT:</b> The index of the argument is
     *              relative to the subcommand, not the command <br>
     *              <i>(see the comments at {@link #getSubcommandArgs()} for more information)</i>
     * @return The double at the provided index
     * @throws IndexOutOfBoundsException If the index is out of bounds
     * @throws ClassCastException If the argument at the provided index isn't a valid double
     */
    public double getDoubleAtArg( int index ) {
        if ( index >= subcommandArgs.length ) { throw new IndexOutOfBoundsException( "Argument at index " + index + " doesn't exist" ); }

        try {
            return Double.parseDouble( subcommandArgs[index] );
        } catch ( NumberFormatException e ) {
            throw new ClassCastException( "Argument at index " + index + " is not a double" );
        }
    }
    /**
     * Sends a message to the sender of the command
     * @param msg The message to send
     */
    public void respond( String msg ) {
        CyberMsgUtils.sendMsg( sender, msg );
    }

    /**
     * Sends multiple messages to the sender of the command
     * @param msgs The messages to send
     */
    public void respond( String... msgs ) {
        CyberMsgUtils.sendMsg( sender, msgs );
    }

}