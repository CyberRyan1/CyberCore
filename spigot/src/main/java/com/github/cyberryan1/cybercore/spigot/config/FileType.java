package com.github.cyberryan1.cybercore.spigot.config;

/**
 * Used to keep track of the different config files.
 *
 * @author CyberRyan
 */
public enum FileType {
    /**
     * Config file. The file that is editable by the user
     */
    CONFIG ( "config.yml" ),
    /**
     * Data file. The file that is used for storage
     */
    DATA ( "data.yml" ),
    /**
     * Custom file. May be used for any needs
     */
    CUSTOM( null );

    private final String fileName;
    FileType( String fileName ) {
        this.fileName = fileName;
    }

    /**
     * Gets the name of the file used for the enum
     * @return Name of the file
     */
    public String getFileName() { return fileName; }
}