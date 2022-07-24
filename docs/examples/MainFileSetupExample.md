
# Main File Example

This is an example of what the main file should look like. <br>
Obviously some things may need to be changed, depending on the project,
what you need, etc.

_**Note:** Imports will always be present in these examples to prevent
wrong file imports and confusion._

```java
// Imports
import org.bukkit.plugin.java.JavaPlugin;
import com.github.cyberryan1.cybercore.CyberCore;
import com.github.cyberryan1.cybercore.utils.VaultUtils;

public class MainFile extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // Set the CyberCore class's plugin instance to this plugin.
        //      Allows you to get the plugin using the 
        //      CyberCore.getPlugin() method.
        CyberCore.setPlugin( this );
        
        // Initializes Vault using the VaultUtils class. If you do not 
        //      want to rely on vault, you can ignore this line.
        new VaultUtils();
        
        // If you would like to do anything with a config.yml or a data.yml,
        //      you would typically do it here. See the ConfigFileExample.md
        //      and the DataFileExample.md files for more information.
        
        // If you would like to register commands, you would typically do it
        //      here as well. For more information, see the 
        //      CommandOneExample.md file
    }
}

```