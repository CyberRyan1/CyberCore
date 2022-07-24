
# Command With Tab Complete Example

This is an example of what a command that has custom arguments (non-players) might look like.
Obviously some things may need to be changed, depending on the project, what you need accomplished, etc.

```java
// Imports

import com.github.cyberryan1.cybercore.commands.CyberCommand;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ColorCommannd extends CyberCommand {

    // In this command, we are going to send the sender a message depending on their
    //      argument. If they type the color code for red, then we will send them
    //      a message in red. If they type the color code for green, then we will
    //      send them a message in green. And so on.

    public ColorCommand() {
        super(
                "color", // The label of the command
                "server.color", // The permission of the command
                "&8/&7color &b(color code)" // The usage of the command
        );
        // Note that in the super constructor, we did not add a permission message. 
        //      This is because there is, by default, one provided for us. The
        //      default permission message is "&cInsufficient Permissions!"

        // Note that we are registering the command with tab completion enabled.
        super.register( true );

        super.demandPermission( true );
        super.setMinArgs( 1 );
    }

    @Override
    public List<String> tabComplete( CommandSender sender, String args[] ) {
        // First, we are going to make a list of all valid color codes. For now,
        //      we will only allow the colors red, green, and blue.
        final List<String> ARG_OPTIONS = new ArrayList<>( List.of( "&c", "&a", "&b" ) );

        // Now, we are going to check if the length of the arguments list is 
        //      zero OR if first argument is zero. If so, we will return 
        //      all the possible options.
        if ( args.length == 0 || args[0].length() == 0 ) {
            return ARG_OPTIONS;
        }

        // Now, we are going to check return all the possible options that
        //      contain the inputted argument only while the length of the
        //      arguments list is one.
        if ( args.length == 1 ) {
            return super.matchArgs( args[0], ARG_OPTIONS );
        }

        // Otherwise, we will just return an empty list.
        return List.of();
    }

    @Override
    public boolean execute( CommandSender sender, String args[] ) {
        // Going to see if the first argument (args[0]) is either &c, &a, 
        //      or &b. If it is, we will send the colored message.

        if ( args[0].equalsIgnoreCase( "&c" ) ) {
            CoreUtils.sendMsg( sender, "&cRed" );
        }
        
        else if ( args[0].equalsIgnoreCase( "&a" ) ) {
            CoreUtils.sendMsg( sender, "&aGreen" );
        }
        
        else if ( args[0].equalsIgnoreCase( "&b" ) ) {
            CoreUtils.sendMsg( sender, "&bBlue" );
        }
        
        else {
            CoreUtils.sendMsg( sender, "&cInvalid Color Code" );
            // If we wanted to send the usage of the command instead, you
            //      could add the following line:
            // super.sendUsage( sender );
        }

        return true;
    }

}
```

Nothing different when registering this command. If you need help with registering
the command, see the CommandBasicExample.md or CommandWithArgumentsExample.md files.