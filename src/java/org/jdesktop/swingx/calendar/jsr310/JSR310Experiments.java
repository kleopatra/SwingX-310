/*
 * Created on 08.01.2009
 *
 */
package org.jdesktop.swingx.calendar.jsr310;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.Format;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.time.LocalDateTime;
import javax.time.Period;
import javax.time.calendrical.DateTime;
import javax.time.calendrical.LocalPeriodUnit;
import javax.time.format.DateTimeFormatter;
import javax.time.format.DateTimeFormatterBuilder;
import javax.time.format.DateTimeFormatters;
import javax.time.format.FormatStyle;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.InteractiveTestCase;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.calendar.jsr310.Date310SelectionModel.SelectionMode;
import org.jdesktop.swingx.calendar310.DatePickerFormatter310;


public class JSR310Experiments extends InteractiveTestCase {
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(JSR310Experiments.class
            .getName());
    
    public static void main(String[] args) {
        JSR310Experiments test = new JSR310Experiments();
        try {
//            test.runInteractiveTests();
//            test.runInteractiveTests("interactive.*310.*");
            test.runInteractive("Field");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Base issue: check behaviour of 2 vs. 4 y
     */
    public void interactiveYearFormat() {
//       JXDatePicker coreFormatPicker = new JXDatePicker(new Date()); 
    }
    
    
    public void interactive310MonthViewGerman() {
        JXMonthView monthView = new JXMonthView();
        monthView.setSelectionMode(SelectionMode.SINGLE_INTERVAL_SELECTION);
//        monthView.setShowingWeekNumber(true);
        monthView.setShowingLeadingDays(true);
        monthView.setShowingTrailingDays(true);
//        monthView.setTraversable(true);
        showInFrame(monthView, "monthView with 310: German");
    }
    
    public void interactive310MonthViewUS() {
        
        JXMonthView monthView = new JXMonthView();
        monthView.setLocale(Locale.US);
//        monthView.setShowingWeekNumber(true);
        monthView.setShowingLeadingDays(true);
        monthView.setShowingTrailingDays(true);
//        monthView.setTraversable(true);
        showInFrame(monthView, "monthView with 310: US");
    }
    
    public void interactiveMonthSymbols() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatters.pattern("MMMM", Locale.FRANCE);
        Format format = formatter.toFormat();
        for (int i = 1; i <= 12; i++) {
            date = date.withMonth(i);
            LOG.info("localized month: " + format.format(date));
        }
    }
    
//    public void interactiveZoomableSpinner() {
//        JXZoomableSpinner spinner = new JXZoomableSpinner();
//        showInFrame(spinner, "zoomable");
//    }
 
    public void interactiveDateSpinner() {
        OffsetDateTimeSpinnerModel model = new OffsetDateTimeSpinnerModel(
                Period.of(1, LocalPeriodUnit.MONTHS));
        JSpinner spinner = new JSpinner(model);
        JComponent box = Box.createHorizontalBox();
        box.add(spinner);

        DateTimeFormatter formatter = DateTimeFormatters.pattern("d");
        Format format = formatter.toFormat();
        JFormattedTextField field = new JFormattedTextField(format);
        field.setValue(model.getValue());
        box.add(field);

        showInFrame(box, "Spinner with JSR310");
    }

    public void interactiveDateFormatter() {
        JComponent box = Box.createHorizontalBox();
        DateTimeFormatter formatter = DateTimeFormatters.pattern("yyyy");
        Format format = formatter.toFormat();
        JFormattedTextField field = new JFormattedTextField(format);
        field.setValue(LocalDateTime.now());
        field.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                LOG.info("field event: " +evt.getPropertyName()+ evt.getNewValue());
                
            }});
        box.add(field);
        showInFrame(box, "text field with formatter");
    }
}
