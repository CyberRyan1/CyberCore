package com.github.cyberryan1.cybercore.spigot.utils.time;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of a timespan
 */
public class Timespan {

    private long seconds = 0L;

    /**
     * Create a new timespan of 0 seconds
     */
    public Timespan() {}

    /**
     * Creates a new timespan
     * @param unit The unit of time (via {@link TimeUnit}) to use
     * @param amount The amount of time to start with
     */
    public Timespan( TimeUnit unit, long amount ) {
        this.seconds = unit.seconds * amount;
    }

    /**
     * Creates a new timespan from the given seconds
     * @param seconds The amount of seconds to start with
     */
    public Timespan( long seconds ) {
        this.seconds = seconds;
    }

    /**
     * Returns the amount of seconds in this timespan
     * @return The amount of seconds in this timespan
     */
    public long getSeconds() {
        return this.seconds;
    }

    /**
     * Gets the amount of time this timespan represents in the given unit
     * @param unit The unit of time to get the amount in
     * @return The amount of time in the given unit
     */
    public double getTimeIn( TimeUnit unit ) {
        return ( this.seconds * 1.0 ) / unit.seconds;
    }

    /**
     * Gets all the amounts for each time unit this timespan represents
     * @return A {@link Map} of {@link TimeUnit} to the amount of time in that unit
     */
    public Map<TimeUnit, Long> getFullTimespan() {
        final Map<TimeUnit, Long> timespans = new HashMap<>();

        long weeks = this.seconds / TimeUnit.WEEKS.seconds;
        timespans.put( TimeUnit.WEEKS, weeks );
        long secondsRemaining = this.seconds % TimeUnit.WEEKS.seconds;

        long days = secondsRemaining / TimeUnit.DAYS.seconds;
        timespans.put( TimeUnit.DAYS, days );
        secondsRemaining = secondsRemaining % TimeUnit.DAYS.seconds;

        long hours = secondsRemaining / TimeUnit.HOURS.seconds;
        timespans.put( TimeUnit.HOURS, hours );
        secondsRemaining = secondsRemaining % TimeUnit.HOURS.seconds;

        long minutes = secondsRemaining / TimeUnit.MINUTES.seconds;
        timespans.put( TimeUnit.MINUTES, minutes );
        secondsRemaining = secondsRemaining % TimeUnit.MINUTES.seconds;
        timespans.put( TimeUnit.SECONDS, secondsRemaining );

        return timespans;
    }

    /**
     * Adds the given timespan to this timespan
     * @param timespan The timespan to add to this timespan
     */
    public void add( Timespan timespan ) {
        this.seconds += timespan.getSeconds();
    }

    /**
     * Adds the given amount of the given unit to this timespan
     * @param unit The unit of time to add
     * @param amount The amount of time to add
     */
    public void add( TimeUnit unit, long amount ) {
        this.seconds += unit.seconds * amount;
    }

    /**
     * Subtracts the given timespan from this timespan
     * @param timespan The timespan to subtract from this timespan
     */
    public void subtract( Timespan timespan ) {
        this.seconds -= timespan.getSeconds();
    }

    /**
     * Subtracts the given amount of the given unit from this timespan
     * @param unit The unit of time to subtract
     * @param amount The amount of time to subtract
     */
    public void subtract( TimeUnit unit, long amount ) {
        this.seconds -= unit.seconds * amount;
    }

    /**
     * @param timespan The timespan to compare to
     * @return True if this timespan is greater than the given timespan
     */
    public boolean isGreater( Timespan timespan ) {
        return this.seconds > timespan.getSeconds();
    }

    /**
     * @param timespan The timespan to compare to
     * @return True if this timespan is less than the given timespan
     */
    public boolean isLess( Timespan timespan ) {
        return this.seconds < timespan.getSeconds();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Timespan timespan = ( Timespan ) o;

        return getSeconds() == timespan.getSeconds();
    }

    @Override
    public int hashCode() {
        return ( int ) ( getSeconds() ^ ( getSeconds() >>> 32 ) );
    }

    /**
     * @return A string representation of the timespan
     */
    @Override
    public String toString() {
        Map<TimeUnit, Long> timespans = this.getFullTimespan();
        String toReturn = "";

        for ( TimeUnit unit : TimeUnit.values() ) {
            if ( timespans.get( unit ) > 0 ) {
                String unitName = unit.toString().toLowerCase();
                if ( timespans.get( unit ) == 1 ) { unitName = unitName.substring( 0, unitName.length() - 1 ); }
                toReturn += timespans.get( unit ) + " " + unitName + " ";
            }
        }

        return toReturn.trim();
    }
}