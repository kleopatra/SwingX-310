/*
 * Created on 08.01.2009
 *
 */
package org.jdesktop.swingx.calendar.jsr310;

import java.text.Format;

import javax.swing.AbstractSpinnerModel;
import javax.time.OffsetDateTime;
import javax.time.Period;
import javax.time.calendrical.LocalPeriodUnit;

public class OffsetDateTimeSpinnerModel extends AbstractSpinnerModel {

    private OffsetDateTime dateTime;
    private Period provider;
    private Format format;
    private String name;
    
    public OffsetDateTimeSpinnerModel() {
        this((Period) null); //Clock.systemDefaultZone().offsetDateTime());
    }
    
    public OffsetDateTimeSpinnerModel(OffsetDateTime dateTime) {
        this(dateTime, Period.of(1, LocalPeriodUnit.YEARS));
    }
    
    public OffsetDateTimeSpinnerModel(OffsetDateTime dateTime,
            Period provider) {
        this.dateTime = dateTime;
        this.provider = provider;
    }

    public OffsetDateTimeSpinnerModel(Period provider) {
        this(OffsetDateTime.now(), provider);
    }

    @Override
    public Object getNextValue() {
        return dateTime.plus(provider);
    }

    @Override
    public Object getPreviousValue() {
        return dateTime.minus(provider);
    }

    @Override
    public Object getValue() {
        return dateTime;
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof OffsetDateTime)) return;
        this.dateTime = (OffsetDateTime) value;
        fireStateChanged();
    }

    /**
     * Sets the format to use with the model. Note: quick hack, doesn't belong here! 
     * @param format
     */
    public void setFormat(Format format) {
        this.format = format;
        fireStateChanged();
    }
    
    public Format getFormat() {
        return this.format;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    

    public String getName() {
        return name;
    }
    
}
