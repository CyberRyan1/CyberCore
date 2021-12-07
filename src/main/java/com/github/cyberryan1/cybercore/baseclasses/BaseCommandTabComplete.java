package com.github.cyberryan1.cybercore.baseclasses;

import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.github.cyberryan1.cybercore.utils.VaultUtils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class BaseCommandTabComplete implements TabCompleter, CommandExecutor {

    // returns true if the sender is a player, false if not
    public static boolean demandPlayer( CommandSender sender ) {
        if ( sender instanceof Player ) {
            return true;
        }
        return false;
    }

    // returns true if the sender is a console sender, false if not
    public static boolean demandConsole( CommandSender sender ) {
        if ( sender instanceof ConsoleCommandSender ) {
            return true;
        }
        return false;
    }

    protected String label;
    protected String permission;
    protected String permissionMsg;
    protected String usage;

    public BaseCommandTabComplete( String label, String permission, String permissionMsg, String usage ) {
        this.label = label;
        this.permission = permission;
        this.permissionMsg = permissionMsg;
        this.usage = usage;
    }

    // will be done in the individual class, depending on the need
    public abstract List<String> onTabComplete( CommandSender sender, Command command, String label, String args[] );

    @Override
    // will also be done in the individual class as the contents of this depends on the need of the command
    /**
     * <p>What is ran when a player executes the command</p>
     * @param sender -- the sender of the command
     * @param command -- the command
     * @param label -- label of the command
     * @param args -- arguments of the command, if any were provided
     * @return whether the command successfully ran or not
     * @since 1.0.2
     */
    public abstract boolean onCommand( CommandSender sender, Command command, String label, String args[] );

    // can be @Override if needed
    public boolean permissionsAllowed( CommandSender sender ) {
        if ( permission == null ) { return true; }
        return VaultUtils.hasPerms( sender, permission );
    }

    public void sendPermissionMsg( CommandSender sender ) {
        sender.sendMessage( permissionMsg );
    }

    public void sendUsage( CommandSender sender ) {
        sender.sendMessage( usage );
    }

    public void sendInvalidPlayerArg( CommandSender sender, String input ) {
        sender.sendMessage( CoreUtils.getColored( "&7Could not find the player &b\"" + input + "&b\"" ) );
    }
}