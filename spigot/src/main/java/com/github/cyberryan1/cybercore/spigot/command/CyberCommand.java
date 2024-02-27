package com.github.cyberryan1.cybercore.spigot.command;

import com.github.cyberryan1.cybercore.spigot.CyberCore;
import com.github.cyberryan1.cybercore.spigot.command.cooldown.CooldownSettings;
import com.github.cyberryan1.cybercore.spigot.command.sent.SentCommand;
import com.github.cyberryan1.cybercore.spigot.command.settings.ArgType;
import com.github.cyberryan1.cybercore.spigot.command.settings.BaseCommand;
import com.github.cyberryan1.cybercore.spigot.utils.CyberCommandUtils;
import com.github.cyberryan1.cybercore.spigot.utils.CyberMsgUtils;
import com.github.cyberryan1.cybercore.spigot.utils.CyberVaultUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Used to easily create a command that can then be registered to the server.
 *
 * @author CyberRyan1
 */
public abstract class CyberCommand extends BaseCommand implements CommandExecutor, TabCompleter {

    /**
     * Creates a new CyberCommand with the provided arguments.<p>
     *
     * <i>Note: this is where you should call the {@link #register(boolean)} method.</i>
     *
     * @param name The name of the command
     * @param permission The permission to use the command (null for no permission)
     * @param permissionMsg The message to send to the player if they do not have the permission
     * @param usage The usage of the command
     */
    public CyberCommand( String name, String permission, String permissionMsg, String usage ) {
        super( name, permission, permissionMsg, usage );
    }

    /**
     * Creates a new CyberCommand with the provided arguments. The permission
     * message is set to the default permission message provided by
     * {@link CyberCommandUtils#getDefaultPermissionMsg()}.<p>
     *
     * <i>Note: this is where you should call the {@link #register(boolean)} method.</i>
     *
     * @param name The name of the command
     * @param permission The permission to use the command (null for no permission)
     * @param usage The usage of the command
     */
    public CyberCommand( String name, String permission, String usage ) {
        super( name, permission, usage );
    }

    /**
     * Creates a new CyberCommand with the provided arguments. The permission
     * message is set to the default permission message provided by
     * {@link CyberCommandUtils#getDefaultPermissionMsg()}. The permission
     * is assumed to be null, meaning that no permission is needed to execute
     * this command.<p>
     *
     * <i>Note: this is where you should call the {@link #register(boolean)} method.</i>
     *
     * @param name The name of the command
     * @param usage The usage of the command
     */
    public CyberCommand( String name, String usage ) {
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

        if ( super.getArgIndexes( ArgType.ONLINE_PLAYER ).contains( args.length - 1 )
                || super.getArgIndexes( ArgType.OFFLINE_PLAYER ).contains( args.length - 1 ) ) {
            if ( args[args.length - 1].isEmpty() ) { return CyberCommandUtils.getOnlinePlayerNames(); }
            else { return CyberCommandUtils.matchOnlinePlayers( args[args.length - 1] ); }
        }
        else if ( super.getArgIndexes( ArgType.STRING ).contains( args.length - 1 ) ) {
            if ( args[args.length - 1].isEmpty() ) { return super.getStringArgOptions( args.length -1 ); }
            else { return CyberCommandUtils.matchArgs( super.getStringArgOptions( args.length - 1 ), args[args.length - 1] ); }
        }

        return TO_RETURN;
    }

    /**
     * The tabComplete method is called when the player presses tab while typing a command. <p>
     * Returning an empty list will do the following: <br>
     * &emsp;- If the argument the command sender is currently typing has been set
     * <i>(via {@link BaseCommand#setArgType(int, ArgType)})</i> to either {@link ArgType#ONLINE_PLAYER}
     * or {@link ArgType#OFFLINE_PLAYER}), then a list of players will be sent to the command sender. <br>
     * &emsp;- If the above yields no results, an empty list will be sent to the command sender. <br>
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
            else if ( settings.getPermissionBypass() == null || CyberVaultUtils.hasPerms( player, settings.getPermissionBypass() ) == false ) {
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
     * the {@link SentCommand} parameter<p>
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
    public void register( boolean includeTabComplete ) {
        super.setTabCompleteEnabled( includeTabComplete );

        CyberCore.getPlugin().getCommand( super.getName() ).setExecutor( this );
        if ( includeTabComplete ) {
            CyberCore.getPlugin().getCommand( super.getName() ).setTabCompleter( this );
        }
    }
}
