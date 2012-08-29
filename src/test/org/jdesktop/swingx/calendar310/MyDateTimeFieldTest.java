/*
 * Created on 04.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import static javax.time.calendrical.LocalDateTimeField.*;
import static javax.time.calendrical.LocalPeriodUnit.*;
import static org.jdesktop.swingx.calendar310.MyDateTimeField.*;

import java.util.logging.Logger;

import javax.time.CalendricalException;
import javax.time.LocalDateTime;
import javax.time.LocalTime;

import org.jdesktop.swingx.InteractiveTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
/**
 * @author Jeanette Winzenburg, Berlin
 */
@RunWith(JUnit4.class)
public class MyDateTimeFieldTest extends InteractiveTestCase {

    private LocalDateTime calendar;

    @Test(expected = CalendricalException.class)
    public void testDecadeOnTime() {
        // date related fields invalid for time 
        LocalTime time = calendar.toLocalTime();
        time.get(DECADE);
    }

    @Test(expected= CalendricalException.class)
    public void testYearOfDecadeInvalidSet() {
        calendar.with(YEAR_OF_DECADE, 11);
    }
    
    @Test (expected= CalendricalException.class)
    public void testDayOfWeekInvalidSet() {
        calendar.with(DAY_OF_WEEK, 11);
    }
    
    @Test
    public void testYearOfDecadeSet() {
        int yearOfDecade = (int) calendar.get(YEAR_OF_DECADE);
        LocalDateTime other = calendar.plusYears(1);
        assertEquals(other, calendar.with(YEAR_OF_DECADE, yearOfDecade + 1));
    }

    @Test
    public void testYearOfDecade() {
        int year = calendar.getYear();
        int yearOfDecade = yearOfDecade(year);
        assertEquals(yearOfDecade, calendar.get(YEAR_OF_DECADE));
    }
    
    @Test
    public void testSameYearOfDecade() {
        LocalDateTime other = calendar.plusYears(10);
        assertEquals(0, YEAR_OF_DECADE.compare(calendar, other));
    }
    
    @Test
    public void testLesserYearOfDecade() {
        LocalDateTime other = calendar.plusYears(-9);
        assertEquals(-1, YEAR_OF_DECADE.compare(calendar, other));
    }
    
    @Test
    public void testGreaterYearOfDecade() {
        LocalDateTime other = calendar.plusYears(9);
        assertEquals(1, YEAR_OF_DECADE.compare(calendar, other));
    }
    

    @Test
    public void testDecadeSet() {
        int decade = (int) calendar.get(DECADE);
        LocalDateTime other = calendar.plus(1, DECADES);
        assertEquals(other, calendar.with(DECADE, decade + 10));
    }
    
    @Test
    public void testDecade() {
        int year = calendar.getYear();
        int decade = decade(year);
        assertEquals(decade, calendar.get(DECADE));
    }
    
    @Test
    public void testDecadeCornerCase() {
        int year = 2010;
        calendar = calendar.withYear(2010);
        assertEquals(year, calendar.get(DECADE));
    }
    
    @Test
    public void testSameDecade() {
        LocalDateTime other = calendar.plusYears(1);
        assertEquals(0, DECADE.compare(calendar, other));
    }
    
    @Test
    public void testLesserDecade() {
        LocalDateTime other = calendar.plusYears(10);
        assertEquals(-1, DECADE.compare(calendar, other));
    }
    
    @Test
    public void testGreaterDecade() {
        LocalDateTime other = calendar.plusYears(-10);
        assertEquals(1, DECADE.compare(calendar, other));
    }
    
    private int yearOfDecade(int year) {
        return year % 10;
    }
    
    private  int decade(int year) {
        return (year / 10) * 10;
    }

    @Before
    public void setup() {
        calendar = LocalDateTime.now();
    }
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger
            .getLogger(MyDateTimeFieldTest.class.getName());
}
