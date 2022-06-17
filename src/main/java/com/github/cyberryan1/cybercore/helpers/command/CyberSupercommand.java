package com.github.cyberryan1.cybercore.helpers.command;

import com.github.cyberryan1.cybercore.CyberCore;
import com.github.cyberryan1.cybercore.helpers.command.helper.CommandHelper;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CyberSupercommand extends CommandHelper implements CommandExecutor, TabCompleter {

    protected List<CyberSubcommand> subcommands = new ArrayList<>();

    public CyberSupercommand( String name, String permission, String permissionMsg, String usage ) {
        super.name = name;
        super.permission = permission;
        super.permissionMsg = CoreUtils.getColored( permissionMsg );
        super.usage = CoreUtils.getColored( usage );
    }

    public CyberSupercommand( String name, String permission, String usage ) {
        this( name, permission, "&cInsufficient Permissions!", usage );
    }

    public CyberSupercommand( String name, String usage ) {
        this( name, null, "&cInsufficient Permissions!", usage );
    }

    //
    // Main Methods
    //

    public List<String> onTabComplete( CommandSender sender, Command command, String label, String args[] ) {
        if ( super.tabcompleteEnabled == false ) {
            return List.of();
        }
        return tabComplete( sender, args );
    }

    public abstract List<String> tabComplete( CommandSender sender, String args[] );

    public boolean onCommand( CommandSender sender, Command command, String label, String args[] ) {
        if ( super.demandPlayer && ( sender instanceof Player ) == false ) {
            CoreUtils.sendMessage( sender, "&7This command can only be ran by a player" );
            return true;
        }

        if ( super.demandConsole && ( sender instanceof ConsoleCommandSender ) == false ) {
            CoreUtils.sendMessage( sender, "&7This command can only be ran by console" );
            return true;
        }

        if ( super.demandPermission && permsAllowed( sender ) == false ) {
            CoreUtils.sendMessage( sender, super.permissionMsg );
            return true;
        }

        if ( args.length < super.minArgs ) {
            sendUsage( sender );
            return true;
        }

        return execute( sender, args );
    }

    public abstract boolean execute( CommandSender sender, String args[] );

    //
    // Helper Methods
    //

    public void register( boolean includeTabComplete ) {
        this.tabcompleteEnabled = includeTabComplete;
        CyberCore.getPlugin().getCommand( super.name ).setExecutor( this );
        if ( this.tabcompleteEnabled ) {
            CyberCore.getPlugin().getCommand( super.name ).setTabCompleter( this );
        }
    }

    @Override
    public void sendUsage( CommandSender sender ) {
        List<String> toSend = new ArrayList<>();
        toSend.add( "&8" );
        for ( CyberSubcommand subcommand : subcommands ) {
            toSend.add( subcommand.getUsage() );
        }
        toSend.add( "&8" );
        CoreUtils.sendMessage( sender, toSend );
    }

    public void addSubcommand( CyberSubcommand subcommand ) {
        this.subcommands.add( subcommand );
    }

    public void removeSubcommand( CyberSubcommand subcommand ) {
        this.subcommands.remove( subcommand );
    }

    public List<String> getAllSubcommandNames() {
        List<String> names = new ArrayList<>();
        for ( CyberSubcommand subcommand : this.subcommands ) {
            names.add( subcommand.getName() );
        }
        return names;
    }

    public CyberSubcommand getSubcommand( String name ) {
        for ( CyberSubcommand subcommand : this.subcommands ) {
            if ( subcommand.getName().equalsIgnoreCase( name ) ) {
                return subcommand;
            }
        }
        return null;
    }

    public List<CyberSubcommand> getSubcommandsForPlayer( Player player ) {
        // return all subcommands where subcommandPermisisonsAllowed is true
        List<CyberSubcommand> subcommands = new ArrayList<>();
        for ( CyberSubcommand subcommand : this.subcommands ) {
            if ( subcommand.permsAllowed( player ) ) {
                subcommands.add( subcommand );
            }
        }
        return subcommands;
    }

    public List<CyberSubcommand> getPlayerSubcommands( Player player ) {
        return getSubcommandsForPlayer( player );
    }

    public boolean subcommandPermissionsAllowed( Player player, String subcommand ) {
        CyberSubcommand sub = getSubcommand( subcommand );
        if ( sub == null ) {
            return false;
        }
        return sub.permsAllowed( player );
    }

    public boolean subcommandPermsAllowed( Player player, String subcommand ) {
        return subcommandPermissionsAllowed( player, subcommand );
    }
}