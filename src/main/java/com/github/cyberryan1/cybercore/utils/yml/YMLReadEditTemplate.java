package com.github.cyberryan1.cybercore.utils.yml;

/**
 * Allows one to also edit the YML file
 * Inherits all methods from the {@link YMLReadTemplate}
 * @see YMLReadTemplate
 */
public class YMLReadEditTemplate extends YMLReadTemplate {

    public void save() {
        ymlManager.saveConfig();
        ymlManager.initialize();
    }

    public void set( String path, Object value ) {
        getConfig().set( path, value );
    }
}