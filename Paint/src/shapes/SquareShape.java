package shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.json.Json;
import javax.json.JsonObject;

import java.awt.geom.AffineTransform;

public class SquareShape extends Shape {

    private int size;

    public SquareShape(int x0, int y0, int size) {
        super();
        this.x1 = x0;
        this.y1 = y0;
        this.size = size;
        this.rotationAngle = 0;
        constructShape();
    }

    @Override
    public void constructShape() {
        awtShape = new Rectangle2D.Float(x1, y1, size, size);
    }

     @Override
    public void paint(Graphics2D g) {
        AffineTransform old = g.getTransform(); // Save the current transform
        Point centroid = getCentroid();
        
        // Rotate the graphics context around the centroid of the square
        g.rotate(rotationAngle, centroid.x, centroid.y);

        // Now paint the square with its current fill and border
        if (fill) {
            g.setColor(fillColor);
            g.fill(awtShape);
        }
        g.setColor(borderColor);
        g.setStroke(new BasicStroke(borderWidth));
        g.draw(awtShape);

        g.setTransform(old); // Restore original transform
    }

    public void setEndPoint(int x2, int y2) {
        // Calculate the size based on the new endpoint, ensuring the square shape
        int newWidth = Math.abs(x2 - x1);
        int newHeight = Math.abs(y2 - y1);
        this.size = Math.min(newWidth, newHeight);
        constructShape();
    }

    public void setSize(int size) {
        this.size = size;
        constructShape();
    }

    @Override
    public boolean isClicked(int x, int y) {
       return awtShape.contains(x, y);
    }

    @Override
    public Point getCentroid() {
        // Centroid calculation should be based on the square's size
        return new Point(x1 + size / 2, y1 + size / 2);
    }

    @Override
    public void rotate(double radians, Point centroid) {
        // Update the rotation angle
        this.rotationAngle = radians;
        // Repaint the shape to show the rotation
    }
    @Override
    public void scale(double scaleFactor) {
        Point centroid = getCentroid();

        // Adjust the top-left corner of the square relative to the centroid
        x1 = centroid.x - (int)((centroid.x - x1) * scaleFactor);
        y1 = centroid.y - (int)((centroid.y - y1) * scaleFactor);
        
        // Update the size of the square
        size = (int)(size * scaleFactor);

        constructShape(); // Reconstruct the shape with the new size
    }
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("type", "square")
            .add("x", x1)
            .add("y", y1)
            .add("properties", Json.createObjectBuilder()
                .add("size", size)
                .add("borderColor", colorToString(borderColor))
                .add("borderWidth", borderWidth)
                .add("fillColor", fill ? colorToString(fillColor) : "none")
                .add("rotation", rotationAngle))
            .build();
    }
}



