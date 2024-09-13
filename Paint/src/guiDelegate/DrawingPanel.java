 package guiDelegate;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.ShapeModel;
import shapes.Shape;
import shapes.SquareShape;
import shapes.TriangleShape;
import shapes.EllipseShape;
import shapes.LineShape;
import shapes.RectangleShape;
import shapes.CircleShape;
import model.ShapeType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class DrawingPanel extends JPanel implements PropertyChangeListener {
    private ShapeModel model;
    private Shape selectedShape;
    private Point startPoint;
    private ShapeType currentShapeType;
    private static final Color HIGHLIGHT_COLOR = new Color(255,0,0,128);
    private Color lineColor;
    private float lineWidth;
    private boolean selectionMode = false;
    private boolean rotationMode = false;

    public DrawingPanel(ShapeModel model) {
        this.model = model;
        this.setBackground(Color.WHITE);
        model.addPropertyChangeListener(this);
        setupMouseHandlers();
    }

    private void setupMouseHandlers() {
        addMouseListener(new MouseAdapter() {
            @Override
public void mousePressed(MouseEvent e) {
    startPoint = e.getPoint(); 

    if (!selectionMode && currentShapeType != null) {
        // In drawing mode, start creating a new shape
        selectedShape = createShape(startPoint, startPoint);
    } else if (selectionMode) {
        // In selection mode, select a shape if clicked
        Shape newSelectedShape = findShapeAtPoint(e.getX(), e.getY());
        if (newSelectedShape != selectedShape) {
            selectedShape = newSelectedShape;
            System.out.println("Selected shape: "+ selectedShape);
        }
        repaint(); 
    }
}


            @Override
            public void mouseReleased(MouseEvent e) {
                if (!selectionMode && currentShapeType != null && selectedShape != null) {
                    model.addShape(selectedShape);
                    selectedShape = null;
                    startPoint = null;
                }
                repaint(); 
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
public void mouseDragged(MouseEvent e) {
    if (selectionMode && selectedShape != null) {
        // Calculate the delta (difference) between the current mouse position and the last position
        int dx = e.getX() - startPoint.x;
        int dy = e.getY() - startPoint.y;

        // Move the shape by this delta
        selectedShape.drag(dx, dy);

        // Update startPoint to the current mouse position for the next calculation
        startPoint = e.getPoint();

        // Repaint the panel to reflect the shape's new position
        repaint();
    } else if (!selectionMode && selectedShape != null && startPoint != null) {
        // Continue updating the shape being drawn
        updateCreatingShape(e.getPoint());
        repaint();
    }
}

        });
        
    }


    private Shape createShape(Point start, Point end) {
        // Ensure currentShapeType is set to something valid
        if (currentShapeType != null) {
            switch (currentShapeType) {
                case LINE:
                    LineShape line = new LineShape(start.x, start.y, end.x, end.y);
                    line.setBorderColor(lineColor); 
                    line.setBorderWidth(lineWidth); 
                    return line;
                case RECTANGLE:
                    RectangleShape rectangle = new RectangleShape(start.x, start.y, end.x, end.y);
                    return rectangle;
                case ELLIPSE: 
                     EllipseShape ellipse = new EllipseShape(start.x, start.y, end.x, end.y);
                
                    return ellipse;
                case TRIANGLE: 
                     TriangleShape triangle = new TriangleShape(start.x, start.y, end.x, end.y);
                     return triangle;
                case SQUARE:
                    int size = Math.max(Math.abs(end.x - start.x), Math.abs(end.y - start.y));
                    SquareShape square = new SquareShape(start.x, start.y, size);
                    return square;
                    case CIRCLE:
                    int circleDiameter = Math.max(Math.abs(end.x - start.x), Math.abs(end.y - start.y));
                    CircleShape circle = new CircleShape(start.x, start.y, circleDiameter);
                    return circle;
            }
        }
        return null;
    }

    private void updateCreatingShape(Point currentPoint) {
        if (selectedShape instanceof LineShape) {
            ((LineShape) selectedShape).setEndPoint(currentPoint.x, currentPoint.y);
        } else if (selectedShape instanceof RectangleShape) {
            ((RectangleShape) selectedShape).setEndPoint(currentPoint.x, currentPoint.y);
        } else if (selectedShape instanceof EllipseShape) {
            ((EllipseShape) selectedShape).setEndPoint(currentPoint.x, currentPoint.y);
        } else if (selectedShape instanceof TriangleShape) {
            ((TriangleShape) selectedShape).setEndPoint(currentPoint.x, currentPoint.y);
        } else if (selectedShape instanceof SquareShape) {
            ((SquareShape) selectedShape).setEndPoint(currentPoint.x, currentPoint.y);
        } else if (selectedShape instanceof CircleShape) {
            ((CircleShape)selectedShape).setEndPoint(currentPoint.x, currentPoint.y);
        }
    }
    
    private Shape findShapeAtPoint(int x, int y) {
        // Iterate through all shapes in reverse order
        for (int i = model.getShapes().size() - 1; i >= 0; i--) {
            Shape shape = model.getShapes().get(i);
            if (shape.isClicked(x, y)) {
                return shape;
            }
        }
        return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Shape shape : model.getShapes()) {
            shape.paint(g2d);
            if (shape == selectedShape) {
                highlightSelectedShape(g2d, shape);
            }
        }
    }

    private void highlightSelectedShape(Graphics2D g, Shape shape) {
        g.setColor(HIGHLIGHT_COLOR);
        g.setStroke(new BasicStroke(3));
        g.draw(shape.getBoundary().getBounds2D());
    }
    
    public Color getSelectedShapeFillColor() {
        if (selectedShape != null) {
            return selectedShape.getFillColor();
        } else {
            return Color.BLACK;
        }
    }

    public void updateSelectedShapeFill(Color fillColor, boolean fill) {
        if (selectedShape != null) {
            selectedShape.setFillColor(fillColor);
            selectedShape.setFillState(fill);
            repaint();
        }
    }

    public ShapeType getCurrentShapeType() {
        return this.currentShapeType;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }
    public void updateSelectedShapeBorderWidth(float width) {
        if (selectedShape != null) {
            selectedShape.setBorderWidth(width);
            repaint();
        }
    }
    
    public void updateSelectedShapeBorderColor(Color color) {
        if (selectedShape != null) {
            System.out.println("Uodating border color of: " + selectedShape);
            selectedShape.setBorderColor(color);
            repaint();
        }
    }
    public void setCurrentShapeType(ShapeType shapeType) {
        this.currentShapeType = shapeType;
        this.selectedShape = null; 
    }
    public void onLineButtonPressed() {
        // Set the current shape type
        this.currentShapeType = ShapeType.LINE;
    
        // Prompt for line color
        Color lineColor = JColorChooser.showDialog(this, "Choose Line Color", Color.BLACK);
        if (lineColor == null) {
            this.currentShapeType = null;
            return;
        }
    
        // Prompt for line width
        String lineWidthString = JOptionPane.showInputDialog(this, "Enter Line Width:", "1.0");
        float lineWidth;
        try {
            lineWidth = Float.parseFloat(lineWidthString);
        } catch (NumberFormatException e) {
            // User entered an invalid number, exit the line creation mode
            JOptionPane.showMessageDialog(this, "Invalid line width entered. Please enter a number.");
            this.currentShapeType = null;
            return;
        }
    
        // Store the properties for new lines
        this.lineColor = lineColor;
        this.lineWidth = lineWidth;
    }
    public boolean isSelectionMode(){
        return selectionMode;

    }
    public void toggleSelectionMode() {
        selectionMode = !selectionMode;
        if (selectionMode) {
            currentShapeType = null; 
      
        }
        repaint();
    }
    public void toggleRotationMode() {
        rotationMode = !rotationMode;
        if (rotationMode) {
     
            selectionMode = false;
        }
    }
    public boolean isRotationMode(){
        return rotationMode;
    }
    public void rotateSelectedShape(int angle) {
        if (selectedShape != null) {
            double radians = Math.toRadians(angle);
            Point centroid = selectedShape.getCentroid();
            selectedShape.rotate(radians, centroid);
            repaint(); 
        }
    }
    public void scaleSelectedShape(double scaleFactor) {
        if (selectedShape != null) {
            selectedShape.scale(scaleFactor);
            repaint(); 
        }
    }
    
    
}