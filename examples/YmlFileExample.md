
# YML File Example

This is an example of how you could implement a config.yml file, data.yml file, or other custom yml files.

This can only be done in the onEnable method of your plugin's main class. This is because the yml files are loaded
when the plugin is enabled. **IMPORTANT:** for each yml file you have, you must have a yml file that has the same 
name with "_default" added to the end of the name. For example, if you have a config.yml file, then you must have 
a config_default.yml file as well. This default file is where you will put the default values for your yml file--
your regular file should be empty. Example yml files are provided at the end of this example.

Main file -
```java
// Imports
import com.github.cyberryan1.cybercore.CyberCore;
import com.github.cyberryan1.cybercore.managers.YmlManager;
import com.github.cyberryan1.cybercore.utils.VaultUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class YmlExample extends JavaPlugin {
    
    public static final ConfigUtils CONFIG = new ConfigUtils();
    public static final DataUtils DATA = new DataUtils();
    
    @Override
    public void onEnable() {
        // Initialize the core parts of CyberCore
        CyberCore.setPlugin( this );
        new VaultUtils();
        
        // Initializing the config.yml file. This will create the file if
        //      it doesn't exist as well as update it, if needed. This 
        //      updating is why the config_default.yml file is needed.
        CONFIG.getYMLManager().initialize();
        // Same thing as above, but for the data.yml file.
        DATA.getYMLManager().initialize();
        // Sometimes you don't want to see path not found errors for
        //      specific yml files. The following disables this
        //      for the data.yml file.
        DATA.sendPathNotFoundErrors( false );
    }
    
    // Reloads the config.yml file
    public static void reloadConfig() {
        CONFIG.getYMLManager().initialize();
    }
    
    // Reloads the config.yml file
    public static void reloadData() {
        DATA.getYMLManager().initialize();
    }
    
    // Saves the data.yml file. Note that you cannot save the config.yml
    //      file since it is not editable.
    public static void saveData() {
        DATA.save();
    }
}
```

ConfigUtils class -
```java
// Imports
import com.github.cyberryan1.cybercore.managers.FileType;
import com.github.cyberryan1.cybercore.managers.YmlManager;
import com.github.cyberryan1.cybercore.utils.yml.YMLReadTemplate;

// We are extending from the YmlReadTemplate since we will only be
//      reading from this file, not writing to it.
public final class ConfigUtils extends YmlReadTemplate {
    
    public ConfigUtils() {
        super.setYMLManager( new YmlManager( FileType.CONFIG ) );
    }
}
```

DataUtils class -
```java
// Imports
import com.github.cyberryan1.cybercore.managers.FileType;
import com.github.cyberryan1.cybercore.managers.YmlManager;
import com.github.cyberryan1.cybercore.utils.yml.YMLReadEditTemplate;

// We are extending from the YmlReadEditTemplate since we will be
//      reading and writing to this file.
public final class DataUtils extends YmlReadEditTemplate {
    
    public DataUtils() {
        super.setYMLManager( new YmlManager( FileType.DATA ) );
    }
}
```

<br>

How you can read from and edit the yml files is shown below:
```java
class TestYml {
    public static void test() {
        // Reading
        String permission = YmlExample.CONFIG.getStr( "command-one.permission" );
        String permissionMsg = YmlExample.CONFIG.getColoredStr( "command-one.permission-msg" );
        int cooldownSeconds = YmlExample.CONFIG.getInt( "command-one.cooldown" );
        String cooldownBypass[] = YmlExample.CONFIG.getStrList( "command-one.cooldown-bypass" );
        ArrayList<String> allKeys = YmlExample.CONFIG.getAllKeys();
        ArrayList<String> keysForCommand = YmlExample.CONFIG.getKeys( "command-one." ); // Notice the dot at the end
        
        // Writing (can only be done with classes that extend YMLReadEditTemplate)
        YmlExample.DATA.set( "test.one", 1 );
        YmlExample.DATA.set( "test.two", "two" );
        YmlExample.saveData();
    }
}
```

<br>

Example config.yml file in the resources folder _(note how it is empty!)_ - 
```yml
```

Example config_default.yml file in the resources folder -
```yml
command-one:
  permission: "one.use"
  permission-msg: "&7You do &cnot&7 have permission to use this command"
  cooldown: 60 # in seconds
  cooldown-bypass:
    - CyberRyan
    - fearin
```
When the plugin loads, it will delete then recreate the config_default.yml file. Then, 
from that file, it will update the config.yml file. For example, when the plugin is loaded
for the first ever time, the config.yml file will be exactly the same as the 
config_default.yml file.

<br>

Example data.yml file in the resources folder _(note how it is empty!)_ -
```yml
```

Example data_default.yml file in the resources folder - <br>
*Note how this one is also empty, since we don't want any default values in our data.yml file*
```yml
```