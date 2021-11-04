package com.github.cyberryan1.cybercore.utils.yml;

public class YMLReadEditTemplate extends YMLReadTemplate {

    public static void save() { ymlManager.saveConfig(); }

    public static void set( String path, Object value ) {
        getConfig().set( path, value );
    }
}