/*
 * Created on 05.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import static javax.time.calendrical.DateTimeAdjusters.*;
import static javax.time.calendrical.LocalDateTimeField.*;
import static org.jdesktop.swingx.calendar310.MyDateTimeField.*;

import javax.time.DayOfWeek;
import javax.time.calendrical.AdjustableDateTime;
import javax.time.calendrical.DateTimeAdjuster;
import javax.time.calendrical.DateTimeField;

import org.jdesktop.swingx.calendar.jsr310.DateTimeUtils;

/**
 * @author Jeanette Winzenburg, Berlin
 */
public enum MyDateTimeAdjuster implements DateTimeAdjuster {
    START_OF_DAY(null),
    START_OF_WEEK(DAY_OF_WEEK),
    START_OF_MONTH(DAY_OF_MONTH),
    START_OF_YEAR(DAY_OF_YEAR),
    START_OF_DECADE(YEAR_OF_DECADE);

    private final DateTimeField startField;
    
    private MyDateTimeAdjuster(DateTimeField startField) {
        this.startField = startField;
        
    }
    @Override
    public AdjustableDateTime doAdjustment(AdjustableDateTime calendrical) {
        return adjust(startOfDay(calendrical));
    }

    /**
     * @param startOfDay
     * @return
     */
    private AdjustableDateTime adjust(AdjustableDateTime startOfDay) {
        if (startField == null) return startOfDay;
        if (this == START_OF_WEEK) {
            return adjustStartOfWeek(startOfDay);
        }
        if (this == START_OF_DECADE) {
            startOfDay = START_OF_YEAR.adjust(startOfDay);
        }
        long min = startField.range().getMinimum();
        return startOfDay.with(startField, min);
    }

    /**
     * @param startOfDay
     * @return
     */
    private AdjustableDateTime adjustStartOfWeek(AdjustableDateTime startOfDay) {
        DayOfWeek first = DateTimeUtils.getFirstDayOfWeek();
        return previousOrCurrent(first).doAdjustment(startOfDay);
    }
    
    /**
     * @param calendrical
     * @return
     */
    private AdjustableDateTime startOfDay(AdjustableDateTime calendrical) {
        return calendrical.with(NANO_OF_DAY, 0);
    }

}
