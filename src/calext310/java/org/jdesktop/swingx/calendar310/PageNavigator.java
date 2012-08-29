/*
 * Created on 01.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import static org.jdesktop.swingx.calendar310.Page.*;

import javax.time.LocalDateTime;

import org.jdesktop.swingx.calendar.jsr310.DateTimeUtils;

/**
 * @author Jeanette Winzenburg, Berlin
 */
public class PageNavigator implements Navigator {

    // constants for vertical navigation - context and layout dependent?
    private int verticalCellUnit;
    private int verticalPageUnit;

    private Page page;
    
    /**
     * Keeps the current selection.
     */
    protected LocalDateTime calendar;
    private int rowCount;

    /**
     * Instantiates a default navigator on a MONTH_PAGE with calendar set to now.
     */
    public PageNavigator() {
        this(LocalDateTime.now());
    }
    
    /**
     * Instantiates a default navigator on a MONTH_PAGE with calendar set to 
     * the given dateTime.
     */
    public PageNavigator(LocalDateTime calendar) {
        this(calendar, MONTH_PAGE, 7, 5);
    }
    
    
    
    /**
     * @param calendar
     * @param page
     * @param verticalCellUnit
     * @param verticalCellMultiplier
     */
    protected PageNavigator(LocalDateTime calendar, Page page, int verticalCellUnit, int verticalCellMultiplier) {
        this.page = page;
        this.verticalCellUnit = verticalCellUnit;
        this.verticalPageUnit = verticalCellMultiplier * verticalCellUnit;
        this.rowCount = verticalCellMultiplier + 1;
        setLead(calendar);
    }

     
    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public LocalDateTime getStartOfPage() {
        return page.getStartOfPage(getLead());
    }

    @Override
    public LocalDateTime getFirstCell() {
        LocalDateTime dateTime = getStartOfPage();
        // JW: PENDING special casing is hacking ...
        if (MONTH_PAGE == page) {
            // need to pass in the locale
            return DateTimeUtils.startOfWeek(dateTime);
        }
        if (DECADE_PAGE == page) {
            return page.addCell(dateTime, -1);
        }
        return dateTime;
    }

    
    @Override
    public int getOffsetToMinimumLead() {
        LocalDateTime pageStart = getStartOfPage();
        LocalDateTime firstCell = getFirstCell();
        return page.getOffsetToMinimum(pageStart, firstCell);
    }

    @Override
    public LocalDateTime getLead() {
        return calendar;
    }

    @Override
    public void setLead(LocalDateTime dateTime) {
        calendar = dateTime;
    }

    @Override
    public int getLeadValue() {
        return page.getCellValue(calendar);
    }

    @Override
    public void setLeadValue(int leadValue) {
        
    }

    @Override
    public int getMaximumLeadValue() {
        return page.getMaximumValue(calendar);
    }

    @Override
    public int getMinimumLeadValue() {
        return page.getMinimumValue(calendar);
    }

    @Override
    public int getColumnCount() {
        return verticalCellUnit;
    }
    
    @Override
    public int getRowCount() {
        return rowCount;
    }

    
    @Override
    public LocalDateTime getCellValue(int row, int column) {
        checkCoordinates(row, column);
        // coordinates are 1-based, zero and 1 have the same result
        row = Math.max(row, 1);
        column = Math.max(column, 1);
        return page.addCell(getFirstCell(), (row - 1) * getColumnCount() + column - 1);
    }

    /**
     * @param row
     * @param column
     */
    private void checkCoordinates(int row, int column) {
        // TODO Auto-generated method stub
        if (row < 0 || row > getRowCount()) 
            throw new IllegalArgumentException("row must be in range [0, " + getRowCount() + "], but was: " +row);
        if (column < 0 || column > getColumnCount()) 
            throw new IllegalArgumentException("column must be in range [0, " + getColumnCount() + "], but was: " +column);
    }

    @Override
    public int getVerticalPageUnit() {
        return verticalPageUnit;
    }

    @Override
    public void nextCell() {
        calendar = page.addCell(calendar, 1);
    }

    @Override
    public void previousCell() {
        calendar = page.addCell(calendar, -1);
    }

    @Override
    public void nextPage() {
        calendar = page.addPage(calendar, 1);
    }

    @Override
    public void previousPage() {
        calendar = page.addPage(calendar, -1);
    }

    @Override
    public void lowerCell() {
        calendar = page.addCell(calendar, verticalCellUnit);
    }

    @Override
    public void upperCell() {
        calendar = page.addCell(calendar, -verticalCellUnit);
    }

    @Override
    public void lowerPage() {
        // PENDING JW: in terms of cell units to remain in same column?
        calendar = page.addCell(calendar, verticalPageUnit);
    }

    @Override
    public void upperPage() {
        // PENDING JW: in terms of cell units to remain in same column?
        calendar = page.addCell(calendar, -verticalPageUnit);
    }

}
