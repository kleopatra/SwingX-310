/*
 * Created on 03.08.2012
 *
 */
package org.jdesktop.swingx.calendar310.plaf.basic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.plaf.ComponentUI;
import javax.time.LocalDateTime;

import org.jdesktop.swingx.action.AbstractActionExt;
import org.jdesktop.swingx.calendar310.CellState;
import org.jdesktop.swingx.calendar310.JXCalendar;
import org.jdesktop.swingx.calendar310.Navigator;
import org.jdesktop.swingx.calendar310.Page;
import org.jdesktop.swingx.calendar310.PageNavigator;
import org.jdesktop.swingx.calendar310.plaf.CalendarUI;
import org.jdesktop.swingx.calendar310.plaf.basic.PagingAnimator.Direction;

/**
 * @author Jeanette Winzenburg, Berlin
 */
public class BasicCalendarUI extends CalendarUI {

    protected JXCalendar calendarView;

    private PageNavigator navigator;

    private NavigatorUI navigatorView;
    
    private PagingAnimator pagingAnimator;

    @Override
    public void installUI(JComponent c) {
        this.calendarView = (JXCalendar) c;
        navigator = new PageNavigator();
//        calendarView.setLayout(new MigLayout());
        calendarView.setFocusable(true);
        navigatorView = new NavigatorUI(navigator, this);
        
        installKeyBindings(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        installKeyboardActions();
        
        pagingAnimator = new PagingAnimator(this);
    }

    
    @Override
    public void uninstallUI(JComponent c) {
        calendarView = null;
    }

//    public JComponent getGridView() {
//        return navigatorView.getGridView();
//    }
    
    /**
     * @param inputMap
     */
    private void installKeyBindings(int type) {
        InputMap inputMap = calendarView.getInputMap(type);
        // inputMap.put(KeyStroke.getKeyStroke("ENTER"), JXCalendar.COMMIT_KEY);
        // inputMap.put(KeyStroke.getKeyStroke("ESCAPE"),
        // JXCalendar.CANCEL_KEY);

        // @KEEP quickly check #606-swingx: keybindings not working in
        // internalframe
        // eaten somewhere
        // inputMap.put(KeyStroke.getKeyStroke("F1"), "selectPreviousDay");

        inputMap.put(KeyStroke.getKeyStroke("LEFT"),
                Navigator.PREVIOUS_CELL_KEY);
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), Navigator.NEXT_CELL_KEY);
        inputMap.put(KeyStroke.getKeyStroke("control LEFT"),
                Navigator.PREVIOUS_PAGE_KEY);
        inputMap.put(KeyStroke.getKeyStroke("control RIGHT"),
                Navigator.NEXT_PAGE_KEY);

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), Navigator.LOWER_CELL_KEY);
        inputMap.put(KeyStroke.getKeyStroke("UP"), Navigator.UPPER_CELL_KEY);
        inputMap.put(KeyStroke.getKeyStroke("PAGE_DOWN"),
                Navigator.LOWER_PAGE_KEY);
        inputMap.put(KeyStroke.getKeyStroke("PAGE_UP"),
                Navigator.UPPER_PAGE_KEY);

        inputMap.put(KeyStroke.getKeyStroke("control UP"),
                Navigator.ZOOM_OUT_KEY);
        inputMap.put(KeyStroke.getKeyStroke("control DOWN"),
                Navigator.ZOOM_IN_KEY);

    }

    protected void installKeyboardActions() {
        // Setup the keyboard handler.
        installKeyBindings(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = calendarView.getActionMap();

        AbstractActionExt commit = new AbstractActionExt() {

            @Override
            public void actionPerformed(ActionEvent e) {
                commit();
            }
        };

        AbstractActionExt cancel = new AbstractActionExt() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        };
        // actionMap.put(JXCalendar.COMMIT_KEY, commit);
        // actionMap.put(JXCalendar.CANCEL_KEY, cancel);

        AbstractActionExt nextCell = new AbstractActionExt() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nextCell();
            }

        };

        actionMap.put(Navigator.NEXT_CELL_KEY, nextCell);

        AbstractActionExt previousCell = new AbstractActionExt() {

            @Override
            public void actionPerformed(ActionEvent e) {
                previousCell();
            }
        };
        actionMap.put(Navigator.PREVIOUS_CELL_KEY, previousCell);

        // PENDING JW: complete (year-, decade-, ?? ) and consolidate with
        // KeyboardAction
        // additional navigation actions
        AbstractActionExt prevPage = new AbstractActionExt() {

            public void actionPerformed(ActionEvent e) {
                previousPage();
            }

        };
        actionMap.put(Navigator.PREVIOUS_PAGE_KEY, prevPage);

        AbstractActionExt nextPage = new AbstractActionExt() {

            public void actionPerformed(ActionEvent e) {
                nextPage();
            }

        };

        actionMap.put(Navigator.NEXT_PAGE_KEY, nextPage);

        AbstractActionExt upperPage = new AbstractActionExt() {

            public void actionPerformed(ActionEvent e) {
                upperPage();
            }

        };
        actionMap.put(Navigator.UPPER_PAGE_KEY, upperPage);

        AbstractActionExt lowerPage = new AbstractActionExt() {

            public void actionPerformed(ActionEvent e) {
                lowerPage();
            }

        };
        actionMap.put(Navigator.LOWER_PAGE_KEY, lowerPage);

        AbstractActionExt upperCell = new AbstractActionExt() {

            public void actionPerformed(ActionEvent e) {
                upperCell();
            }

        };
        actionMap.put(Navigator.UPPER_CELL_KEY, upperCell);

        AbstractActionExt lowerCell = new AbstractActionExt() {

            public void actionPerformed(ActionEvent e) {
                lowerCell();
            }

        };
        actionMap.put(Navigator.LOWER_CELL_KEY, lowerCell);

        AbstractActionExt zoomIn = new AbstractActionExt() {

            public void actionPerformed(ActionEvent e) {
                commit();
            }

        };
        actionMap.put(Navigator.ZOOM_IN_KEY, zoomIn);

        AbstractActionExt zoomOut = new AbstractActionExt() {

            public void actionPerformed(ActionEvent e) {
                zoomOut();
            }

        };
        actionMap.put(Navigator.ZOOM_OUT_KEY, zoomOut);

    }

 //----------------- 2D cell navigation
    
    /**
     * 
     */
    protected void lowerCell() {
        getNavigator().lowerCell();
        calendarView.repaint();
    }

    /**
     * 
     */
    protected void upperCell() {
        getNavigator().upperCell();
        calendarView.repaint();
    }

    /**
     * 
     */
    protected void previousCell() {
        getNavigator().previousCell();
        calendarView.repaint();
    }

    /**
     * 
     */
    protected void nextCell() {
        getNavigator().nextCell();
        calendarView.repaint();
    }

//--------------- 2D page navigation
    /**
     * 
     */
    protected void lowerPage() {
        // <snip> Scrolling Animation
        // Prepare the animation
        pagingAnimator.beforeMove(Direction.FORWARD);
        // </snip>    
        navigator.lowerPage();
        updateSelectionFromNavigator(Direction.FORWARD);
    }

    /**
     * 
     */
    protected void upperPage() {
        // <snip> Scrolling Animation
        // Prepare the animation
        pagingAnimator.beforeMove(Direction.BACKWARD);
        // </snip>    
        navigator.upperPage();
        updateSelectionFromNavigator(Direction.BACKWARD);
    }

    private void nextPage() {
        // <snip> Scrolling Animation
        // Prepare the animation
        pagingAnimator.beforeMove(Direction.FORWARD);
        // </snip>    
        navigator.nextPage();
        updateSelectionFromNavigator(Direction.FORWARD);
    }


    /**
     * 
     */
    protected void previousPage() {
        // <snip> Scrolling Animation
        // Prepare the animation
        pagingAnimator.beforeMove(Direction.BACKWARD);
        // </snip>    
        navigator.previousPage();
        updateSelectionFromNavigator(Direction.BACKWARD);
    }

    private void updateSelectionFromNavigator(Direction direction) {
//        calendarView.getSelectionModel().setAdjusting(true);
//        calendarView.setSelectionDate(navigator.getLeadDate());
        // <snip> Scrolling Animation
        // Complete and start animation if appropriate
        if (direction != null) {
            pagingAnimator.afterMove(direction);
            // </snip>    
        }
//        calendarView.ensurePageVisible();
        // PENDING JW: any way to get rid of this manual repaint?
        // needed only if navigating unselectable dates
        calendarView.repaint();
    }
    

//------------------- zoom/commit/cancel


    /**
     * 
     */
    protected void zoomOut() {
        // TODO Auto-generated method stub

    }

    /**
     * 
     */
    protected void cancel() {
        // TODO Auto-generated method stub

    }

    /**
     * 
     */
    protected void commit() {
        // TODO Auto-generated method stub

    }

    protected Rectangle getPageDetailsBounds() {
        return new Rectangle(20, 20, 150, 150);
    }
    
    // ---------------------- allow access for subs
    /**
     * @return
     */
    protected PageNavigator getNavigator() {
        return navigator;
    }

    // ------------------ paint

    @Override
    public void paint(Graphics g, JComponent c) {
        if (pagingAnimator.isRunning()) {
            pagingAnimator.paintIcon(g);
        } else {
            paintDetails(g);

        }
    }

    /**
     * @param g
     */
    protected void paintDetails(Graphics g) {
        navigatorView.paintComponent(g);
        
    }
    // ----------------------- layout



    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(200, 200);
    }

    // -------------------------- calendar ui implementation
    @Override
    public LocalDateTime getCellAtLocation(int x, int y) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LocalDateTime getCell(int row, int column) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LocalDateTime getStartOfPage() {
        return getNavigator().getStartOfPage();
    }

    /**
     * PENDING JW: shouldn't be necessary to publicly expose - all grid related
     * logic must be solved in the navigator.
     */
    @Override
    public Page getPageType() {
        return getNavigator().getPage();
    }

    @Override
    public int getColumnCount() {
        return getNavigator().getColumnCount();
    }

    @Override
    public int getRowCount() {
        return getNavigator().getRowCount();
    }

    @Override
    public LocalDateTime getLead() {
        return getNavigator().getLead();
    }

    @Override
    public CellState getCellState(int row, int column) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings({ "UnusedDeclaration" })
    public static ComponentUI createUI(JComponent c) {
        return new BasicCalendarUI();
    }

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(BasicCalendarUI.class
            .getName());
}
