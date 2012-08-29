/*
 * Created on 01.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import static javax.time.calendrical.LocalDateTimeField.*;
import static javax.time.calendrical.LocalPeriodUnit.*;

import javax.time.LocalDateTime;
import javax.time.calendrical.DateTimeField;
import javax.time.calendrical.DateTimeValueRange;
import javax.time.calendrical.PeriodUnit;

import org.jdesktop.swingx.calendar.jsr310.DateTimeUtils;

/**
 * @author Jeanette Winzenburg, Berlin
 */
public enum Page {

    MONTH_PAGE(MONTH_OF_YEAR, MONTHS, 
            DAY_OF_MONTH, DAYS),
            
    YEAR_PAGE(YEAR, YEARS, MONTH_OF_YEAR, MONTHS),
    
    DECADE_PAGE(null, DECADES, YEAR, YEARS);
    
    public final DateTimeField pageField;
    public final DateTimeField cellField;
    public final PeriodUnit pageUnit;
    public final PeriodUnit cellUnit;
    
    /**
     * @param pageField
     * @param pageUnit
     * @param cellField
     * @param cellUnit
     */
    Page(DateTimeField pageField, PeriodUnit pageUnit,
            DateTimeField cellField, PeriodUnit cellUnit) {
        this.pageField = pageField;
        this.pageUnit = pageUnit;
        this.cellField = cellField;
        this.cellUnit = cellUnit;
    }
    
    public LocalDateTime addPage(LocalDateTime calendar, long amount) {
        return calendar.plus(amount, pageUnit);
    }
    
    public LocalDateTime addCell(LocalDateTime calendar, long amount) {
        return calendar.plus(amount, cellUnit);
    }

    public LocalDateTime getStartOfPage(LocalDateTime calendar) {
        return DateTimeUtils.startOf(calendar, pageField);
    }
    
    public LocalDateTime getStartOfCell(LocalDateTime calendar) {
        return DateTimeUtils.startOf(calendar, cellField);
    }

    /**
     * @param calendar
     * @return
     */
    public int getMaximumValue(LocalDateTime calendar) {
        DateTimeValueRange range = cellField.range(calendar);
        return (int) range.getMaximum();
    }

    /**
     * @param calendar
     * @return
     */
    public int getMinimumValue(LocalDateTime calendar) {
        DateTimeValueRange range = cellField.range(calendar);
        return (int) range.getMinimum();
    }
    
    public int getCellValue(LocalDateTime calendar) {
        return (int) calendar.get(cellField);
    }
    
    public int getPageValue(LocalDateTime calendar) {
        return (int) calendar.get(pageField);
    }

    /**
     * @param pageStart
     * @param firstCell
     * @return
     */
    public int getOffsetToMinimum(LocalDateTime pageStart,
            LocalDateTime firstCell) {
        if (!pageStart.isAfter(firstCell)) return 0;
        if (DateTimeUtils.isSame(pageStart, firstCell, cellField)) return 0;
        // firstCell is at least one unit before
        int amount = 0;
        while (!DateTimeUtils.isSame(pageStart, firstCell, cellField)) {
            pageStart = addCell(pageStart, -1);
            amount++;
        }
        return amount;
    }

    /**
     * Returns a boolean indicating whether the given date is contained in the 
     * Calendar, using the range of the pageType's cell.
     * 
     * @param range the calendar which defines the range
     * @param date the date to check
     * @return a boolean indicating whether the given dat is contained in the
     *   Calendar.
     */
    public boolean isContainedInCell(LocalDateTime range, LocalDateTime date) {
        return isContained(range, date, cellField);
    }

    /**
     * Returns a boolean indicating whether the given date is contained in the 
     * Calendar, using the range of the pageType's cell.
     * 
     * @param range the calendar which defines the range
     * @param date the date to check
     * @return a boolean indicating whether the given dat is contained in the
     *   Calendar.
     */
    public boolean isContainedInPage(LocalDateTime range, LocalDateTime date) {
        return isContained(range, date, pageField);
    }

    /**
     * Returns a boolean indicating whether the given date is contained in the 
     * range, using the range of the given field.
     * 
     * @param range the calendar which defines the range
     * @param date the date to check
     * @param field the field to check
     * @return a boolean indicating whether the given dat is contained in the
     *   Calendar.
     */
    private boolean isContained(LocalDateTime range, LocalDateTime date,
            DateTimeField field) {
        return DateTimeUtils.isSame(range, date, field);
    }
}
