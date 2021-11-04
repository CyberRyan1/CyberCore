package com.github.cyberryan1.cybercore.managers;

public enum FileType {
    CONFIG ( "config.yml" ),
    DATA ( "data.yml" );

    private final String fileName;
    FileType( String fileName ) {
        this.fileName = fileName;
    }
    public String getFileName() { return fileName; }
}