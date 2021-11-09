package com.github.cyberryan1.cybercore.utils.yml;

public class YMLReadEditTemplate extends YMLReadTemplate {

    public void save() { ymlManager.saveConfig(); }

    public void set( String path, Object value ) {
        getConfig().set( path, value );
    }
}