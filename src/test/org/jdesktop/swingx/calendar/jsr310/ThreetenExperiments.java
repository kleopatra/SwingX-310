/*
 * Created on 19.07.2012
 *
 */
package org.jdesktop.swingx.calendar.jsr310;

import static javax.time.calendrical.LocalDateTimeField.*;

import java.util.Locale;
import java.util.logging.Logger;

import javax.time.LocalDateTime;
import javax.time.LocalTime;
import javax.time.calendrical.DateTimeAdjusters;
import javax.time.format.DateTimeFormatter;
import javax.time.format.DateTimeFormatterBuilder;
import javax.time.format.DateTimeFormatters;

import org.jdesktop.swingx.InteractiveTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * @author Jeanette Winzenburg, Berlin
 */
@RunWith(JUnit4.class)
public class ThreetenExperiments extends InteractiveTestCase {

    public static void main(String[] args) {
        ThreetenExperiments test = new ThreetenExperiments();
        try {
            test.runInteractiveTests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void interactiveFormatYear() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("MMMM")
            .appendLiteral(" ")
            .appendPattern("yyyy")
            .toFormatter();
        LOG.info(formatter.print(LocalDateTime.now()) + " start day of week:" + DateTimeUtils.getFirstDayOfWeek(Locale.getDefault()));
    }
    
    public void interactiveFormatDayOfWeek() {
        String pattern = "E";
        LocalDateTime time = LocalDateTime.now();
        String result = "";
        for (int i = 0; i < 5; i++) {
            DateTimeFormatter formatter = DateTimeFormatters.pattern(pattern); //, Locale.ENGLISH);
            result += pattern + " - " + formatter.print(time) + "\n";
            pattern += "E";
        }
        LOG.info(result);
    }
    
    public void interactiveFormatMonth() {
        String pattern = "M";
        LocalDateTime time = LocalDateTime.now();
        String result = "";
        for (int i = 0; i < 5; i++) {
            DateTimeFormatter formatter = DateTimeFormatters.pattern(pattern); //, Locale.ENGLISH);
            result += pattern + " - " + formatter.print(time) + "\n";
            pattern += "M";
        }
        LOG.info(result);
    }
    
//------------------------------ unit tests
    
    @Test
    public void testDateTimeField() {
        // 27.oct.2012
        LocalDateTime dt = LocalDateTime.now();
        LocalDateTime start =  dt.with(MONTH_OF_YEAR, 1);
        
        start = dt.withMonth(10).withDayOfMonth(27);//(DateTimeAdjusters.firstDayOfMonth());
        LOG.info("" + start +  start.with(LocalTime.MIDNIGHT));
        
    }
    
    @Test
    public void testWeekOfYear() {
        DateTimeFormatter formatter = DateTimeFormatters.pattern("w");
    }
    
    @Test
    public void testNarrowWeekOfDayPrint() {
        DateTimeFormatter formatter = DateTimeFormatters.pattern("EEEEE", Locale.ENGLISH);
        assertEquals("W", formatter.print(LocalDateTime.now().with(DAY_OF_WEEK, 3)));
    }
    
    @Test
    public void testNarrowMonthPrint() {
        DateTimeFormatter formatter = DateTimeFormatters.pattern("MMMMM", Locale.ENGLISH);
        assertEquals("J", formatter.print(LocalDateTime.now().withMonth(1)));
    }
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger
            .getLogger(ThreetenExperiments.class.getName());
}
