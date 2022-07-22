package com.github.cyberryan1.cybercore.helpers.command;

import com.github.cyberryan1.cybercore.CyberCore;
import com.github.cyberryan1.cybercore.helpers.command.helper.CommandHelper;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CyberSubcommand extends CommandHelper {

    /**
     * @param name the name of the subcommand
     * @param permission the permission for the subcommand
     * @param permissionMsg the permission message for the subcommand
     * @param usage the usage message for the subcommand
     */
    public CyberSubcommand( String name, String permission, String permissionMsg, String usage ) {
        super.name = name;
        super.permission = permission;
        super.permissionMsg = null;
        if ( permissionMsg != null ) {  super.permissionMsg = CoreUtils.getColored( permissionMsg ); }
        super.usage = null;
        if ( usage != null ) { super.usage = CoreUtils.getColored( usage ); };
    }

    /**
     * @param name the name of the subcommand
     * @param permission the permission for the subcommand
     * @param usage the usage message for the subcommand
     */
    public CyberSubcommand( String name, String permission, String usage ) {
        this( name, permission, "&cInsufficient Permissions!", usage );
    }

    /**
     * @param name the name of the subcommand
     * @param usage the usage message for the subcommand
     */
    public CyberSubcommand( String name, String usage ) {
        this( name, null, "&cInsufficient Permissions!", usage );
    }

    //
    // Main Methods
    //

    /**
     * This should be ignored by most developers
     */
    public List<String> onTabComplete( CommandSender sender, String args[] ) {
        if ( super.tabcompleteEnabled == false ) { return List.of(); }
        return tabComplete( sender, args );
    }

    /**
     * The tabComplete method is called when the player presses tab in the console.
     * @param sender the sender of the command
     * @param args the arguments of the command (all of them!)
     * @return a list of strings that can be used as tab completions
     */
    public abstract List<String> tabComplete( CommandSender sender, String args[] );

    /**
     * This should be ignored by most developers
     */
    public SubcommandStatus runExecute( CommandSender sender, String args[] ) {
        if ( super.demandPlayer && ( sender instanceof Player ) == false ) {
            CoreUtils.sendMessage( sender, "&7This subcommand can only be ran by a player" );
            return SubcommandStatus.INVALID_RUNNER;
        }

        if ( super.demandConsole && ( sender instanceof ConsoleCommandSender ) == false ) {
            CoreUtils.sendMessage( sender, "&7This subcommand can only be ran by console" );
            return SubcommandStatus.INVALID_RUNNER;
        }

        if ( super.demandPermission && ( sender.hasPermission( super.permission ) == false ) ) {
            CoreUtils.sendMessage( sender, this.permissionMsg );
            return SubcommandStatus.NO_PERMISSION;
        }

        if ( args.length < super.minArgs ) {
            sendUsage( sender );
            return SubcommandStatus.INVALID_ARGS;
        }

        if ( super.isAsync() ) {
            Bukkit.getScheduler().runTaskAsynchronously( CyberCore.getPlugin(), () -> execute( sender, args ) );
            return SubcommandStatus.NORMAL;
        }

        else {
            return execute( sender, args );
        }
    }

    /**
     * The execute method is called when the player runs the command.
     * @param sender the sender of the command
     * @param args the arguments of the command (all of them!)
     * @return the status of the subcommand
     */
    public abstract SubcommandStatus execute( CommandSender sender, String args[] );
}