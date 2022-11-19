package com.github.cyberryan1.cybercore.spigot.utils.time;

/**
 * Keeps track of a timestamp, in seconds
 */
public class Timestamp {

    private long timestamp;

    /**
     * Creates a new timestamp with the current time
     */
    public Timestamp() {
        this.timestamp = System.currentTimeMillis() / 1000L;
    }

    /**
     * Creates a new timestamp with the given time
     * @param timestamp The time to set the timestamp to
     */
    public Timestamp( long timestamp ) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the timestamp in seconds
     * @return The timestamp in seconds
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Adds the given timespan to this timestamp
     * @param timestamp The timestamp to add to the current timestamp
     */
    public void add( Timestamp timestamp ) {
        this.timestamp += timestamp.getTimestamp();
    }

    /**
     * Adds the given {@link Timespan} to this timestamp
     * @param timespan The {@link Timespan} to add to the current timestamp
     */
    public void add( Timespan timespan ) {
        this.timestamp += timespan.getSeconds();
    }

    /**
     * Subtracts the given timestamp from this timestamp
     * @param timestamp The timestamp to subtract from the current timestamp
     */
    public void subtract( Timestamp timestamp ) {
        this.timestamp -= timestamp.getTimestamp();
    }

    /**
     * Subtracts the given {@link Timespan} from this timestamp
     * @param timespan The {@link Timespan} to subtract from the current timestamp
     */
    public void subtract( Timespan timespan ) {
        this.timestamp -= timespan.getSeconds();
    }

    /**
     * @return True if the current timestamp is before the current time, false otherwise
     */
    public boolean isBeforeNow() {
        return this.timestamp < System.currentTimeMillis() / 1000L;
    }

    /**
     * @return True if the current timestamp is after the current time, false otherwise
     */
    public boolean isAfterNow() {
        return this.timestamp > System.currentTimeMillis() / 1000L;
    }

    /**
     * @param timestamp The timestamp to compare to
     * @return True if this timestamp is before the given timestamp, false otherwise
     */
    public boolean isBefore( Timestamp timestamp ) {
        return this.timestamp < timestamp.getTimestamp();
    }

    /**
     * @param timestamp The timestamp to compare to
     * @return True if this timestamp is after the given timestamp, false otherwise
     */
    public boolean isAfter( Timestamp timestamp ) {
        return this.timestamp > timestamp.getTimestamp();
    }

    /**
     * @param timestamp The timestamp to compare to
     * @return True if this timestamp equals the given timestamp, false otherwise
     */
    public boolean equals( Timestamp timestamp ) {
        return this.timestamp == timestamp.getTimestamp();
    }
}