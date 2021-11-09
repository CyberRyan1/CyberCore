package com.github.cyberryan1.cybercore.utils.yml;

import com.github.cyberryan1.cybercore.managers.YMLManager;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collections;

public class YMLReadTemplate {

    protected YMLManager ymlManager;

    public void setYMLManager( YMLManager manager ) {
        ymlManager = manager;
    }

    public YMLManager getYMLManager() {
        return ymlManager;
    }

    public FileConfiguration getConfig() { return ymlManager.getConfig(); }

    public boolean getBool( String path ) {
        checkPath( path );
        System.out.println( "test" );
        return getConfig().getBoolean( path );
    }

    public int getInt( String path ) {
        checkPath( path );
        return getConfig().getInt( path );
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
        if ( getConfig().get( path ) == null ) {
            CoreUtils.logError( "Config path \"" + path + "\" was not found, please check the \"" + ymlManager.getFileFor().toString() + "\" file" );
        }
    }
}