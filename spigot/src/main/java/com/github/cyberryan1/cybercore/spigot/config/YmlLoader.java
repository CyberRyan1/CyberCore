package com.github.cyberryan1.cybercore.spigot.config;

import com.github.cyberryan1.cybercore.common.config.FileType;
import com.github.cyberryan1.cybercore.spigot.CyberCore;
import com.github.cyberryan1.cybercore.spigot.utils.CyberLogUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.vyarus.yaml.updater.YamlUpdater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Loads YML files via the following:
 * <ul>
 *     <li>Creates the file if it doesn't exist</li>
 *     <li>If the file already exists, updates it's contents</li>
 * </ul>
 * When the above is completed, the file is loaded into a {@link FileConfiguration} object.
 *
 * @author CyberRyan1
 */
public class YmlLoader {

    private final FileType type;
    private final String mainFileName;
    private final String defaultFileName;
    private File mainFile;
    private File defaultFile;
    private FileConfiguration mainConfig;

    /**
     * Creates a new YMLManager of the specified {@link FileType}
     * @param type FileType of the file this represents
     */
    public YmlLoader( FileType type ) {
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
    public YmlLoader( String fileName ) {
        this.type = FileType.CUSTOM;
        this.mainFileName = fileName;
        this.defaultFileName = this.mainFileName.replace( ".yml", "_default.yml" );
    }

    /**
     * Creates a new YMLManager with the specified main file and default file locations.
     * To include directories, separate them with a forward slash (forward slash = /).
     * Note that the {@link FileType} of this YMLManager will be {@link FileType#CUSTOM}.
     * @param mainFileLocation Location of the main file
     * @param defaultFileLocation Location of the default file
     */
    public YmlLoader( String mainFileLocation, String defaultFileLocation ) {
        this.type = FileType.CUSTOM;

        if ( mainFileLocation.contains( "/" ) == false ) {
            this.mainFileName = mainFileLocation;
        }

        else {
            this.mainFileName = mainFileLocation.substring( mainFileLocation.lastIndexOf( "/" ) + 1 );

            String directory = mainFileLocation
                    .substring( 0, mainFileLocation.lastIndexOf( "/" ) )
                    .replace( "/", File.separator );
            this.mainFile = new File( CyberCore.getPlugin().getDataFolder() + File.separator + directory, this.mainFileName );
        }

        if ( defaultFileLocation.contains( "/" ) == false ) {
            this.defaultFileName = defaultFileLocation;
        }

        else {
            this.defaultFileName = defaultFileLocation.substring( defaultFileLocation.lastIndexOf( "/" ) + 1 );

            String directory = defaultFileLocation
                    .substring( 0, defaultFileLocation.lastIndexOf( "/" ) )
                    .replace( "/", File.separator );
            this.defaultFile = new File( CyberCore.getPlugin().getDataFolder() + File.separator + directory, this.defaultFileName );
        }
    }

    /**
     * Creates and updates the config files, as needed
     * Also initializes the FileConfiguration
     */
    public void initialize() {
        // Save the main file if it doesn't exist
        if ( this.mainFile.exists() == false ) {
            CyberLogUtils.logInfo( "The " + this.mainFileName + " file was not found, so a new one is being created..." );

            // Creating the directory to the main file
            File mainFileDirectory = new File( this.mainFile.getParent() );
            mainFileDirectory.mkdirs();

            // Copy the default file to the main file
            try {
                this.mainFile.createNewFile();

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
                CyberLogUtils.logError( "An error occurred while trying to create the " + this.mainFileName + " file; see below for details" );
                throw new RuntimeException( e );
            }
            CyberLogUtils.logInfo( "The " + this.mainFileName + " file was created successfully" );
        }

        // Update the main file, if needed
        else {
            CyberLogUtils.logInfo( "The " + this.mainFileName + " file was found, so it will be used" );
            CyberLogUtils.logInfo( "Updating the " + this.mainFileName + " file now..." );

            // Getting the default file's contents
            InputStream defaultFileContents = CyberCore.getPlugin().getResource( this.defaultFileName );

            // Updating the main file with the default file
            YamlUpdater.create( this.mainFile, defaultFileContents )
                    .update();

            CyberLogUtils.logInfo( "Successfully updated the " + this.mainFileName + " file" );
        }

        // Initialize the file configuration
        reloadConfiguration();
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
            CyberLogUtils.logError( "An error occurred while trying to save the " + this.mainFileName + " file; see below for details" );
            throw new RuntimeException( e );
        }
    }

    /**
     * Reloads the configuration from the file
     */
    public void reloadConfiguration() {
        this.mainConfig = YamlConfiguration.loadConfiguration( this.mainFile );
    }

    /**
     * @return The {@link FileType} used
     */
    public FileType getFileType() { return type; }
}