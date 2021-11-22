package com.github.cyberryan1.cybercore.managers;

import com.github.cyberryan1.cybercore.CyberCore;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

public class YMLManager {

    public FileType type;

    private FileConfiguration config;
    private File configFile;

    public YMLManager() {
        this.type = null;
    }

    public YMLManager( FileType type ) {
        this.type = type;
        saveDefaultConfig();
    }

    public FileConfiguration getConfig() {
        if ( config == null ) { reloadConfig(); }
        return config;
    }

    public File getConfigFile() { return configFile; }

    public FileType getFileFor() { return type; }

    public void reloadConfig() {
        if ( configFile == null ) {
            configFile = new File( CyberCore.getPlugin().getDataFolder(), type.getFileName() );
        }

        config = YamlConfiguration.loadConfiguration( configFile );

        InputStream defaultStream = CyberCore.getPlugin().getResource( type.getFileName() );
        if ( defaultStream != null ) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration( new InputStreamReader( defaultStream ) );
            config.setDefaults( defaultConfig );
        }
    }

    public void saveConfig() {
        if ( config == null || configFile == null ) { return; }
        try {
            getConfig().save( configFile );
        }
        catch ( IOException e ) {
            CoreUtils.logError( "Could not save the file " + type.getFileName() );
        }
    }

    // saves the default config
    public void saveDefaultConfig() {
        if ( configFile == null ) { configFile = new File( CyberCore.getPlugin().getDataFolder(), type.getFileName() ); }
        if ( configFile.exists() == false ) { CyberCore.getPlugin().saveResource( type.getFileName(), false ); }
    }

    // Checks if any new keys, comments, etc, have been added via a plugin update
    // And adds them to the current config, if needed
    // Note: must reload the config afterward for changes to come into effect
    public void updateConfig() {
        if ( configFile == null ) { configFile = new File( CyberCore.getPlugin().getDataFolder(), type.getFileName() ); }
        if ( configFile.exists() == false ) { CyberCore.getPlugin().saveResource( type.getFileName(), false ); }

        try { ConfigUpdater.update( CyberCore.getPlugin(), type.getFileName(), configFile, Collections.emptyList() );
        } catch ( IOException e ) { e.printStackTrace(); }

        reloadConfig();
    }
}