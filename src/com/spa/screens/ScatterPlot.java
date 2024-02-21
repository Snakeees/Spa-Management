package com.spa.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static com.spa.SpaManagement.BUTTON_COLOR;


public class ScatterPlot extends JPanel implements MouseMotionListener {

    public int[] dataSet;
    public String[] MonthNames;
    private ArrayList <DataPoint> dataPoints = new ArrayList<>();
    public Color graphColor = Color.RED;

    private int xMin, xMax, yMin, yMax;

    int axisLabelWidth = 15;
    int axisLabelHeight = 10;
    int graphWidth;
    int graphHeight;
    int originX;
    int originY;
    int padX = 40;
    int padY = 20;

    double xScale;
    double yScale;

    public ScatterPlot(int[] data, int[] size, String[] monthNames) {
        this.dataSet = data;
        this.MonthNames = monthNames;
        setSize(size[0], size[1]);
        Dimension dimension = new Dimension(size[0]+padX*2, size[1]+padY*3);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        this.graphWidth = size[0];
        this.graphHeight = size[1];
        this.originX = padX;
        this.originY = padY + graphHeight;

        calculateScale();
        setBackground(BUTTON_COLOR);
        setSize(size[0], size[1]);
        this.addMouseMotionListener(this);
        ToolTipManager.sharedInstance().registerComponent(this);
        ToolTipManager.sharedInstance().setInitialDelay(0);
    }

    private void calculateScale() {
        xMin = 0;
        xMax = dataSet.length - 1;
        yMin = 0;
        yMax = (int) Math.ceil(findMax(dataSet) * 1.2);
        xScale = (double) graphWidth / (xMax - xMin);
        yScale = (double) graphHeight / (yMax - yMin);
    }

    private int findMax(int[] dataSet) {
        int max = Integer.MIN_VALUE;
        for (int data : dataSet) {
            max = Math.max(max, data);
        }
        return max;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawAxes(g2d);
        drawScatterPlot(g2d, dataSet, graphColor);
    }

    private void drawAxes(Graphics2D g2d) {
        float[] dashPattern = { 2f, 3f }; // Pattern for the dotted lines
        Stroke dotted = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, dashPattern, 0f);

        // Horizontal Axis
        g2d.setColor(Color.BLACK);
        g2d.drawLine(originX, originY, originX + graphWidth, originY);
        g2d.drawLine(originX, originY, originX, originY - graphHeight);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(dotted);

        FontMetrics fm = g2d.getFontMetrics();

        for (int i = xMin; i <= xMax; i += 1) {
            int xScreen = originX + (int)(i * xScale);
            g2d.drawLine(xScreen, originY, xScreen, originY - graphHeight); // Vertical grid lines

            int textWidth = fm.stringWidth(MonthNames[i]);
            int textX = xScreen - (textWidth / 2);

            g2d.setColor(Color.WHITE);
            g2d.drawString(MonthNames[i], textX, originY + axisLabelHeight * 2);
            g2d.setColor(Color.LIGHT_GRAY);
        }

        int yIncrement = determineYIncrement();

        for (int i = yMin; i <= yMax; i += yIncrement) {
            int yScreen = originY - (int)(i * yScale);
            g2d.drawLine(originX, yScreen, originX + graphWidth, yScreen); // Horizontal grid lines

            String text = Integer.toString(i);
            int textWidth = fm.stringWidth(text);
            int textX = originX - textWidth - (int)(axisLabelWidth * 0.5);
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, textX, yScreen - 1 + (fm.getAscent() - fm.getDescent()) / 2);
            g2d.setColor(Color.LIGHT_GRAY);
        }

        g2d.setStroke(new BasicStroke());
    }

    private void drawScatterPlot(Graphics2D g2d, int[] dataSet, Color color) {
        dataPoints.clear(); // Clear previous data points
        g2d.setColor(color);
        Stroke defaultStroke = g2d.getStroke();
        Stroke dotted = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{2f, 0f, 2f}, 0f);

        for (int i = 0; i < dataSet.length; i++) {
            int x = originX + (int)(i * xScale);
            int y = originY - (int)((dataSet[i]) * yScale);

            g2d.fillOval(x - 5, y - 5, 10, 10);
            Rectangle bounds = new Rectangle(x - 10, y - 10, 20, 20);
            dataPoints.add(new DataPoint(bounds, dataSet[i]));

            if (i > 0) {
                int prevX = originX + (int)((i - 1) * xScale);
                int prevY = originY - (int)((dataSet[i - 1]) * yScale);

                g2d.setStroke(dotted);
                g2d.draw(new Line2D.Float(prevX, prevY, x, y));
            }
        }
        g2d.setStroke(defaultStroke);
    }

    private int determineYIncrement() {
        int[] increments = {2, 5, 10, 25, 50, 75, 100, 150, 200, 250, 300, 400, 500, 750, 1000};
        int selectedIncrement = increments[increments.length - 1];

        for (int increment : increments) {
            if (yMax / increment <= 10) {
                selectedIncrement = increment;
                break;
            }
        }
        return selectedIncrement;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        for (DataPoint point : dataPoints) {
            if (point.contains(e.getX(), e.getY())) {
                setToolTipText("Value: " + point.data);
                return;
            }
        }
        setToolTipText(null);
    }


    class DataPoint {
        Rectangle bounds;
        int data;

        DataPoint(Rectangle bounds, int data) {
            this.bounds = bounds;
            this.data = data;
        }

        boolean contains(int x, int y) {
            return bounds.contains(x, y);
        }
    }

}
