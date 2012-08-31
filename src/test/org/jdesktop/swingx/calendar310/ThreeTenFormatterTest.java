/*
 * Created on 31.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.time.LocalDate;
import javax.time.LocalDateTime;
import javax.time.calendrical.DateTime;
import javax.time.format.DateTimeFormatter;
import javax.time.format.DateTimeFormatterBuilder;
import javax.time.format.DateTimeFormatters;
import javax.time.format.FormatStyle;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.InteractiveTestCase;
import org.jdesktop.swingx.JXPanel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Jeanette Winzenburg, Berlin
 */
@RunWith(JUnit4.class)
public class ThreeTenFormatterTest extends InteractiveTestCase {

    @Test
    public void testFormat() throws ParseException {
        Locale old = Locale.getDefault();
        try {
            
        } finally {
            Locale.setDefault(old);
        }
        DateFormat format = DateFormat.getDateInstance(); // default == medium
        Date date = format.parse("1.1.2012");
        
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseLenient()
            .appendLocalized(FormatStyle.MEDIUM, null)
            .toFormatter();
        LOG.info(formatter.print(LocalDate.now()) + "/ " + format.format(date));
        LocalDate localDate = formatter.parse("1.1.2000", LocalDate.class);

    }
    
    public static void main(String[] args) {
        ThreeTenFormatterTest test = new ThreeTenFormatterTest();
        try {
            test.runInteractiveTests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void interactiveTextFieldFormat() {
        DateTimeFormatter longFormat = DateTimeFormatters.pattern("dd.MM.yyyy"); 
        DateTimeFormatter shortFormat = DateTimeFormatters.pattern("dd.MM.yy"); 
        
        JFormattedTextField field = new JFormattedTextField(
                new DatePickerFormatter310(new Format[] {longFormat.toFormat(), shortFormat.toFormat()} )
                );
        field.setValue(LocalDateTime.now());
        field.setColumns(20);
        JComponent panel = new JPanel();
        panel.add(field);
        JTextField second = new JFormattedTextField(shortFormat.toFormat());
        panel.add(second);
        showInFrame(panel, "short/long format");
    }
    
    public void interactiveTextFieldFormatLenient() {
        MigLayout layout = new MigLayout("wrap 2", "[][fill, grow]");
        
        JComponent content = new JXPanel(layout);
        for (FormatStyle format : FormatStyle.values()) {
            addWithLenient(content, format);
        }
        
        JFormattedTextField old = new JFormattedTextField(new Date());
        content.add(new JLabel("old"));
        content.add(old);
        showInFrame(content, "builder (lenient?)");
    }
    
    /**
     * @param content
     * @param format
     */
    private void addWithLenient(JComponent content, FormatStyle format) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseLenient()
            .appendLocalized(format, null)
            .toFormatter();
        Format f = formatter.toFormat();
        content.add(new JLabel(format.toString()));
        JFormattedTextField field = new JFormattedTextField(f);
        field.setValue(LocalDateTime.now());
        field.setColumns(20);
        content.add(field);
        
    }
    
    public void interactiveSimpleField() {
        final JTextField field = new JTextField(20);
        final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseLenient()
            .appendLocalized(FormatStyle.MEDIUM, null)
            .toFormatter();
        Action action = new AbstractAction("") {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTime date = formatter.parse(field.getText(), LocalDate.class);
                LOG.info("date: " + date);
            }
        };
        field.setAction(action);
        
        JComponent content = new JPanel();
        content.add(new JLabel("Input date: "));
        content.add(field);
        showInFrame(content, "lenient?");
        
    }
    
    public void interactiveTextFieldFormatAsIs() {
        MigLayout layout = new MigLayout("wrap 2", "[][fill, grow]");

        JComponent content = new JXPanel(layout);
        for (FormatStyle format : FormatStyle.values()) {
            addWithFormatters(content, format);
        }
        
        JFormattedTextField old = new JFormattedTextField(new Date());
        content.add(new JLabel("old"));
        content.add(old);
        showInFrame(content, "DateTimeFormatters");
    }

    /**
     * @param content
     * @param format
     */
    private void addWithFormatters(JComponent content, FormatStyle format) {
        Format f = DateTimeFormatters.localizedDate(format).toFormat();
        content.add(new JLabel(format.toString()));
        JFormattedTextField field = new JFormattedTextField(f);
        field.setValue(LocalDateTime.now());
        field.setColumns(20);
        content.add(field);
        
    }


    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(ThreeTenFormatterTest.class
            .getName());
}
