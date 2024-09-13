package shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.json.Json;
import javax.json.JsonObject;

import java.awt.geom.AffineTransform;

public class RectangleShape extends Shape {

    private double rotationAngle;
    private String id;

    public RectangleShape(int x0, int y0, int x1, int y1) {
        super();
        this.x1 = x0;
        this.y1 = y0;
        this.width = Math.abs(x1 - x0);
        this.height = Math.abs(y1 - y0);
        this.rotationAngle= 0;
        constructShape();
    }

    @Override
    public void constructShape() {
        awtShape = new Rectangle2D.Float(x1, y1, width, height);
    }

     @Override
    public void paint(Graphics2D g) {
        AffineTransform old = g.getTransform(); 
        Point centroid = getCentroid();
        
        // Rotate the graphics context around the centroid of the rectangle
        g.rotate(rotationAngle, centroid.x, centroid.y);

        // paint the rectangle with its current fill and border
        if (fill) {
            g.setColor(fillColor);
            g.fill(awtShape);
        }
        g.setColor(borderColor);
        g.setStroke(new BasicStroke(borderWidth));
        g.draw(awtShape);

        g.setTransform(old); 
    }

    public void setEndPoint(int x2, int y2) {
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
        constructShape();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }
    @Override
    public boolean isClicked(int x, int y) {
        return ((Rectangle2D) awtShape).contains(x, y);
    }
    @Override
    public Point getCentroid() {
        return new Point(x1 + width / 2, y1 + height / 2);
    }
    @Override
    public void rotate (double radians, Point centroid) {
        this.rotationAngle = radians;
    }
    @Override
    public void scale(double scaleFactor) {
        Point centroid = getCentroid();

        // Adjust the corners of the rectangle relative to the centroid
        x1 = centroid.x - (int)((centroid.x - x1) * scaleFactor);
        y1 = centroid.y - (int)((centroid.y - y1) * scaleFactor);
        int newWidth = (int)(width * scaleFactor);
        int newHeight = (int)(height * scaleFactor);

        // Update width and height
        width = newWidth;
        height = newHeight;

        constructShape(); 
    }
   public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("type", "rectangle")
        .add("x", x1)
        .add("y", y1)
        .add("properties", Json.createObjectBuilder()
            .add("width", width)
            .add("height", height)
            .add("borderColor", colorToString(borderColor))
            .add("borderWidth", borderWidth)
            .add("fillColor", fill ? colorToString(fillColor) : "none") 
            .add("rotation", rotationAngle))
        .build();
}
}
    