/*
 * Created on 04.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import static javax.time.calendrical.LocalDateTimeField.*;
import static javax.time.calendrical.LocalPeriodUnit.*;

import javax.time.DateTimes;
import javax.time.calendrical.DateTime;
import javax.time.calendrical.DateTimeBuilder;
import javax.time.calendrical.DateTimeField;
import javax.time.calendrical.DateTimeValueRange;
import javax.time.calendrical.PeriodUnit;

/**
 * @author Jeanette Winzenburg, Berlin
 */
public enum MyDateTimeField implements DateTimeField {

    DECADE("Decade", DECADES, FOREVER, 
            DateTimeValueRange.of(DateTimes.MIN_YEAR, DateTimes.MAX_YEAR)),
            
    YEAR_OF_DECADE("YearOfDecade", YEARS, YEARS, 
            DateTimeValueRange.of(0, 9));         
    
    private final String name;
    private final PeriodUnit baseUnit;
    private final PeriodUnit rangeUnit;
    private final DateTimeValueRange range;

    private MyDateTimeField(String name, PeriodUnit baseUnit, PeriodUnit rangeUnit, DateTimeValueRange range) {
        this.name = name;
        this.baseUnit = baseUnit;
        this.rangeUnit = rangeUnit;
        this.range = range;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PeriodUnit getBaseUnit() {
        return baseUnit;
    }

    @Override
    public PeriodUnit getRangeUnit() {
        return rangeUnit;
    }

    @Override
    public DateTimeValueRange range() {
        return range;
    }
    
    @Override
    public DateTimeValueRange range(DateTime dateTime) {
        return range();
    }
    
    @Override
    public int compare(DateTime calendrical1, DateTime calendrical2) {
        return DateTimes.safeCompare(calendrical1.get(this), calendrical2.get(this));
    }

    @Override
    public long doGet(DateTime calendrical) {
        long year = calendrical.get(YEAR);
        return this == DECADE ? decade(year) : yearOfDecade(year);
    }

    /**
     * @param year
     * @return
     */
    private long yearOfDecade(long year) {
        return year % 10;
    }

    private long decade(long year) {
        return (year / 10) * 10;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends DateTime> R doSet(R calendrical, long newValue) {
        long decade;
        long yearOfDecade;
        if (this == DECADE) {
            decade = decade(newValue);
            yearOfDecade = calendrical.get(YEAR_OF_DECADE);
        } else {
            checkValidValue(newValue);
            decade = calendrical.get(DECADE);
            yearOfDecade = newValue;
        }
        return (R) calendrical.with(YEAR, decade + yearOfDecade);
    }

    /**
     * Checks that the specified value is valid for this field.
     * <p>
     * This validates that the value is within the outer range of valid values
     * returned by {@link #range()}.
     * 
     * @param value  the value to check
     * @return the value that was passed in
     */
    public long checkValidValue(long value) {  // JAVA8 default method on interface
        return range().checkValidValue(value, this);
    }

    @Override
    public boolean resolve(DateTimeBuilder builder, long value) {
        // TODO Auto-generated method stub
        return false;
    }

}
