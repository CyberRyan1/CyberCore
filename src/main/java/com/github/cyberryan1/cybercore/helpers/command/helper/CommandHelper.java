package com.github.cyberryan1.cybercore.helpers.command.helper;

import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.github.cyberryan1.cybercore.utils.VaultUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper {
    protected String name;
    protected String permission;
    protected String permissionMsg;
    protected String usage;
    protected boolean demandPlayer = false;
    protected boolean demandConsole = false;
    protected boolean demandPermission = false;
    protected int minArgs = 0;
    protected boolean tabcompleteEnabled = true;

    public boolean permissionsAllowed( CommandSender sender ) {
        if ( permission == null ) { return true; }
        return VaultUtils.hasPerms( sender, this.permission );
    }

    public boolean permsAllowed( CommandSender sender ) {
        return permissionsAllowed( sender );
    }

    public void sendPermissionMsg( CommandSender sender ) {
        CoreUtils.sendMessage( sender, this.permissionMsg );
    }

    public void sendPermMsg( CommandSender sender ) {
        sendPermissionMsg( sender );
    }

    public void sendUsage( CommandSender sender ) {
        CoreUtils.sendMessage( sender, this.usage );
    }

    public List<String> getOnlinePlayerNames() {
        List<String> list = new ArrayList<>();
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            list.add( player.getName() );
        }
        return list;
    }

    public List<String> matchOnlinePlayers( String input ) {
        List<String> toReturn = new ArrayList<>();
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            if ( player.getName().toLowerCase().startsWith( input.toLowerCase() ) ) {
                toReturn.add( player.getName() );
            }
        }
        return toReturn;
    }

    public List<String> matchArgs( ArrayList<String> list, String input ) {
        for ( int index = list.size() - 1; index >= 0; index-- ) {
            if ( list.get( index ).toLowerCase().startsWith( input.toLowerCase() ) == false ) {
                list.remove( index );
            }
        }
        return list;
    }

    public List<String> matchArgs( List<String> list, String input ) {
        return matchArgs( new ArrayList<>( list ), input );
    }

    public String combineArgs( String args[] ) {
        return String.join( " ", args );
    }

    public String combineArgs( String args[], int start ) {
        StringBuilder sb = new StringBuilder();
        for ( int i = start; i < args.length; i++ ) {
            sb.append( args[ i ] );
            if ( i != args.length - 1 ) {
                sb.append( " " );
            }
        }
        return sb.toString();
    }

    //
    // Getters and setters
    //

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getPermissionMsg() {
        return permissionMsg;
    }

    public String getUsage() {
        return usage;
    }

    public boolean isDemandPlayer() {
        return demandPlayer;
    }

    public boolean isDemandConsole() {
        return demandConsole;
    }

    public boolean isDemandPermission() {
        return demandPermission;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public void setDemandPlayer( boolean demandPlayer ) {
        this.demandPlayer = demandPlayer;
    }

    public void setDemandConsole( boolean demandConsole ) {
        this.demandConsole = demandConsole;
    }

    public void setDemandPermission( boolean demandPermission ) {
        this.demandPermission = demandPermission;
    }

    public void setMinArgs( int minArgs ) {
        this.minArgs = minArgs;
    }
}