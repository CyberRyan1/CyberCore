package com.github.cyberryan1.cybercore.helpers.command;

import com.github.cyberryan1.cybercore.CyberCore;
import com.github.cyberryan1.cybercore.helpers.command.helper.CommandHelper;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CyberCommand extends CommandHelper implements CommandExecutor, TabCompleter {

    public CyberCommand( String name, String permission, String permissionMsg, String usage ) {
        super.name = name;
        super.permission = permission;
        super.permissionMsg = CoreUtils.getColored( permissionMsg );
        super.usage = CoreUtils.getColored( usage );
    }

    public CyberCommand( String name, String permission, String usage ) {
        this( name, permission, "&cInsufficient Permissions!", usage );
    }

    public CyberCommand( String name, String usage ) {
        this( name, null, "&cInsufficient Permissions!", usage );
    }

    //
    // Main Methods
    //

    /**
     * This should be ignored by most developers
     */
    public List<String> onTabComplete( CommandSender sender, Command command, String label, String args[] ) {
        if ( super.tabcompleteEnabled == false ) {
            return List.of();
        }
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

    /**
     * The execute method is called when the player runs the command.
     * @param sender the sender of the command
     * @param args the arguments of the command
     * @return true if the command was executed successfully, false otherwise
     */
    public abstract boolean execute( CommandSender sender, String args[] );

    //
    // Helper Methods
    //

    /**
     * Required to be ran for the command to work properly
     * @param includeTabComplete if the command should be tab completeable
     */
    public void register( boolean includeTabComplete ) {
        this.tabcompleteEnabled = includeTabComplete;
        CyberCore.getPlugin().getCommand( super.name ).setExecutor( this );
        if ( this.tabcompleteEnabled ) {
            CyberCore.getPlugin().getCommand( super.name ).setTabCompleter( this );
        }
    }
}