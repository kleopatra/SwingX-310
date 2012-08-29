/*
 * $Id: CalendarState.java 3100 2008-10-14 22:33:10Z rah003 $
 *
 * Copyright 2007 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jdesktop.swingx.calendar310;

import static org.jdesktop.swingx.calendar310.Page.*;

import javax.time.LocalDateTime;

/**
 * States of a cell in a calendar page.
 * 
 * @author Jeanette Winzenburg
 */
public enum CellState {
    TODAY_CELL(true, true),
    DAY_OFF_CELL(true, false),
    DAY_CELL(true, true, MONTH_PAGE, DAY_OFF_CELL),
    WEEK_OF_YEAR_TITLE(false, false),
    DAY_OF_WEEK_TITLE(false, false), 
    PAGE_TITLE(false, false),
    // zoomed states
    MONTH_CELL(true, true, YEAR_PAGE),
    YEAR_OFF_CELL(true, false, DECADE_PAGE),
    YEAR_CELL(true, true, DECADE_PAGE, YEAR_OFF_CELL);
    
    private boolean onPage;
    private boolean content;
    private Page page;
    private CellState offState;
    
    CellState(boolean content, boolean onPage) {
        this(content, onPage, MONTH_PAGE);
    }
    
    CellState(boolean content, boolean onPage, Page page) {
        this(content, onPage, page, null);
    }
    
    CellState(boolean content, boolean onPage, Page page, CellState offState) {
        this.onPage = onPage;
        this.page = page;
        this.content = content;
        this.offState = offState;
    }
    
    public boolean isOnPage() {
        return onPage;
    }
    
    public Page getPage() {
        return page;
    }
    
//    public CalendarCellState getOffState() {
//        return offState;
//    }
    
//    public boolean hasOffState() {
//        return isContent() && offState != null;
//    }
    
    /**
     * Returns a boolean indicating whether the type represents a content state. 
     * If false, the type represents a title/header field.
     * @return true for content types, false for header types
     */
    public boolean isContent() {
        return content;
        
    }
    
    /**
     * Returns a boolean indicating whether the type is content and the date 
     * inside the page.
     * 
     * @return
     */
    public boolean isOnPage(LocalDateTime page, LocalDateTime date) {
        return isContent() && isContainedInPage(page, date);
        
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
        return page.isContainedInCell(range, date);
    }
    
    /**
     * Returns a boolean indicating whether the given date is contained in the 
     * Calendar, using the range of the pageType.
     * 
     * @param range the calendar which defines the range
     * @param date the date to check
     * @return a boolean indicating whether the given dat is contained in the
     *   Calendar.
     */
    public boolean isContainedInPage(LocalDateTime range, LocalDateTime date) {
        return page.isContainedInPage(range, date);
    }
}