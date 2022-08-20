package com.github.cyberryan1.cybercore.utils.yml;

import com.github.cyberryan1.cybercore.managers.YmlManager;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Allows one to read all data from a YML file
 *
 * @deprecated Finished moving to submodules
 */
public class YMLReadTemplate {

    protected YmlManager ymlManager;
    protected boolean sendPathNotFoundWarns = true;

    public void setYMLManager( YmlManager manager ) {
        ymlManager = manager;
    }

    public YmlManager getYMLManager() {
        return ymlManager;
    }

    public FileConfiguration getConfig() { return ymlManager.getConfig(); }

    public void sendPathNotFoundWarns( boolean b ) { sendPathNotFoundWarns = b; }

    public boolean getBool( String path ) {
        checkPath( path );
        return getConfig().getBoolean( path );
    }

    public int getInt( String path ) {
        checkPath( path );
        return getConfig().getInt( path );
    }

    public long getLong( String path ) {
        checkPath( path );
        return getConfig().getLong( path );
    }

    public float getFloat( String path ) {
        checkPath( path );
        return ( float ) getConfig().getDouble( path );
    }

    public double getDouble( String path ) {
        checkPath( path );
        return getConfig().getDouble( path );
    }

    public String getStr( String path ) {
        checkPath( path );
        return getConfig().getString( path );
    }

    public String getColoredStr( String path ) {
        checkPath( path );
        return CoreUtils.getColored( getConfig().getString( path ) );
    }

    public String[] getStrList( String path ) {
        checkPath( path );
        String list[] = getConfig().getStringList( path ).toArray( new String[0] );
        return list;
    }

    public String[] getColoredStrList( String path ) {
        String toReturn[] = getStrList( path );
        for ( int index = 0; index < toReturn.length; index++ ) {
            toReturn[index] = CoreUtils.getColored( toReturn[index] );
        }
        return toReturn;
    }

    public Object get( String path ) {
        checkPath( path );
        return getConfig().get( path );
    }

    public ArrayList<String> getAllKeys() {
        ArrayList<String> results = new ArrayList<>();
        String keys[] = getConfig().getKeys( true ).toArray( new String[0] );
        Collections.addAll( results, keys );
        return results;
    }

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

    private void checkPath( String path ) {
        if ( sendPathNotFoundWarns && getConfig().get( path ) == null ) {
            CoreUtils.logError( "Config path \"" + path + "\" was not found, please check the \""
                    + ymlManager.getFileType().toString() + "\" file" );
        }
    }
}