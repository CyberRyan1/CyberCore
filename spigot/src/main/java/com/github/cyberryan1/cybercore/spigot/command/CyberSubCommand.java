package com.github.cyberryan1.cybercore.spigot.command;

import com.github.cyberryan1.cybercore.spigot.CyberCore;
import com.github.cyberryan1.cybercore.spigot.command.sent.SentCommand;
import com.github.cyberryan1.cybercore.spigot.command.sent.SentSubCommand;
import com.github.cyberryan1.cybercore.spigot.command.settings.ArgType;
import com.github.cyberryan1.cybercore.spigot.command.settings.BaseCommand;
import com.github.cyberryan1.cybercore.spigot.utils.CyberCommandUtils;
import com.github.cyberryan1.cybercore.spigot.utils.CyberMsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Used to easily create a subcommand that can then be added to a {@link CyberSuperCommand}.
 * <br> <br>
 *
 * <b>IMPORTANT NOTE REGARDING ARGUMENT INDEXES:</b> <br>
 * The argument at index zero is not the subcommand name, rather it is the first argument
 * after the subcommand name. If the argument's length is zero, then there were no arguments
 * passed to the command after the subcommand name. <br> <br>
 *
 * Example: for the command "/sent send (player) (message)", the argument at index zero is
 * the player and the argument at index one is the message. <br> <br>
 *
 * Additionally, the {@link BaseCommand#setMinArgLength(int)} method is calculated based on
 * the number of arguments that are passed after the subcommand name. For example, for the
 * command "/sent send (player) (message)" where you want the player and message argument
 * to be required, you would set the minimum argument length to 2. <br>
 *
 * @author CyberRyan1
 */
public abstract class CyberSubCommand extends BaseCommand {

    /**
     * Creates a new CyberSubCommand with the provided arguments.
     *
     * @param name The name of the subcommand
     * @param permission The permission to use the subcommand (null for no permission)
     * @param permissionMsg The message to send to the player if they do not have the permission
     * @param usage The usage of the subcommand
     */
    public CyberSubCommand( String name, String permission, String permissionMsg, String usage ) {
        super( name, permission, permissionMsg, usage );
    }

    /**
     * Creates a new CyberSubCommand with the provided arguments. The permission
     * message is set to the default permission message provided by
     * {@link CyberCommandUtils#getDefaultPermissionMsg()}.
     *
     * @param name The name of the subcommand
     * @param permission The permission to use the subcommand (null for no permission)
     * @param usage The usage of the subcommand
     */
    public CyberSubCommand( String name, String permission, String usage ) {
        super( name, permission, usage );
    }

    /**
     * Creates a new CyberSubCommand with the provided arguments. The permission
     * message is set to the default permission message provided by
     * {@link CyberCommandUtils#getDefaultPermissionMsg()}. The permission
     * is assumed to be null, meaning that no permission is needed to execute
     * this command.
     *
     * @param name The name of the subcommand
     * @param usage The usage of the subcommand
     */
    public CyberSubCommand( String name, String usage ) {
        super( name, usage );
    }

    /**
     * This should be ignored by most developers
     */
    public final List<String> onTabComplete( SentCommand superCommand, String subcommandLabel, String subcommandArgs[] ) {
        if ( super.isTabCompleteEnabled() == false ) { return Collections.emptyList(); }

        final List<String> TO_RETURN = tabComplete( superCommand, new SentSubCommand( superCommand.getSender(), subcommandLabel, subcommandArgs ) );
        if ( TO_RETURN.isEmpty() == false ) { return TO_RETURN; }

        if ( super.getArgIndexes( ArgType.ONLINE_PLAYER ).contains( subcommandArgs.length - 1 )
                || super.getArgIndexes( ArgType.OFFLINE_PLAYER ).contains( subcommandArgs.length - 1 ) ) {
            if ( subcommandArgs[subcommandArgs.length - 1].isEmpty() ) { return CyberCommandUtils.getOnlinePlayerNames(); }
            else { return CyberCommandUtils.matchOnlinePlayers( subcommandArgs[subcommandArgs.length - 1] ); }
        }
        else if ( super.getArgIndexes( ArgType.STRING ).contains( subcommandArgs.length - 1 ) ) {
            if ( subcommandArgs[subcommandArgs.length - 1].isEmpty() ) { return super.getStringArgOptions( subcommandArgs.length -1 ); }
            else { return CyberCommandUtils.matchArgs( super.getStringArgOptions( subcommandArgs.length - 1 ), subcommandArgs[subcommandArgs.length - 1] ); }
        }

        return TO_RETURN;
    }

    /**
     * The tabComplete method is called when the player presses tab while typing a subcommand. <p>
     * Returning an empty list will do the following: <br>
     *      &emsp;- If the argument the command sender is currently typing has been set
     * <i>(via {@link BaseCommand#setArgType(int, ArgType)})</i> to either {@link ArgType#ONLINE_PLAYER}
     * or {@link ArgType#OFFLINE_PLAYER}), then a list of players will be sent to the command sender. <br>
     *      &emsp;- If the above yields no results, an empty list will be sent to the command sender. <br>
     * To override this behavior, return a list containing one string that is empty, and that will be returned instead.
     *
     * @param superCommand The {@link SentCommand} of the command that is being typed
     * @param subCommand The {@link SentSubCommand} of the subcommand that is being typed
     * @return A list of strings to return to the command sender
     */
    public abstract List<String> tabComplete( SentCommand superCommand, SentSubCommand subCommand );

    /**
     * This should be ignored by most developers
     */
    public final boolean onCommand( SentCommand superCommand, String subcommandLabel, String subcommandArgs[] ) {
        if ( super.isDemandPlayer() && ( superCommand.getSender() instanceof Player ) == false ) {
            CyberMsgUtils.sendMsg( superCommand.getSender(), "&sYou must be a player to use this command" );
            return true;
        }

        if ( super.isDemandConsole() && ( superCommand.getSender() instanceof ConsoleCommandSender ) == false ) {
            CyberMsgUtils.sendMsg( superCommand.getSender(), "&sYou must be console to use this command" );
            return true;
        }

        if ( super.isDemandPermission() && permissionsAllowed( superCommand.getSender() ) == false ) {
            sendPermissionMsg( superCommand.getSender() );
            return true;
        }

        if ( subcommandArgs.length < super.getMinArgLength() ) {
            sendUsage( superCommand.getSender() );
            return true;
        }

        if ( super.isValidatingArgs() ) {
            for ( int index = 0; index < subcommandArgs.length; index++ ) {
                if ( super.validateArgument( superCommand.getSender(), subcommandArgs[index], index ) == false ) { return true; }
            }
        }

        if ( super.runAsync() ) {
            Bukkit.getScheduler().runTaskAsynchronously( CyberCore.getPlugin(),
                    () -> execute( superCommand, new SentSubCommand( superCommand.getSender(), subcommandLabel, subcommandArgs ) )
            );
        }

        else {
            return execute( superCommand, new SentSubCommand( superCommand.getSender(), subcommandLabel, subcommandArgs ) );
        }

        return true;
    }

    /**
     * The execute method is called when the player executes a subcommand. All information
     * about the supercommand, subcommand, who executed it, the arguments, etc. are provided in
     * the {@link SentCommand} and {@link SentSubCommand} parameters<p>
     *
     * The value returned by this method will be returned to the
     * {@link CommandExecutor#onCommand(CommandSender, Command, String, String[])} method.
     *
     * @param superCommand The {@link SentCommand} of the command that was executed
     * @param subCommand The {@link SentSubCommand} of the subcommand that was executed
     * @return What to return to the {@link CommandExecutor#onCommand(CommandSender, Command, String, String[])} method
     *
     * @see CommandExecutor#onCommand(CommandSender, Command, String, String[])
     */
    public abstract boolean execute( SentCommand superCommand, SentSubCommand subCommand );
}
