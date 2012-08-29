/*
 * Created on 02.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import java.util.logging.Logger;

import javax.time.LocalDateTime;
import javax.time.calendrical.DateTimeField;
import javax.time.calendrical.PeriodUnit;
import javax.time.format.DateTimeFormatter;
import javax.time.format.DateTimeFormatterBuilder;

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
public class PageNavigatorTest extends InteractiveTestCase {
    
    
    public static void main(String[] args) {
        PageNavigatorTest test = new PageNavigatorTest();
        try {
            test.runInteractiveTests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    
//    public void interactiveNavigatorView() {
//        Navigator navigator = createNavigator();
//        NavigatorView view = new NavigatorView(navigator);
//        
//        JComponent box = new JXPanel(new MigLayout());
//        box.add(view.getGridView());
//        showInFrame(box, "direct navigatorView");
//    }
//    
    public void interactiveCalendar() {
        
        showInFrame(new JXCalendar(), "JXCalendar with nav");
    }
    
    public void interactiveVisualizeGrid() {
        Navigator navigator = createNavigator();
        LocalDateTime calendar = navigator.getLead();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("MMMM")
            .appendLiteral(" ")
            .appendPattern("yyyy")
            .toFormatter();
        String grid = formatter.print(calendar);
        LocalDateTime first = navigator.getFirstCell();
        LocalDateTime start = navigator.getStartOfPage();
        LocalDateTime last = start.plus(navigator.getMaximumLeadValue()- 1, navigator.getPage().cellUnit);
        // iterate over rows
        for (int row = 0; row < 6; row++) {
            grid += "\n";
            for (int column = 0; column < 7; column++) {
                if (first.isBefore(start) || first.isAfter(last)) {
                    grid += " * ";
                } else {
                    grid += " " + first.get(navigator.getPage().cellField);
                }
                first = first.plus(1, navigator.getPage().cellUnit);
            }
        }
        
        LOG.info("one month: \n" + grid);
    }

    private Navigator navigator;

    @Test(expected = IllegalArgumentException.class )
    public void testCellValueExceptionNegRow() {
        navigator.getCellValue(-1, 0);
    }
    
    @Test(expected = IllegalArgumentException.class )
    public void testCellValueExceptionGreaterRow() {
        navigator.getCellValue(navigator.getRowCount() + 1, 0);
    }
    
    @Test(expected = IllegalArgumentException.class )
    public void testCellValueExceptionNegColumn() {
        navigator.getCellValue(0, -1);
    }
    
    @Test(expected = IllegalArgumentException.class )
    public void testCellValueExceptionGreaterColumn() {
        navigator.getCellValue(0, navigator.getColumnCount() +1);
    }
    
    @Test
    public void testCellValue() {
        
        LocalDateTime first = navigator.getFirstCell();
        // corner case: very first
        assertEquals(first, navigator.getCellValue(1, 1));
        
        // corner case: last column in first row
        LocalDateTime lastInFirst = navigator.getPage().addCell(first, navigator.getColumnCount() - 1);
        assertEquals(lastInFirst, navigator.getCellValue(1, navigator.getColumnCount()));
        
        LocalDateTime firstInFourth = navigator.getPage().addCell(first, navigator.getColumnCount() * 3);
        assertEquals(firstInFourth, navigator.getCellValue(4, 1));
        
        LocalDateTime lastInThird = navigator.getPage().addCell(firstInFourth, -1);
        assertEquals(lastInThird, navigator.getCellValue(3, navigator.getColumnCount()));
    }
    
    /**
     * Test that cellValue(0, x) has same result as cellValue(1, x)
     */
    @Test
    public void testFirstEqualsZero() {
        
        LocalDateTime first = navigator.getFirstCell();
        for (int column = 0; column <= navigator.getColumnCount(); column++) {
            assertEquals(navigator.getCellValue(0, column), navigator.getCellValue(1, column));
        }
        for (int row = 0; row <= navigator.getRowCount() ; row++) {
            assertEquals(navigator.getCellValue(row, 0), navigator.getCellValue(row, 1));
        }
    }
    
    @Test
    public void testOffsetToMinimum() {
        int offset = getNavigator().getOffsetToMinimumLead();
        LocalDateTime page = getNavigator().getStartOfPage();
        LocalDateTime first = getNavigator().getFirstCell();
        DateTimeField cellField = getNavigator().getPage().cellField;
        PeriodUnit cellUnit = getNavigator().getPage().cellUnit;
        assertTrue(DateTimeUtils.isSame(page, first.plus(offset, cellUnit), cellField));
    }
    
    @Test
    public void testLeadValue() {
        LocalDateTime dateTime = getNavigator().getLead();
        DateTimeField cellField = getNavigator().getPage().cellField;
        long lead = dateTime.get(cellField);
        assertEquals(lead, getNavigator().getLeadValue());
    }
    
    protected Navigator getNavigator() {
        return navigator;
    }
    
    /**
     * @return
     */
    protected Navigator createNavigator() {
        return new PageNavigator();
    }
    

    @Before
    @Override
    public void setUp() throws Exception {
        // TODO Auto-generated method stub
        navigator = createNavigator();
    }

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(PageNavigatorTest.class
            .getName());
}
