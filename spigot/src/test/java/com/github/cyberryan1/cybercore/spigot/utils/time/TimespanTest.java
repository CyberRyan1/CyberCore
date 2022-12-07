package com.github.cyberryan1.cybercore.spigot.utils.time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimespanTest {

    @Test
    void toPrettyStringOneWeek() {
        Timespan timespan = new Timespan( TimeUnit.WEEKS, 1 );
        assertEquals( "1 week", timespan.toPrettyString() );
    }

    @Test
    void toPrettyStringOneWeekOneDay() {
        Timespan timespan = new Timespan( TimeUnit.WEEKS, 1 );
        timespan.add( TimeUnit.DAYS, 1 );
        assertEquals( "1 week and 1 day", timespan.toPrettyString() );
    }

    @Test
    void toPrettyStringOneWeekOneDayThreeHours() {
        Timespan timespan = new Timespan( TimeUnit.WEEKS, 1 );
        timespan.add( TimeUnit.DAYS, 1 );
        timespan.add( TimeUnit.HOURS, 3 );
        assertEquals( "1 week, 1 day, and 3 hours", timespan.toPrettyString() );
    }

    @Test
    void toPrettyStringOneWeekOneDayThreeHoursFiveMinutes() {
        Timespan timespan = new Timespan( TimeUnit.WEEKS, 1 );
        timespan.add( TimeUnit.DAYS, 1 );
        timespan.add( TimeUnit.HOURS, 3 );
        timespan.add( TimeUnit.MINUTES, 5 );
        assertEquals( "1 week, 1 day, 3 hours, and 5 minutes", timespan.toPrettyString() );
    }
}