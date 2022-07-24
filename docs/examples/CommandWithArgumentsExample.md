
# Command With Arguments Example

This is an example of what a command might look like. Obviously some things may need to be changed,
depending on the project, what you need accomplished, etc.

```java
// Imports
import com.github.cyberryan1.cybercore.commands.CyberCommand;
import com.github.cyberryan1.cybercore.commands.ArgType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

// Because this is a regular command, we are going to extend CyberCommand.
public class PlayerOnlineCommand extends CyberCommand {

    public PlayerOnlineCommand() {
        // The super constructor is how we setup the command for CyberCore
        super(
                "playeronline", // The label of the command
                "server.playeronline", // The permission of the command
                "&c&lYou don't have the correct permissions for this command", // The permission message of the command
                "&8/&7playeronline &b(player)" // The usage of the command
        );
        // Registering the command will make it available for use. Setting the argument 
        //      to true will enable tab completions. False will disable tab completions 
        //      and return an empty list.
        super.register( true );

        // Because we have a permission, we want to make sure that the command is only
        //      ran by players with the permission. Doing this makes it so that 
        //      the execute method (below) is not ran unless the player has the permission
        //      specified in the super constructor. If a player doesn't, they will 
        //      be sent the permission message specified in the super constructor.
        super.demandPermission( true );
        // Similarly, because we want the command to only be run by players, we set the
        //      demandPlayer to true. If a player doesn't execute the command, they are
        //      sent a message and the execute method is not ran.
        super.demandPlayer( true );
        // Also, since we have an argument, we want to make sure that the command is only
        //      ran when the length of the arguments is at least one. If the length is
        //      less than one, the execute method is not ran and the usage of the command
        //      is sent.
        super.setMinArgs( 1 );
        // Since our first argument (index 0) is a player, we want to make sure that the 
        //      argument is valid. In this case, our argument can either be an online or 
        //      offline player, so we set the argument type to ArgType.OFFLINE_PLAYER. If 
        //      the argument is not valid, the execute method is not ran and the usage of 
        //      the command is sent.
        super.setArgType( 0, ArgType.OFFLINE_PLAYER );

    }

    @Override
    public List<String> tabComplete( CommandSender sender, String args[] ) {
        // This is where we would put code for tab completions, however, since our
        //      args length is at a max one and since we have designated the type
        //      of the first argument, the tab completions are already handled.
        //      Therefore, we should just return an empty list and let CyberCommand
        //      handle the tab completions.
        return List.of();
    }

    @Override
    public boolean execute( CommandSender sender, String args[] ) {
        // Because we have the super.demandPlayer() method set to true, we know that the
        //      sender is a player. Therefore, we can cast the sender to a player
        final Player player = ( Player ) sender;

        // Similarly, since we designated the arg type of the first argument to be an
        //      offline player, we know that the argument is valid. Therefore, we can
        //      cast the argument to a player.
        final OfflinePlayer target = Bukkit.getOfflinePlayer( args[0] );
        
        // In this command, we are going to tell the sender (player) whether the
        //      argument (target) is online or not.
        if ( target.isOnline() ) {
            // Using CoreUtils.sendMsg() sends the colored message to the player.
            CoreUtils.sendMsg( player, "&b" + target.getName() + " &7is online" );
        }
        else {
            CoreUtils.sendMsg( player, "&b" + target.getName() + " &7is offline" );
        }

        // Similar to regular commands, we return true to indicate that the command
        //      executed successfully, false otherwise. In our case, we want to
        //      return true.
        return true;
    }
}
```

Now, all that you have to do is register the command in your plugin's onEnable method. To register the command,
all you have to do is create a new instance of the class, like below:
```java
new PlayerOnlineCommand();
```