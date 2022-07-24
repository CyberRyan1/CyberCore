
# Command Basic Example

This is an example of what an extremely basic command might look like.

```java
// Imports
import com.github.cyberryan1.cybercore.commands.CyberCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

// Because this is a regular command, we are going to extend CyberCommand.
public class HelloWorldCommand extends CyberCommand {
    
    public HelloWorldCommand() {
        // The super constructor is how we setup the command for CyberCore
        super(
                "helloworld", // The label of the command
                "&8/&7helloworld" // The usage of the command
        );
        // Registering the command will make it available for use. Setting the argument 
        //      to true will enable tab completions. False will disable tab completions 
        //      and return an empty list. In this case, we don't have any arguments to
        //      tab complete, so we set it to false. 
        super.register( false );
    }
    
    @Override
    public List<String> tabComplete( CommandSender sender, String args[] ) {
        // Because we registered this command with tab completions disabled, we don't
        //      need to do anything here except return an empty list.
        return List.of();
    }
    
    @Override
    public boolean execute( CommandSender sender, String args[] ) {
        // This is where we put the code for our command when it is executed. In this
        //      case, we are just sending a message to the sender.
        sender.sendMessage( "Hello World!" );
        
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
new HelloWorldCommand();
```