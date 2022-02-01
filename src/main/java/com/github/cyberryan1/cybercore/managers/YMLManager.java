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

/**
 * Represents a YML file that is in the plugin's resources folder
 *
 * @author CyberRyan
 */
public class YMLManager {

    public FileType type;

    private String fileName;
    private FileConfiguration config;
    private File configFile;

    /**
     * Creates a new YMLManager and assumes everything to be null
     */
    public YMLManager() {
        this.type = null;
    }

    /**
     * Creates a new YMLManager of the specified {@link FileType}
     * @param type FileType of the file this represents
     */
    public YMLManager( FileType type ) {
        this.type = type;
        this.fileName = type.getFileName();
        saveDefaultConfig();
    }

    /**
     * Creates a new YMLManager with the specified file name. File name should not include the directory
     * To the plugin.
     * @param fileName Name of the file this represents
     */
    public YMLManager( String fileName ) {
        this.type = FileType.CUSTOM;
        this.fileName = fileName;
        saveDefaultConfig();
    }

    /**
     * Gets the {@link FileConfiguration} of this file
     * @return Configuration of this file
     */
    public FileConfiguration getConfig() {
        if ( config == null ) { reloadConfig(); }
        return config;
    }

    /**
     * @return The file represented
     */
    public File getConfigFile() { return configFile; }

    /**
     * @return The {@link FileType} used
     */
    public FileType getFileFor() { return type; }

    /**
     * Reloads the config variable from the raw file
     */
    public void reloadConfig() {
        if ( configFile == null ) {
            configFile = new File( CyberCore.getPlugin().getDataFolder(), fileName );
        }

        config = YamlConfiguration.loadConfiguration( configFile );

        InputStream defaultStream = CyberCore.getPlugin().getResource( fileName );
        if ( defaultStream != null ) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration( new InputStreamReader( defaultStream ) );
            config.setDefaults( defaultConfig );
        }
    }

    /**
     * Saves the config to the file
     */
    public void saveConfig() {
        if ( config == null || configFile == null ) { return; }
        try {
            getConfig().save( configFile );
        }
        catch ( IOException e ) {
            CoreUtils.logError( "Could not save the file " + fileName );
        }
    }

    /**
     * Saves the default file resource to the plugin's resources folder, provided it already doesn't exist
     */
    public void saveDefaultConfig() {
        if ( configFile == null ) { configFile = new File( CyberCore.getPlugin().getDataFolder(), fileName ); }
        if ( configFile.exists() == false ) { CyberCore.getPlugin().saveResource( fileName, false ); }
    }

    /**
     * Checks if any new keys, comments, or anything else has been added to this file from a plugin update
     */
    public void updateConfig() {
        if ( configFile == null ) { configFile = new File( CyberCore.getPlugin().getDataFolder(), fileName ); }
        if ( configFile.exists() == false ) { CyberCore.getPlugin().saveResource( fileName, false ); }

        try { ConfigUpdater.update( CyberCore.getPlugin(), fileName, configFile, Collections.emptyList() );
        } catch ( IOException e ) { e.printStackTrace(); }

        reloadConfig();
    }
}