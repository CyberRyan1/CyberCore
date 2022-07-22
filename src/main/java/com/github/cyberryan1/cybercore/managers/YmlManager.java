package com.github.cyberryan1.cybercore.managers;

import com.github.cyberryan1.cybercore.CyberCore;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.vyarus.yaml.updater.YamlUpdater;
import ru.vyarus.yaml.updater.report.UpdateReport;

import java.io.*;

public class YmlManager {

    public FileType type;

    private String mainFileName;
    private String defaultFileName;
    private File mainFile;
    private File defaultFile;
    private FileConfiguration mainConfig;

    /**
     * Creates a new YMLManager and assumes everything to be null
     */
    public YmlManager() {
        this.type = null;
    }

    /**
     * Creates a new YMLManager of the specified {@link FileType}
     * @param type FileType of the file this represents
     */
    public YmlManager( FileType type ) {
        this.type = type;
        this.mainFileName = type.getFileName();
        this.mainFile = new File( CyberCore.getPlugin().getDataFolder(), this.mainFileName );
        this.defaultFileName = this.mainFileName.replace( ".yml", "_default.yml" );
        this.defaultFile = new File( CyberCore.getPlugin().getDataFolder(), this.defaultFileName );
    }

    /**
     * Creates a new YMLManager with the specified file name. File name should not include the directory
     * To the plugin.
     * @param fileName Name of the file this represents
     */
    public YmlManager( String fileName ) {
        this.type = FileType.CUSTOM;
        this.mainFileName = fileName;
        this.defaultFileName = this.mainFileName.replace( ".yml", "_default.yml" );
    }

    /**
     * Creates and updates the config files, as needed
     * Also initializes the FileConfiguration
     */
    public void initialize() {
        // Save the up-to-date default file
        CyberCore.getPlugin().saveResource( this.defaultFileName, true );
        CoreUtils.logInfo( "Saved the default file configuration for the " + this.mainFileName + " file in the " + this.defaultFileName + " file" );

        // Save the main file if it doesn't exist
        if ( this.mainFile.exists() == false ) {
            CoreUtils.logInfo( "The " + this.mainFileName + " file was not found, so a new one is being created..." );
            CyberCore.getPlugin().saveResource( this.mainFileName, false );

            // Copy the default file to the main file
            try {
                byte bytes[] = new byte[8192];
                InputStream fin = CyberCore.getPlugin().getResource( this.defaultFileName );
                FileOutputStream fout = new FileOutputStream( this.mainFile );

                int read = fin.read( bytes );
                while ( read >= 0 ) {
                    fout.write( bytes, 0, read );
                    read = fin.read( bytes );
                }

                fin.close();
                fout.close();
            } catch ( IOException e ) {
                CoreUtils.logError( "An error occurred while trying to create the " + this.mainFileName + " file; see below for details" );
                throw new RuntimeException( e );
            }
            CoreUtils.logInfo( "The " + this.mainFileName + " file was created successfully" );
        }

        // Update the main file, if needed
        else {
            CoreUtils.logInfo( "The " + this.mainFileName + " file was found, so it will be used" );

            if ( this.defaultFile.length() == 0 ) {
                CoreUtils.logWarn( "Since the " + this.defaultFileName + " file is empty, the " + this.mainFileName + " file will not be updated" );
            }

            else {
                CoreUtils.logInfo( "Updating the " + this.mainFileName + " file now..." );
                UpdateReport report = YamlUpdater.create( this.mainFile, this.defaultFile ).update();
                CoreUtils.logInfo( "Successfully updated the " + this.mainFileName + " file" );
            }
        }

        // Initialize the file configuration
        this.mainConfig = YamlConfiguration.loadConfiguration( this.mainFile );
    }

    /**
     * Gets the {@link FileConfiguration} of this file
     * @return Configuration of this file
     */
    public FileConfiguration getConfig() {
        if ( mainConfig == null ) { initialize(); }
        return mainConfig;
    }

    /**
     * Saves the configuration to the file
     */
    public void saveConfig() {
        try {
            getConfig().save( this.mainFile );
        } catch ( IOException e ) {
            CoreUtils.logError( "An error occurred while trying to save the " + this.mainFileName + " file; see below for details" );
            throw new RuntimeException( e );
        }
    }

    /**
     * @return The {@link FileType} used
     */
    public FileType getFileType() { return type; }
}