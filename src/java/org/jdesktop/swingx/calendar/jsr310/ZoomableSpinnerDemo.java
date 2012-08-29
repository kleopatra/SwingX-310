/*
 * Created on 20.01.2008
 *
 */
package org.jdesktop.swingx.calendar.jsr310;

import java.util.logging.Logger;

import org.jdesktop.swingx.InteractiveTestCase;
import org.jdesktop.swingx.JXMonthView;

public class ZoomableSpinnerDemo extends InteractiveTestCase {
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(ZoomableSpinnerDemo.class
            .getName());
    
    public static void main(String[] args) {
        setSystemLF(true);
            ZoomableSpinnerDemo test = new ZoomableSpinnerDemo();
            try {
              test.runInteractiveTests();
            } catch (Exception e) {
                System.err.println("exception when executing interactive tests:");
                e.printStackTrace();
            } 
        
    }

    


    public void interactiveXMonthView() {
        JXMonthView monthView = new JXMonthView();
        monthView.setZoomable(true);
        showInFrame(monthView, "zoomable monthView");
    }
    
    public void interactiveShowSpinner() {
        JXZoomableSpinner spinner = new JXZoomableSpinner();
        showInFrame(spinner, "demo");
    }
}
