package com.github.cyberryan1.cybercore.spigot.command;

import com.github.cyberryan1.cybercore.spigot.CyberCore;
import com.github.cyberryan1.cybercore.spigot.command.cooldown.CooldownSettings;
import com.github.cyberryan1.cybercore.spigot.command.sent.SentCommand;
import com.github.cyberryan1.cybercore.spigot.command.sent.SentSubCommand;
import com.github.cyberryan1.cybercore.spigot.command.settings.ArgType;
import com.github.cyberryan1.cybercore.spigot.command.settings.BaseCommand;
import com.github.cyberryan1.cybercore.spigot.command.settings.CommandSettings;
import com.github.cyberryan1.cybercore.spigot.utils.CyberCommandUtils;
import com.github.cyberryan1.cybercore.spigot.utils.CyberMsgUtils;
import com.github.cyberryan1.cybercore.spigot.utils.CyberVaultUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to easily create a command that can be registered to the server. This command
 * also has support for subcommands.
 *
 * @author CyberRyan1
 */
public abstract class CyberSuperCommand extends BaseCommand implements CommandExecutor, TabCompleter {

    private final List<CyberSubCommand> subcommandList = new ArrayList<>();
    private boolean executeSubcommands = true;
    private boolean isOnlySubCommands = true;

    /**
     * Creates a new CyberSuperCommand with the provided arguments.<p>
     *
     * <i>Note: this is where you should call the {@link #register(boolean)} method.</i>
     *
     * @param name The name of the command
     * @param permission The permission to use the command (null for no permission)
     * @param permissionMsg The message to send to the player if they do not have the permission
     * @param usage The usage of the command <i>(Note: if any {@link CyberSubCommand} are added
     *              to this command, their usages will also be sent, unless you override
     *              the {@link #sendUsage(CommandSender)} method)</i>
     */
    public CyberSuperCommand( String name, String permission, String permissionMsg, String usage ) {
        super( name, permission, permissionMsg, usage );
    }

    /**
     * Creates a new CyberSuperCommand with the provided arguments. The permission
     * message is set to the default permission message provided by
     * {@link CyberCommandUtils#getDefaultPermissionMsg()}.<p>
     *
     * <i>Note: this is where you should call the {@link #register(boolean)} method.</i>
     *
     * @param name The name of the command
     * @param permission The permission to use the command (null for no permission)
     * @param usage The usage of the command <i>(Note: if any {@link CyberSubCommand} are added
     *              to this command, their usages will also be sent, unless you override
     *              the {@link #sendUsage(CommandSender)} method)</i>
     */
    public CyberSuperCommand( String name, String permission, String usage ) {
        super( name, permission, usage );
    }

    /**
     * Creates a new CyberSuperCommand with the provided arguments. The permission
     * message is set to the default permission message provided by
     * {@link CyberCommandUtils#getDefaultPermissionMsg()}. The permission
     * is assumed to be null, meaning that no permission is needed to execute
     * this command.<p>
     *
     * <i>Note: this is where you should call the {@link #register(boolean)} method.</i>
     *
     * @param name The name of the command
     * @param usage The usage of the command <i>(Note: if any {@link CyberSubCommand} are added
     *              to this command, their usages will also be sent, unless you override
     *              the {@link #sendUsage(CommandSender)} method)</i>
     */
    public CyberSuperCommand( String name, String usage ) {
        super( name, usage );
    }

    /**
     * This should be ignored by most developers
     */
    @Override
    public final List<String> onTabComplete( CommandSender sender, Command command, String label, String[] args ) {
        if ( super.isTabCompleteEnabled() == false ) { return Collections.emptyList(); }

        final List<String> TO_RETURN = tabComplete( new SentCommand( this, sender, args ) );
        if ( TO_RETURN.isEmpty() == false ) { return TO_RETURN; }

        if ( args.length == 1 ) {
            final List<String> availableSubcommandNames = getSubCommandsForSender( sender ).stream()
                    .map( CyberSubCommand::getName )
                    .collect( Collectors.toList() );

            if ( args[0].isBlank() ) { return availableSubcommandNames; }
            else { return CyberCommandUtils.matchArgs( availableSubcommandNames, args[0] ); }
        }

        final CyberSubCommand subCommand = getSubCommand( args[0] );
        if ( subCommand == null ) { return Collections.emptyList(); }
        final SentCommand superCommand = new SentCommand( this, sender, args );

        int subcommandIndex = -1;
        for ( int index = 0; index < args.length; index++ ) {
            if ( args[index].equalsIgnoreCase( subCommand.getName() ) ) {
                subcommandIndex = index;
                break;
            }
        }

        final String subcommandArgs[] = new String[args.length - subcommandIndex - 1];
        if ( subcommandArgs.length != 0 ) {
            for ( int index = subcommandIndex + 1; index < args.length; index++ ) {
                subcommandArgs[index - subcommandIndex - 1] = args[index];
            }
        }

        return subCommand.onTabComplete( superCommand, subCommand.getName(), subcommandArgs );
    }

    /**
     * The tabComplete method is called when the player presses tab while typing a command. <p>
     * Returning an empty list will do the following: <br>
     *
     * <ol>
     *     <li>
     *         If this command has any subcommands <i>(added by the {@link #addSubCommand(CyberSubCommand)} method)</i>:
     *         <ol type="I">
     *             <li>
     *                 If the current argument the player is typing is the first argument, the names of each
     *                 {@link CyberSubCommand} added will be returned<br>
     *             </li>
     *             <li>
     *                 If the current argument the player is typing is not the first argument, a {@link CyberSubCommand}
     *                 will attempted to be found with the name of the first argument, and return the list provided by
     *                 the {@link CyberSubCommand#tabComplete(SentCommand, SentSubCommand)} method
     *             </li>
     *         </ol>
     *     </li>
     *     <li>
     *         If the argument the command sender is currently typing has been set <i>(via
     *         {@link BaseCommand#setArgType(int, ArgType)})</i> to either {@link ArgType#ONLINE_PLAYER} or
     *         {@link ArgType#OFFLINE_PLAYER}), then a list of players will be sent to the command sender.
     *     </li>
     *     <li>
     *         If the above yields no results, an empty list will be sent to the command sender.
     *     </li>
     * </ol>
     *
     * To override this behavior, return a list containing one string that is empty, and that will be returned instead.
     *
     * @param command The {@link SentCommand} of the command that is being typed
     * @return A list of strings to return to the command sender
     */
    public abstract List<String> tabComplete( SentCommand command );

    /**
     * This should be ignored by most developers
     */
    @Override
    public final boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
        // Checking if the command sender is a player
        if ( super.isDemandPlayer() && ( sender instanceof Player ) == false ) {
            CyberMsgUtils.sendMsg( sender, "&sYou must be a player to use this command" );
            return true;
        }

        // Checking if the command sender is console
        if ( super.isDemandConsole() && ( sender instanceof ConsoleCommandSender ) == false ) {
            CyberMsgUtils.sendMsg( sender, "&sYou must be console to use this command" );
            return true;
        }

        // Checking if sender has permissions to run the command
        if ( super.isDemandPermission() && permissionsAllowed( sender ) == false ) {
            sendPermissionMsg( sender );
            return true;
        }

        // Argument length validation
        if ( args.length < super.getMinArgLength() ) {
            sendUsage( sender );
            return true;
        }

        // Argument validation
        if ( executeSubcommands && args.length >= 1 ) {
            final CyberSubCommand subCommand = getSubCommand( args[0] );
            if ( subCommand != null ) {
                final SentCommand superCommand = new SentCommand( this, sender, args );

                final String subcommandArgs[] = new String[args.length - 1];
                if ( subcommandArgs.length != 0 ) {
                    System.arraycopy( args, 1, subcommandArgs, 0, args.length - 1 );
                }

                return subCommand.onCommand( superCommand, subCommand.getName(), subcommandArgs );
            }
        }

        // Argument validation
        if ( super.isValidatingArgs() ) {
            for ( int index = 0; index < args.length; index++ ) {
                if ( super.validateArgument( sender, args[index], index ) == false ) { return true; }
            }
        }

        // Cooldown validation
        // Only works if the isDemandPlayer() method is true
        if ( super.isDemandPlayer() && super.getCooldownManager() != null ) {
            final Player player = ( Player ) sender;
            final CooldownSettings settings = super.getCooldownManager().getSettings();

            // Checking if the player is on cooldown
            if ( super.getCooldownManager().isOnCooldown( player ) ) {
                String msg = settings.getCooldownMsg();
                msg = msg.replace( "[REMAIN]", super.getCooldownManager().getTimeRemaining( player ).toString() );
                CyberMsgUtils.sendMsg( player, msg );
                return true;
            }

            // Otherwise adding the player to the cooldown, unless they are allowed to bypass the cooldown
            else if ( settings.getPermissionBypass() != null && CyberVaultUtils.hasPerms( player, settings.getPermissionBypass() ) == false ) {
                super.getCooldownManager().addCooldown( player );
            }
        }

        // Running the command asynchronously
        if ( super.runAsync() ) {
            Bukkit.getScheduler().runTaskAsynchronously( CyberCore.getPlugin(),
                    () -> execute( new SentCommand( this, sender, args ) )
            );
        }

        // Running the command
        else {
            return execute( new SentCommand( this, sender, args ) );
        }

        return true;
    }

    /**
     * The execute method is called when the player executes a command. All information
     * about the command, who executed it, the arguments, etc. are provided in
     * the {@link SentCommand} parameter. <p>
     *
     * If any subcommands are added to this command and the {@link #getExecuteSubCommands()} method
     * return true, then the corresponding subcommand (from the first argument) will be executed. If
     * no subcommand can be found or the {@link #getExecuteSubCommands()} method is false, then this
     * method will be called. <p>
     *
     * The value returned by this method will be returned to the
     * {@link CommandExecutor#onCommand(CommandSender, Command, String, String[])} method.
     *
     * @param command The {@link SentCommand} of the command that was executed
     * @return What to return to the {@link CommandExecutor#onCommand(CommandSender, Command, String, String[])} method
     *
     * @see CommandExecutor#onCommand(CommandSender, Command, String, String[])
     */
    public abstract boolean execute( SentCommand command );

    /**
     * Required to be executed for the command to work properly
     * @param includeTabComplete if the command should be tab completable
     */
    public final void register( boolean includeTabComplete ) {
        super.setTabCompleteEnabled( includeTabComplete );

        CyberCore.getPlugin().getCommand( super.getName() ).setExecutor( this );
        if ( includeTabComplete ) {
            CyberCore.getPlugin().getCommand( super.getName() ).setTabCompleter( this );
        }
    }

    /**
     * Sends the usage message of this command along with any subcommands to the sender
     * @param sender the sender of the command
     */
    @Override
    public void sendUsage( CommandSender sender ) {
        List<String> toSend = new ArrayList<>();
        toSend.add( "&8" );
        if ( super.getUsage() != null && super.getUsage().length() > 0 ) { toSend.add( super.getUsage() ); }
        for ( CyberSubCommand subcommand : subcommandList ) {
            toSend.add( subcommand.getUsage() );
        }
        toSend.add( "&8" );
        CyberMsgUtils.sendMsg( sender, toSend );
    }

    /**
     * Adds a {@link CyberSubCommand} to the command
     * @param subcommand The {@link CyberSubCommand} to add
     */
    public void addSubCommand( CyberSubCommand subcommand ) {
        subcommandList.add( subcommand );
    }

    /**
     * @return All {@link CyberSubCommand}s that are added to this command
     */
    public List<CyberSubCommand> getSubCommandList() {
        return subcommandList;
    }

    /**
     * @return The names of all the {@link CyberSubCommand}s in this command
     */
    public List<String> getSubCommandNames() {
        return subcommandList.stream()
                .map( CommandSettings::getName )
                .collect( Collectors.toList() );
    }

    /**
     * @param name The name of the {@link CyberSubCommand} to get
     * @return The {@link CyberSubCommand} with the provided name, or null if not found
     */
    public CyberSubCommand getSubCommand( String name ) {
        return subcommandList.stream()
                .filter( subcommand -> subcommand.getName().equalsIgnoreCase( name ) )
                .findFirst()
                .orElse( null );
    }

    /**
     * @param sender The {@link CommandSender}
     * @return A list of {@link CyberSubCommand} that the provided {@link CommandSender} has permission to use
     */
    public List<CyberSubCommand> getSubCommandsForSender( CommandSender sender ) {
        return subcommandList.stream()
                .filter( subcommand -> subcommand.permissionsAllowed( sender ) )
                .collect( Collectors.toList() );
    }

    /**
     * @param executeSubcommands True if the command, when executed, should try to
     *                           execute a subcommand before executing the command
     *                           itself, otherwise false.
     */
    public void setExecuteSubCommands( boolean executeSubcommands ) {
        this.executeSubcommands = executeSubcommands;
    }

    /**
     * @return True if the command, when executed, should try to execute a
     * subcommand before executing the command itself, otherwise false.
     */
    public boolean getExecuteSubCommands() {
        return executeSubcommands;
    }

    /**
     *
     * @param onlySubCommands True if the command, when executed, should ONLY try to
     *                        execute the subcommands, and not the {@link #execute} method
     *                        itself, otherwise false. If this is true, and the provided
     *                        argument matches no subcommand, the {@link BaseCommand#sendUsage}
     *                        will be sent.
     */
    public void setOnlySubCommands( boolean onlySubCommands ) {
        this.isOnlySubCommands = onlySubCommands;
    }

    /**
     * @return True if the command, when executed, should ONLY try to execute the subcommands,
     * and not the {@link #execute} method itself, otherwise false. If this is true, and the
     * provided argument matches no subcommand, the {@link BaseCommand#sendUsage} will be sent.
     */
    public boolean isOnlySubCommands() {
        return isOnlySubCommands;
    }
}
