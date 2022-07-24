
# Super and Sub Command Example

This is an example of what a command (supercommand) with subcommands might look like.
Obviously some things may need to be changed, depending on the project, what you need accomplished, etc.

Punish Super Command File -
```java
// Imports
import com.github.cyberryan1.cybercore.helpers.command.CyberSubcommand;
import com.github.cyberryan1.cybercore.helpers.command.CyberSupercommand;

import java.util.List;
import java.util.stream.Collectors;

// Note that instead of extending CyberCommand we are extending CyberSupercommand
public class PunishSuperCommand extends CyberSupercommand {

    // In this command, we are going to punish the target in some way.
    //      The punishment options are kick and warn.

    // Note how the three methods of this supercommand are the same as
    //      they would be in a regular command, however, the contents
    //      are a bit different.

    public PunishSuperCommand() {
        super(
                // The label of the command
                "punish",
                // We set the usage to null because a different usage 
                //      message is automatically provided. More
                //      information below.
                null
        );

        // Enabling tab completions for our subcommands.
        super.register( true );

        // We set the minimum args to 1 because the sender must provide
        //      a subcommand.
        super.minArgs( 1 );
        
        // Adding the subcommands to the supercommand.
        super.addSubcommand( new PunishKickSubcommand() );
    }

    @Override
    public List<String> tabComplete( CommandSender sender, String args[] ) {
        // We must iterate through the subcommands and get all their
        //      labels for tab completion. The 
        //      super.getSubcommandsForPlayer() methods returns all
        //      subcommands that the player has permission to use.
        final List<String> ALLOWED_SUBCOMMAND_LABELS = super.getSubcommandsForPlayer( sender ).stream()
                .map( CyberCommand::getLabel )
                .collect( Collectors.toList() );

        // Standard tab completion for the first argument (args[0])
        if ( args.length == 0 || args[0].length() == 0 ) {
            return ALLOWED_SUBCOMMAND_LABELS;
        } else if ( args.length == 1 ) {
            return matchArgs( ALLOWED_SUBCOMMAND_LABELS, args[0] );
        }

        // For the remaining arguments, we need to iterate through
        //      the tab complete methods of each subcommand and
        //      return the results of that.
        for ( CyberSubcommand sub : super.getSubcommandsForPlayer( sender ) ) {
            if ( subcmd.getName().equalsIgnoreCase( args[0] ) ) {
                // Note how we return the onTabComplete method, NOT
                //      the tabComplete method.
                return subcmd.onTabComplete( sender, args );
            }
        }

        return List.of();
    }

    @Override
    public boolean execute( CommandSender sender, String args[] ) {

        // We must iterate through all the subcommands and execute
        //      them if their name matches the first argument (args[0]).
        for ( CyberSubcommand sub : super.getSubcommandsForPlayer( sender ) ) {
            if ( subcmd.getName().equalsIgnoreCase( args[0] ) ) {
                // Note how we return the onExecute method, NOT
                //      the execute method.
                subcmd.onExecute( sender, args );

                // If you would like to do any handling with the
                //      SubcommandStatus enum, here is where 
                //      you would do that.

                return true;
            }
        }

        // Since no subcommands were executed, we are going to
        //      send the usage of the command
        super.sendUsage( sender );

        // Example of the default sendUsage method for supercommands:
        //      - *empty line*
        //      - "&8/&7punish &bkick (player)"
        //      - "&8/&7punish &bwarn (player)"
        //      - *empty line*
        // Note how all the lines, except the empty lines, are just
        //      the usage of the subcommand.

        return true;
    }
}
```

Punish Kick Subcommand File -
```java
// Imports
import com.github.cyberryan1.cybercore.helpers.command.ArgType;
import com.github.cyberryan1.cybercore.helpers.command.CyberSubcommand;
import com.github.cyberryan1.cybercore.helpers.command.SubcommandStatus;
import org.bukkit.Bukkit;

import java.util.List;

// Note that we are extending from the CyberSubcommand class
public class PunishKickSubcommand extends CyberSubcommand {

    // Note how the three methods of the subcommand are similar to
    //      regular commands, just with a slight change in the
    //      execute method (see below).

    public PunishKickSubcommand() {
        super(
                // The label of the subcommand
                "kick",
                // The usage of the subcommand
                "&8/&7punish &akick (player)"
        );
        // Note how there is no need to register the subcommand.

        // Set the minimum args to 2 because the sender must provide
        //      the first argument, which for this subcommand is
        //      "kick", and the second argument, which will be an
        //      online player.
        super.setMinArgs( 2 );

        // Set the arg type of argument 2 to online player
        super.setArgType( 1, ArgType.ONLINE_PLAYER );

        // You can pretty much use all the same restrictions as
        //      in a regular command, like demandPlayer(),
        //      demandPermission(), etc.
    }

    @Override
    public List<String> tabComplete( CommandSender sender, String args[] ) {
        // Because we specified the argument type of argument 2 to
        //      an online player, we don't need to do anything here
        //      except return an empty list.
        return List.of();
    }

    @Override
    // Note how instead of returning a boolean, we are returning 
    //      a SubcommandStatus
    public SubcommandStatus execute( CommandSender sender, String args[] ) {
        final Player target = Bukkit.getPlayer( args[1] );
        target.kickPlayer( "You have been kicked for some reason" );

        return SubcommandStatus.NORMAL;
    }
}
```

Punish Warn Subcommand File -

```java
// Imports
import com.github.cyberryan1.cybercore.helpers.command.ArgType;
import com.github.cyberryan1.cybercore.helpers.command.CyberSubcommand;
import com.github.cyberryan1.cybercore.helpers.command.SubcommandStatus;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.Bukkit;

import java.util.List;

public class PunishWarnSubcommand extends CyberSubcommand {

    public PunishWarnSubcommand() {
        super(
                // The label of the subcommand
                "warn",
                // The permission of the subcommand
                "server.punish.warn",
                // The usage of the subcommand
                "&8/&7punish &awarn (player) (reason)"
        );

        // Set the minimum args to 3 because the sender must provide
        //      the first argument, which for this subcommand is
        //      "warn", the second argument, which will be an
        //      online player, and then the remaining arguments
        //      will be combined for the reason.
        super.setMinArgs( 3 );

        // Only senders with the permission can run this subcommand.
        //      When someone without the correct permission tries
        //      to run this subcommand, the permission message
        //      will be sent to them automatically.
        super.demandPermission( true );
        // Set the arg type of argument 2 (index 1) to
        //      online player
        super.setArgType( 1, ArgType.ONLINE_PLAYER );
    }

    @Override
    public List<String> tabComplete( CommandSender sender, String args[] ) {
        // Because we specified the argument type of argument 2 to
        //      an online player, and the remaining arguments are 
        //      out of our control, so we don't need to do 
        //      anything here except return an empty list.
        return List.of();
    }

    @Override
    // Note how instead of returning a boolean, we are returning 
    //      a SubcommandStatus
    public SubcommandStatus execute( CommandSender sender, String args[] ) {
        final Player target = Bukkit.getPlayer( args[1] );
        // The super.combineArgs() method combines the arguments
        //      from the args list starting at index 2 into a
        //      singular string, all separated by spaces. This
        //      can be used in any command, not just subcommands.
        final String combinedArgs = super.combineArgs( args, 2 );

        CoreUtils.sendMsg( player, "&7You warned &a" + target.getName() + "&7 due to &a" + combinedArgs );
        CoreUtils.sendMsg( target, "&7You were warned due to &a" + combinedArgs );

        return SubcommandStatus.NORMAL;
    }
}
```