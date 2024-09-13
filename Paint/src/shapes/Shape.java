package shapes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

//Abstract base class for shapes in a vector graphics drawing application.

public abstract class Shape implements Serializable {
    protected java.awt.Shape awtShape;
    protected int x1, y1, x2, y2;
    protected Color borderColor, fillColor;
    protected boolean fill;
    protected float borderWidth;
    protected double rotationAngle;
    protected int width, height;
    protected String id;

    //Constructor
    public Shape() {
        borderColor = Color.BLACK;
        fillColor = Color.WHITE;
        borderWidth = 1.0f;
        rotationAngle = 0.0;
        fill = false;
    }
//Abstract shape construction
    public abstract void constructShape();

    //Paints the shape on a Graphics2d context

    public void paint(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();
        g.rotate(Math.toRadians(rotationAngle), x1 + (x2 - x1) / 2.0, y1 + (y2 - y1) / 2.0);

        if (fill) {
            g.setColor(fillColor);
            g.fill(awtShape);
        }

        g.setColor(borderColor);
        g.setStroke(new BasicStroke(borderWidth));
        g.draw(awtShape);

        g.setTransform(oldTransform);
    }
//Checks if a point is in the shape
    public boolean isClicked(int x, int y) {
        return awtShape.contains(x, y);
    }
//Moves the shape
    public void drag(int dx, int dy) {
        if (awtShape !=null) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
        constructShape();
    }
    }
    // Method to update the rotation angle
    public void rotate(double angle) {
        this.rotationAngle += angle;
        if (this.rotationAngle < 0) {
            this.rotationAngle += 360;
        } else if (this.rotationAngle >= 360) {
            this.rotationAngle -= 360;
        }
    }
    // Getters and setters for properties
    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public boolean getFillState() {
        return fill;
    }

    public void setFillState(boolean fill) {
        this.fill = fill;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public java.awt.Shape getBoundary() {
        return awtShape;
    }

    //Rotation/Resizing abstracts
    public abstract Point getCentroid();

    public abstract void rotate (double radians, Point centroid);

    public abstract void scale (double scaleFactor);

    public void setId(String id) {
        this.id =id;

    }
    public String getId() {
        return id;
    }
    protected String colorToString(Color color) {
        if (color == null) return "none";
    
        // Colors to their names
        if (color.equals(Color.RED)) return "red";
        if (color.equals(Color.GREEN)) return "green";
        if (color.equals(Color.BLUE)) return "blue";
        if (color.equals(Color.YELLOW)) return "yellow";
        if (color.equals(Color.BLACK)) return "black";
        if (color.equals(Color.WHITE)) return "white";
        if (color.equals(Color.CYAN)) return "cyan";
        if (color.equals(Color.MAGENTA)) return "magenta";
        if (color.equals(Color.ORANGE)) return "orange";
        if (color.equals(Color.PINK)) return "pink";
        if (color.equals(Color.GRAY)) return "gray";
        if (color.equals(Color.DARK_GRAY)) return "darkGray";
        if (color.equals(Color.LIGHT_GRAY)) return "lightGray";
    
    
        // Format: rgb(r,g,b) e.g., "rgb(255,0,0)"red
        return String.format("rgb(%d,%d,%d)", color.getRed(), color.getGreen(), color.getBlue());
    }
}