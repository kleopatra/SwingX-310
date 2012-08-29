/*
 * Created on 03.08.2012
 *
 */
package org.jdesktop.swingx.calendar310.plaf.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.time.LocalDateTime;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.calendar.jsr310.DateTimeUtils;
import org.jdesktop.swingx.calendar310.Navigator;

/**
 * Controller for painting the details of a calendar page.
 * 
 * PENDING JW: don't extend a panel, just provide one.
 * 
 * @author Jeanette Winzenburg, Berlin
 */
public class NavigatorView {

    private Navigator navigator;

    private boolean rowHeader;

    private boolean columnHeader;
    
    private GridPanel grid;

    private JLabel label;

    public NavigatorView() {
        this(null);
    }

    /**
     * @param object
     */
    public NavigatorView(Navigator navigator) {
        this.navigator = navigator;
        grid = new GridPanel();
        label = new JLabel();
        label.setHorizontalAlignment(JLabel.TRAILING);
    }

    public JComponent getGridView() {
        return grid;
    }
    
    protected int getActualColumnCount() {
        if (navigator == null)
            return 0;
        int columnCount = navigator.getColumnCount();
        if (rowHeader) {
            columnCount++;
        }
        return columnCount;
    }

    protected int getActualRowCount() {
        if (navigator == null)
            return 0;
        int rowCount = navigator.getRowCount();
        if (columnHeader) {
            rowCount++;
        }
        return rowCount;
    }


    /**
     * @return
     */
    protected int getFirstRow() {
        return navigator != null? 1 : -1;
    }

    /**
     * @return
     */
    public int getLastRow() {
        return navigator != null ? navigator.getRowCount() : -1;
    }
    
    /**
     * @return
     */
    protected int getLastColumn() {
        return navigator != null ? navigator.getColumnCount() : - 1;
    }

    /**
     * @return
     */
    protected int getFirstColumn() {
        return navigator != null? 1 : -1;
    }

    Border empty = BorderFactory.createEmptyBorder();
    Border lead = BorderFactory.createLineBorder(Color.red);

    /**
     * @param row
     * @param column
     * @return
     */
    public JComponent prepareRendererComponent(int row, int column) {
        
        LocalDateTime dateTime = navigator.getCellValue(row, column);
        label.setText("" + dateTime.getDayOfMonth());
         label.setBorder(DateTimeUtils.isSame(dateTime, navigator.getLead(), navigator.getPage().cellField) ?
                 lead : empty);
        return label;
    }
    
    
    private class GridPanel extends JXPanel {
        
        private CellRendererPane cellRendererPane;
        private int cellHeight;
        private int cellWidth;

        public GridPanel() {
            cellRendererPane = new CellRendererPane();
            setLayout(null);
            add(cellRendererPane);
            setFocusable(true);
        }
        
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }


        @Override
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }


        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintColumnHeader(g);
            paintRowHeader(g);
            paintCells(g);
        }

        /**
         * @param g
         */
        protected void paintCells(Graphics g) {
            calculateBoxWidths();
            
            for (int row = getFirstRow(); row <= getLastRow() ; row++) {
                for (int column = getFirstColumn(); column <= getLastColumn(); column++) {
                    paintCell(g, row, column);
                }
            }
        }

        protected void calculateBoxWidths() {
            int rowCount = getActualRowCount();
            int columnCount = getActualColumnCount();
            cellWidth = getWidth() / columnCount;
            cellHeight = getHeight() / rowCount;
        }

        /**
         * @param g
         * @param row
         * @param column
         */
        protected void paintCell(Graphics g, int row, int column) {
            JComponent comp = prepareRendererComponent(row, column);
            Rectangle r = getCellBounds(row, column);
//            cellRendererPane.paintComponent(g, comp, this, r);
            cellRendererPane.paintComponent(g, comp, this, r.x, r.y, r.width, r.height, true);
        }

        /**
         * @param row
         * @param column
         * @return
         */
        private Rectangle getCellBounds(int row, int column) {
            Rectangle bounds = new Rectangle(0, 0, cellWidth-1, cellHeight-1);
            if (!columnHeader) {
                row--;
            }
            if (!rowHeader) {
                column--;
            }
            bounds.translate(column * cellWidth, row * cellHeight);
            return bounds;
        }


        /**
         * @param g
         */
        protected void paintRowHeader(Graphics g) {
            if (!rowHeader)
                return;
        }

        /**
         * @param g
         */
        protected void paintColumnHeader(Graphics g) {
            if (!columnHeader)
                return;
        }

    }

}
