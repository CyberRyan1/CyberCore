package com.github.cyberryan1.cybercore.helpers.command;

import com.github.cyberryan1.cybercore.helpers.command.helper.CommandHelper;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CyberSubcommand extends CommandHelper {

    public CyberSubcommand( String name, String permission, String permissionMsg, String usage ) {
        super.name = name;
        super.permission = permission;
        super.permissionMsg = CoreUtils.getColored( permissionMsg );
        super.usage = CoreUtils.getColored( usage );
    }

    public CyberSubcommand( String name, String permission, String usage ) {
        this( name, permission, "&cInsufficient Permissions!", usage );
    }

    public CyberSubcommand( String name, String usage ) {
        this( name, null, "&cInsufficient Permissions!", usage );
    }

    //
    // Main Methods
    //

    public List<String> onTabComplete( CommandSender sender, String args[] ) {
        if ( super.tabcompleteEnabled == false ) { return List.of(); }
        return tabComplete( sender, args );
    }

    public abstract List<String> tabComplete( CommandSender sender, String args[] );

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

        return execute( sender, args );
    }

    public abstract SubcommandStatus execute( CommandSender sender, String args[] );
}