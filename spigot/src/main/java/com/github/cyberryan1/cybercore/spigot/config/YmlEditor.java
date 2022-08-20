package com.github.cyberryan1.cybercore.spigot.config;

/**
 * Allows one to read and write data from a YML file.
 *
 * @see YmlReader
 * @author CyberRyan1
 */
public class YmlEditor extends YmlReader {

    /**
     * Constructs a new {@link YmlEditor} instance using the
     * provided {@link YmlLoader}
     * @param ymlLoader The {@link YmlLoader} to use
     */
    public YmlEditor( YmlLoader ymlLoader ) {
        super( ymlLoader );
    }

    /**
     * Constructs a new {@link YmlEditor} instance using the
     * provided {@link YmlLoader}
     * @param ymlLoader The {@link YmlLoader} to use
     * @param sendPathNotFoundWarns Whether or not to send warnings when a path is not found
     */
    public YmlEditor( YmlLoader ymlLoader, boolean sendPathNotFoundWarns ) {
        super( ymlLoader, sendPathNotFoundWarns );
    }

    /**
     * Saves the configuration to the file.
     */
    public void save() {
        super.getYmlLoader().saveConfig();
        super.getYmlLoader().reloadConfiguration();
    }

    /**
     * Sets the value of the provided path. To save this, use {@link #save()}
     * @param path The path to set the value of
     * @param value The value to set the path to
     */
    public void set( String path, Object value ) {
        super.getConfig().set( path, value );
    }
}