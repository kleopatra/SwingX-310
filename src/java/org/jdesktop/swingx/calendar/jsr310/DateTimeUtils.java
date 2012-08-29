/*
 * Created on 12.01.2009
 *
 */
package org.jdesktop.swingx.calendar.jsr310;

import static javax.time.calendrical.DateTimeAdjusters.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.time.DayOfWeek;
import javax.time.LocalDateTime;
import javax.time.LocalTime;
import javax.time.calendrical.DateTimeField;
import javax.time.calendrical.LocalDateTimeField;
import javax.time.calendrical.WeekDefinition;
import javax.time.format.DateTimeFormatters;
import javax.time.format.DateTimeTextProvider;
import javax.time.format.TextStyle;


/**
 * Utility methods for jsr310 api. 
 */
public class DateTimeUtils {

    
//----------------------------- formatting/parsing
    
    public static Map<DayOfWeek, String> getShortDayOfWeekTexts(Locale locale) {
        Map<DayOfWeek, String> daysOfTheWeek = new HashMap<DayOfWeek, String>();
        DateTimeTextProvider provider = DateTimeFormatters.getTextProvider();
        for (DayOfWeek day : DayOfWeek.values()) {
            daysOfTheWeek.put(day, provider.getText(LocalDateTimeField.DAY_OF_WEEK, 
                    day.getValue(), TextStyle.SHORT, locale));
        }
        return daysOfTheWeek;
    }
 
    
// ------------------------- temporary workaround
    
    /**
     * Returns the first day of the week for the default locale 
     * (as returned by Locale.getDefault()).
     * 
     * @return
     */
    public static DayOfWeek getFirstDayOfWeek() {
        return getFirstDayOfWeek(Locale.getDefault());
    }
    
    /**
     * Returns the first day of the week for the given locale. Internally queries
     * the WeekContext.
     * 
     * @param locale the Locale to look up the first day of week for, must not be null.
     * @return
     */
    public static DayOfWeek getFirstDayOfWeek(Locale locale) {
        WeekDefinition context = WeekDefinition.of(locale);
        return context.getFirstDayOfWeek();
//        Calendar calendar = Calendar.getInstance(locale);
//        int first = calendar.getFirstDayOfWeek();
//        return DayOfWeek.of(first > 1 ? first - 1 : 7);
    }
    
//    public static int getMinimalDaysInFirstWeek(Locale locale) {
//        return Calendar.getInstance(locale).getMinimalDaysInFirstWeek();
//    }

//------------------------------ date calculation
    
    public static LocalDateTime startOf(LocalDateTime dateTime, DateTimeField field) {
        if (field instanceof LocalDateTimeField) {
            return startOf(dateTime, (LocalDateTimeField) field);
        }
        throw new UnsupportedOperationException("fields != LocalDateTimeField not yet implemented: " + field);
    }
    
    public static LocalDateTime startOf(LocalDateTime dateTime, LocalDateTimeField field) {
        switch (field) {
            case DAY_OF_MONTH:
                return startOfDay(dateTime);
            case MONTH_OF_YEAR:
                return startOfMonth(dateTime);
            case YEAR:
                return startOfYear(dateTime);
            }
        throw new UnsupportedOperationException("yet unsupported field: " + field);
    }
    
    public static LocalDateTime startOfYear(LocalDateTime dateTime) {
        return startOfDay(dateTime.with(firstDayOfYear()));
        
    }
    
    /**
     * Returns a LocalDateTime representing the end of the last day in 
     * the month of the given dateTime
     * 
     * @param dateTime 
     * @return
     */
    public static LocalDateTime endOfMonth(LocalDateTime dateTime) {
        LocalDateTime startOfNextMonth = startOfMonth(dateTime).plusMonths(1);
        return startOfNextMonth.minusNanos(1L);
    }
    
    /**
     * Returns a LocalDateTime representing the start of day of the 
     * first day of the week in the default Locale.
     * 
     * @param dateTime the dateTtime to adjust
     * @return the start of day of the first day of week
     */
    public static LocalDateTime startOfWeek(LocalDateTime dateTime) {
        return startOfWeek(dateTime, Locale.getDefault());
    }
    
    /**
     * Returns a LocalDateTime representing the start of day of the first
     * day of the week in the given Locale.
     * 
     * @param dateTime the dateTime to adjust
     * @param locale the Locale to use
     * @return the start of day of the first day of week
     */
    public static LocalDateTime startOfWeek(LocalDateTime dateTime,
            Locale locale) {
        DayOfWeek first = getFirstDayOfWeek(locale);
        dateTime = dateTime.with(previousOrCurrent(first));
        return startOfDay(dateTime);
    }

    /**
     * Returns a LocalDateTime representing the start of day of the first
     * day of the month.
     * 
     * @param dateTime
     * @return
     */
    public static LocalDateTime startOfMonth(LocalDateTime dateTime) {
        return startOfDay(dateTime.with(firstDayOfMonth()));
    }

    public static boolean isStartOfMonth(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth()== 1 
                // JW: working because it's cached - shouldn't rely on that implementation detail
            && LocalTime.MIDNIGHT.equals(dateTime.toLocalTime());
    }
//    
//    public static LocalDateTime startOf(LocalDateTime dateTime, DateTimeField field) {
//        LocalDateTime date = startOfDay(dateTime);
//        if (field == DAY_OF_MONTH) {
//           return date; 
//        }
//        date = date.with(DateTimeAdjusters.firstDayOfMonth());
//        if (field == )
//        return date;
//    }
    

    /**
     * @param dateTime
     * @return
     */
    public static LocalDateTime startOfDay(LocalDateTime dateTime) {
        return LocalDateTime.ofMidnight(dateTime.toLocalDate());
    }
    


    /**
     * Returns true if the given dates are the same to the precision of the field.
     * F.i. if they are on the same day with different hours, this will return 
     * true for a DaY_OF_MONTH and false for HOUR_OF_DAY.
     * 
     * @param one one of the dates to compare
     * @param other the other of the dates to compare
     * @param field the precision of the comparison
     * @return
     */
    public static boolean isSame(LocalDateTime one, LocalDateTime other,
            DateTimeField field) {
        if (field instanceof LocalDateTimeField) {
            return isSame(one, other, (LocalDateTimeField) field);
        }
        return false;
    };
    
    private static boolean isSame(LocalDateTime one, LocalDateTime other,
            LocalDateTimeField field) {
        
        LocalDateTimeField[] values = LocalDateTimeField.values();
        for (int i = field.ordinal(); i < values.length; i++ ) {
            LocalDateTimeField element = values[i];
            if (element.compare(one, other) != 0) return false;
        }
        return true;
    };
    
    
    
    private DateTimeUtils()  { }
}
