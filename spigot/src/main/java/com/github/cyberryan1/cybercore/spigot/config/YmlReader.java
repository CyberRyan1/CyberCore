package com.github.cyberryan1.cybercore.spigot.config;

import com.github.cyberryan1.cybercore.spigot.utils.CyberColorUtils;
import com.github.cyberryan1.cybercore.spigot.utils.CyberLogUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Allows one to read the data from a YML file.
 *
 * @see YmlLoader
 * @author CyberRyan1
 */
public class YmlReader {

    private final YmlLoader ymlLoader;
    private boolean sendPathNotFoundWarns = true;

    /**
     * Constructs a new {@link YmlReader} instance using the
     * provided {@link YmlLoader}
     * @param ymlLoader The {@link YmlLoader} to use
     */
    public YmlReader( YmlLoader ymlLoader ) {
        this.ymlLoader = ymlLoader;
    }

    /**
     * Constructs a new {@link YmlReader} instance using the
     * provided {@link YmlLoader}
     * @param ymlLoader The {@link YmlLoader} to use
     * @param sendPathNotFoundWarns Whether or not to send warnings when a path is not found
     */
    public YmlReader( YmlLoader ymlLoader, boolean sendPathNotFoundWarns ) {
        this.ymlLoader = ymlLoader;
        this.sendPathNotFoundWarns = sendPathNotFoundWarns;
    }

    /**
     * @return The {@link YmlLoader} instance
     */
    public YmlLoader getYmlLoader() {
        return ymlLoader;
    }

    /**
     * @return The {@link FileConfiguration} provided by the {@link YmlLoader}
     */
    public FileConfiguration getConfig() {
        return ymlLoader.getConfig();
    }

    /**
     * @param sendPathNotFoundErrors Whether or not to send warnings when a path is not found
     */
    public void sendPathNotFoundWarns( boolean sendPathNotFoundErrors ) {
        this.sendPathNotFoundWarns = sendPathNotFoundErrors;
    }

    /**
     * @return Whether or not warnings are sent when a path is not found
     */
    public boolean isSendingPathNotFoundWarns() {
        return sendPathNotFoundWarns;
    }

    /**
     * First checks if the path is valid, then returns the boolean found at the path
     * @param path The path to get the value of
     * @return The boolean found at the path
     */
    public boolean getBool( String path ) {
        checkPath( path );
        return getConfig().getBoolean( path );
    }

    /**
     * First checks if the path is valid, then returns the int found at the path
     * @param path The path to get the value of
     * @return The int found at the path
     */
    public int getInt( String path ) {
        checkPath( path );
        return getConfig().getInt( path );
    }

    /**
     * First checks if the path is valid, then returns the long found at the path
     * @param path The path to get the value of
     * @return The long found at the path
     */
    public long getLong( String path ) {
        checkPath( path );
        return getConfig().getLong( path );
    }

    /**
     * First checks if the path is valid, then returns the float found at the path
     * @param path The path to get the value of
     * @return The float found at the path
     */
    public float getFloat( String path ) {
        checkPath( path );
        return ( float ) getConfig().getDouble( path );
    }

    /**
     * First checks if the path is valid, then returns the double found at the path
     * @param path The path to get the value of
     * @return The double found at the path
     */
    public double getDouble( String path ) {
        checkPath( path );
        return getConfig().getDouble( path );
    }

    /**
     * First checks if the path is valid, then returns the string found at the path
     * @param path The path to get the value of
     * @return The string found at the path
     */
    public String getStr( String path ) {
        checkPath( path );
        return getConfig().getString( path );
    }

    /**
     * First checks if the path is valid, then returns the colored string found at the path
     * @param path The path to get the value of
     * @return The colored string found at the path
     */
    public String getColoredStr( String path ) {
        checkPath( path );
        return CyberColorUtils.getColored( getConfig().getString( path ) );
    }

    /**
     * First checks if the path is valid, then returns the list of strings found at the path
     * @param path The path to get the value of
     * @return The list of strings found at the path
     */
    public String[] getStrList( String path ) {
        checkPath( path );
        String list[] = getConfig().getStringList( path ).toArray( new String[0] );
        return list;
    }

    /**
     * First checks if the path is valid, then returns the list of colored strings found at the path
     * @param path The path to get the value of
     * @return The list of colored strings found at the path
     */
    public String[] getColoredStrList( String path ) {
        String toReturn[] = getStrList( path );
        for ( int index = 0; index < toReturn.length; index++ ) {
            toReturn[index] = CyberColorUtils.getColored( toReturn[index] );
        }
        return toReturn;
    }

    /**
     * First checks if the path is valid, then returns the object found at the path
     * @param path The path to get the value of
     * @return The object found at the path
     */
    public Object get( String path ) {
        checkPath( path );
        return getConfig().get( path );
    }

    /**
     * @return The list of all paths in the config (deep search)
     */
    public ArrayList<String> getAllKeys() {
        ArrayList<String> results = new ArrayList<>();
        String keys[] = getConfig().getKeys( true ).toArray( new String[0] );
        Collections.addAll( results, keys );
        return results;
    }

    /**
     * The list of all paths in the config that start with the provided
     * path (deep search).
     * @param startingPath The path to start the search at (should end with a '.')
     * @return The list of all paths in the config that start with the provided path
     */
    public ArrayList<String> getKeys( String startingPath ) {
        checkPath( startingPath );

        ArrayList<String> results = getAllKeys();
        for ( int index = results.size() - 1; index >= 0; index-- ) {
            String str = results.get( index );
            if ( str.startsWith( startingPath ) == false ) {
                results.remove( index );
            }
            else if ( str.replace( startingPath, "" ).contains( "." ) ) {
                results.remove( index );
            }
        }

        return results;
    }

    /**
     * Checks if the provided path is valid. If the path isn't valid and the
     * {@link #isSendingPathNotFoundWarns()} is true, an error is logged to the console.
     * @param path The path to check
     */
    private void checkPath( String path ) {
        if ( sendPathNotFoundWarns && getConfig().get( path ) == null ) {
            CyberLogUtils.logError( "Config path \"" + path + "\" was not found, please check the \""
                    + ymlLoader.getFileType().toString() + "\" file" );
        }
    }
}