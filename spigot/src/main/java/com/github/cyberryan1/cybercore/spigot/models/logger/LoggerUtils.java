package com.github.cyberryan1.cybercore.spigot.models.logger;

import com.github.cyberryan1.cybercore.common.models.logger.ICyberLogger;
import com.github.cyberryan1.cybercore.spigot.CyberCore;

import java.util.logging.Level;

public class LoggerUtils implements ICyberLogger {

    private boolean debugLogging = false;

    @Override
    public void logDebug( String msg ) {
        if ( debugLogging ) {
            CyberCore.getPlugin().getLogger().log( Level.INFO, "[DEBUG] " + msg );
        }
    }

    @Override
    public void logInfo( String msg ) {
        CyberCore.getPlugin().getLogger().log( Level.INFO, msg );
    }

    @Override
    public void logWarn( String msg ) {
        CyberCore.getPlugin().getLogger().log( Level.WARNING, msg );
    }

    @Override
    public void logError( String msg ) {
        CyberCore.getPlugin().getLogger().log( Level.SEVERE, msg );
    }

    @Override
    public void setDebugLogging( boolean b ) {
        this.debugLogging = b;
    }

    @Override
    public boolean isLoggingDebug() {
        return this.debugLogging;
    }
}