/*
 * Created on 05.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import static org.jdesktop.swingx.calendar310.MyDateTimeAdjuster.*;

import java.util.Locale;
import java.util.logging.Logger;

import javax.time.DayOfWeek;
import javax.time.LocalDateTime;
import javax.time.LocalTime;
import javax.time.ZonedDateTime;

import org.jdesktop.swingx.InteractiveTestCase;
import org.jdesktop.swingx.calendar.jsr310.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Jeanette Winzenburg, Berlin
 */
@RunWith(JUnit4.class)
public class MyDateTimeAdjustersTest extends InteractiveTestCase {
    
    private LocalDateTime calendar;

    @Test
    public void testStartOfDecade() {
        LocalDateTime adjusted = calendar.with(START_OF_DECADE);
        assertEquals(LocalTime.MIDNIGHT, adjusted.toLocalTime());
        assertEquals(2010, adjusted.getYear());
        assertEquals(1, adjusted.getDayOfMonth());
        assertEquals(1, adjusted.getMonthValue());
    }
    
    @Test
    public void testStartOfYear() {
        LocalDateTime adjusted = calendar.with(START_OF_YEAR);
        assertEquals(LocalTime.MIDNIGHT, adjusted.toLocalTime());
        assertEquals(1, adjusted.getDayOfMonth());
        assertEquals(1, adjusted.getMonthValue());
    }
    
    @Test
    public void testStartOfMonth() {
        LocalDateTime adjusted = calendar.with(START_OF_MONTH);
        assertEquals(LocalTime.MIDNIGHT, adjusted.toLocalTime());
        assertEquals(1, adjusted.getDayOfMonth());
    }
    
    @Test
    public void testStartOfWeek() {
        DayOfWeek dow = calendar.getDayOfWeek();
        DayOfWeek first = DateTimeUtils.getFirstDayOfWeek();
        if (dow == first) {
            calendar = calendar.plusDays(1);
        }
        assertFalse("sanity: calendar must not be on firstDayOfWee", first == calendar.getDayOfWeek());
        LocalDateTime adjusted = calendar.with(START_OF_WEEK);
        assertEquals(LocalTime.MIDNIGHT, adjusted.toLocalTime());
        assertEquals(first, adjusted.getDayOfWeek());
    }
    
    
    @Test
    public void testStartOfWeekUS() {
        Locale old = Locale.getDefault();
        Locale.setDefault(Locale.US);
        try {

            DayOfWeek dow = calendar.getDayOfWeek();
            DayOfWeek first = DateTimeUtils.getFirstDayOfWeek();
            if (dow == first) {
                calendar = calendar.plusDays(1);
            }
            assertFalse("sanity: calendar must not be on firstDayOfWee",
                    first == calendar.getDayOfWeek());
            LocalDateTime adjusted = calendar.with(START_OF_WEEK);
            assertEquals(LocalTime.MIDNIGHT, adjusted.toLocalTime());
            assertEquals(first, adjusted.getDayOfWeek());
        } finally {
            Locale.setDefault(old);
        }
    }
    
    @Test
    public void testStartOfDayZoned() {
        ZonedDateTime z = ZonedDateTime.now();
        ZonedDateTime adjusted = z.with(START_OF_DAY);
        assertEquals(LocalTime.MIDNIGHT, adjusted.toLocalTime());
    }
    
    @Test
    public void testStartOfDay() {
        LocalDateTime adjusted = calendar.with(START_OF_DAY);
        assertEquals(calendar.toLocalDate(), adjusted.toLocalDate());
        assertEquals(LocalTime.MIDNIGHT, adjusted.toLocalTime());
    }
    @Before
    public void setup() {
        calendar = LocalDateTime.now();
    }
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger
            .getLogger(MyDateTimeAdjustersTest.class.getName());
}
