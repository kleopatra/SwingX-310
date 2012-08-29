/*
 * Created on 01.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;
import static org.jdesktop.swingx.calendar310.Page.*;

import javax.time.LocalDate;
import javax.time.LocalDateTime;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Jeanette Winzenburg, Berlin
 */
@RunWith(JUnit4.class)
public class PageTest extends TestCase {
    
    private LocalDateTime calendar;
    private LocalDateTime today;
    private LocalDateTime tomorrow;

    @Test
    public void testDayInMonthCellContained() {
        assertTrue("must be same day " + calendar + " / " + today, 
                MONTH_PAGE.isContainedInCell(calendar, today));
        assertFalse("must not be same day " + calendar + " / " + tomorrow, 
                MONTH_PAGE.isContainedInCell(calendar, tomorrow));
    }
    
    @Test
    public void testDayInMonthPageContained() {
        assertTrue("must be same month " + calendar + " / " + today, 
                MONTH_PAGE.isContainedInPage(calendar, today));
        LocalDateTime nextMonth = today.plusMonths(1);
        assertFalse("must not be same month " + calendar + " / " + nextMonth, 
                MONTH_PAGE.isContainedInPage(calendar, nextMonth));
   }
    
    @Test
    public void testYearMax() {
        assertEquals(12, YEAR_PAGE.getMaximumValue(calendar));
    }
    
    @Test
    public void testYearMin() {
        assertEquals(1, YEAR_PAGE.getMinimumValue(calendar));
    }
    
    @Test
    public void testMonthMax() {
        LocalDate date = calendar.toLocalDate();
        assertEquals(date.lengthOfMonth(), MONTH_PAGE.getMaximumValue(calendar));
    }
    
    @Test
    public void testMonthMin() {
        assertEquals(1, MONTH_PAGE.getMinimumValue(calendar));
    }
    
    @Test
    public void testMonthAddPage() {
        assertAddPage(MONTH_PAGE, 1);
    }

    @Test
    public void testMonthSubPage() {
        assertAddPage(MONTH_PAGE, -1);
    }

    @Test
    public void testYearAddPage() {
        assertAddPage(YEAR_PAGE, 1);
    }

    @Test
    public void testYearSubPage() {
        assertAddPage(YEAR_PAGE, -1);
    }
    
    @Test
    public void testDecadeAddPage() {
//        assertAddPage(DECADE_PAGE, 1);
//        assertAddPage(DECADE_PAGE, -1);
    }

    protected void assertAddPage(Page page, int amount) {
        LocalDateTime date = page.addPage(calendar, amount);
        long month = calendar.get(page.pageField);
        long day = calendar.get(page.cellField);
        assertEquals(month + amount, date.get(page.pageField));
        assertEquals(day, date.get(page.cellField));
    }

    @Before
    @Override
    public void setUp() throws Exception {
        today = LocalDateTime.now();
        tomorrow = today.plusDays(1);
        calendar = today.withHour(11); 
    }

    
}
