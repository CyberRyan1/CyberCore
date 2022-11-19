package com.github.cyberryan1.cybercore.spigot.utils.time;

/**
 * Represents a unit of time
 */
public enum TimeUnit {
    SECONDS ( 1L ),
    MINUTES ( 60L),
    HOURS ( 3600L ),
    DAYS ( 86400L ),
    WEEKS ( 604800L );

    /**
     * The amount of seconds in one unit of this time
     */
    public final long seconds;
    TimeUnit( long ticks ) {
        this.seconds = ticks;
    }
}